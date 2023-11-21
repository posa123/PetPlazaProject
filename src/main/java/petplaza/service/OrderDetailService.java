package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import petplaza.model.dto.OrderDetailDto;
import petplaza.model.dto.OrderDetailPageDto;
import petplaza.model.dto.OrderPageDto;
import petplaza.model.entity.OrderDetailEntity;

import petplaza.model.entity.UnitLogueEntity;
import petplaza.model.repository.OrderDetailRepository;
import petplaza.model.repository.ProductRepository;
import petplaza.model.repository.UnitLogueEntityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitLogueService unitLogueService;
    @Autowired
    private UnitLogueEntityRepository unitLogueEntityRepository;

    /*
    제품 가격 판매가를 가져와서 주문 가격에 넣어줘야한다.
    제품 가격 판매가가 변동이 있을경우에도 주문 가격은 유지되야만 한다.
    */

    // 1. 상세 관리 출력
    @Transactional
    public List<OrderDetailDto> getDetail(int ono) {
        // 1. 상세 관리 리스트에서 해당하는 주문번호로만 이루어진 리스트들 찾기
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findByOrderEntityOno(ono);


        System.out.println("ono = " + ono);
        System.out.println(orderDetailEntityList);
        // 2. 주문번호로만 이뤄진 리스트 담기 위한 가변 배열 생성
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

        // 3. Entity리스트를 반복문을 이용하여 해당하는 엔티티를 찾아 dto형식으로 변환하여 위에서 만든 가변 배열에 넣기.
        orderDetailEntityList.forEach((orderDetailEntity) -> {
            orderDetailDtoList.add(orderDetailEntity.toDetailDto());
        });
        // 4. 리스트 반환
        return orderDetailDtoList;
    }

    // 2. 상세 관리 수정
    @Transactional
    public boolean putDetail(OrderDetailDto orderDetailDto) {
        System.out.println("orderDetailDto = " + orderDetailDto);

        // 1. 엔티티에서 상세관리번호를 찾기.
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(orderDetailDto.getDno());
        // 2. 만약에 해당 엔티티값이 존재하면
        if(optionalOrderDetailEntity.isPresent()) {
            // 1. 그 값을 가져온다.
            OrderDetailEntity orderDetailEntity = optionalOrderDetailEntity.get();

            // 이전 수량 저장
            int beforeDamount = orderDetailEntity.getDamount();

            // 수정된 값을 업데이트
            orderDetailEntity.setDamount(orderDetailDto.getDamount());

            // 수정 전과 수정 후의 수량 차이 계산
            int count = orderDetailDto.getDamount() - beforeDamount;
            
            // 현재재고수 함수를 사용하여 해당 상품의 현재 재고수를 구하여 저장
            int getUnit = unitLogueService.nowCount(optionalOrderDetailEntity.get().getProductEntity().getPno()); // 수정할 수량


            if(count <= getUnit) {
                UnitLogueEntity unitLogueEntity = UnitLogueEntity.builder()
                        .uincrease(-count)
                        .urecord("주문판매(수량수정)")
                        .build();

                unitLogueEntity.setProductEntity(optionalOrderDetailEntity.get().getProductEntity());


                unitLogueEntityRepository.save(unitLogueEntity);

            } else {

                return false;

            }

            return true;
        }

        return false;
    }

    // 상세 관리 삭제
    @Transactional
    public boolean removeDetail(int dno) {
        // 1. 해당하는 상세관리번호를 엔티티에서 찾는다.
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(dno);
        // 2. 만약에 엔티티에 존재하면
        if(optionalOrderDetailEntity.isPresent()) {
            // 1. 그 값을 삭제한다.
            orderDetailRepository.deleteById(dno);

            // 리스트에 담겨있는 수량
            int choiceUnit = optionalOrderDetailEntity.get().getDamount();
            // 현재 재고 수량
            int getunit = unitLogueService.nowCount(optionalOrderDetailEntity.get().getProductEntity().getPno());

            UnitLogueEntity unitLogueEntity = UnitLogueEntity.builder()
                    .uincrease(choiceUnit)
                    .urecord("주문판매(취소)")
                    .build();

            unitLogueEntity.setProductEntity(optionalOrderDetailEntity.get().getProductEntity());

            unitLogueEntityRepository.save(unitLogueEntity);

            return true;
        }

        return false;
    }

}
