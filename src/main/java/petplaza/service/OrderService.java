package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import petplaza.model.dto.*;
import petplaza.model.entity.*;

import petplaza.model.repository.*;


import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UnitLogueService unitLogueService;
    @Autowired
    private UnitLogueEntityRepository unitLogueEntityRepository;


    // 0. 주문등록
    @Transactional
    public boolean postOrder(OrderDto orderDto , OrderDetailDto orderDetailDto) {

        // 관리자가 선택한 mno 정보를 찾아오기
        MemberEntity memberEntity = memberRepository.findById(orderDto.getMno()).get();

        // dto를 entity로 변환하여 저장하기
        OrderEntity orderEntity = orderRepository.save(orderDto.toEntity());

        // 주문 상태를 주문 엔티티에 저장하기( 기본값 요청으로 사용하기 )
        orderEntity.setOstate("요청");

        // 회원 정보를 주문 엔티티에 저장하기
        orderEntity.setMemberEntity(memberEntity);

        // orderEntity에 저장된 ono를 잧아오기
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderEntity.getOno());

        // 주문 상세 정보를 담을 리스트
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();

        // 없다면 false 반환
        if( !orderEntityOptional.isPresent() ){
            return false;
        }
        // dto를 entity로 변환하여 저장하기
        OrderDetailEntity orderDetailEntity  = orderDetailRepository.save( orderDetailDto.DetailEntity() );

        // 단방향 ono fk( 주문 정보를 주문 상세에 저장 )
        orderDetailEntity.setOrderEntity( orderEntity );
        // 관리자가 선택한 pno정보를 찾아오기
        ProductEntity productEntity = productRepository.findById( orderDetailDto.getPno() ).get();
        
        //단뱡향 pno fk ( 찾아온 pno 정보를 주문 상세에 저장 )
        orderDetailEntity.setProductEntity( productEntity ) ;

        // dprice에 pprice 값을 설정
        orderDetailEntity.setDprice(productEntity.getPprice());

        // OrderDetailEntity를 다시 저장하여 변경된 dprice를 DB에 반영
        orderDetailEntity = orderDetailRepository.save(orderDetailEntity);
        
        // 엔티티를 리스트에 더하기
        orderDetailEntity.getOrderEntity().getOrderDetailEntity().add(orderDetailEntity);

        // 수량 상호작용 , 재고기록과 제품번호 저장
        int choiceUnit = orderDetailDto.getDamount(); // 선택한 수량
        int getUnit =unitLogueService.nowCount(orderDetailDto.getPno()); // 재고기록에서 가져온 수량
        System.out.println("nowCount함수 =" + getUnit + "선택수량=" +choiceUnit );

        if(choiceUnit <= getUnit) {
            UnitLogueEntity unitLogueEntity = UnitLogueEntity.builder()
                    .uincrease(-choiceUnit)
                    .urecord("주문판매(주문요청)")
                    .build();

            unitLogueEntity.setProductEntity(productEntity);

            unitLogueEntityRepository.save(unitLogueEntity);

        } else {

            return false;

        }

        return true;
    }
    // 1. 주문출력
    @Transactional
    public OrderPageDto getOrder(int page , String key , String keyword , int view ) {
        Pageable pageable = PageRequest.of(page -1 , view);
        // 1. 주문 관리 리스트 엔티티에서 모두 찾기 // 1. 데이터베이스에서 모든 정보를 가져온다.
        Page<OrderEntity> orderEntityList = orderRepository.findBySearch(key , keyword , pageable);
        
        // 2. 리스트로 담기 위한 가변 배열 생성하기 // 2. 객체의 리스트를 생성하는 코드 [ 빈 객체 선언]
        List<OrderDto> orderDtos = new ArrayList<>();

        // 3. 반복문 돌려서 orderEntity 형식을 Dto형식으로 변환하여 배열에 담기
        orderEntityList.forEach((orderEntity) -> orderDtos.add(orderEntity.toDto()));

        // 총 페이지수
        int totalPages = orderEntityList.getTotalPages();

        // 총 게시물수
        Long totalCounts = orderEntityList.getTotalElements();
        OrderPageDto orderPageDto = OrderPageDto.builder()
                .orderDtos(orderDtos)
                .totalCounts(totalCounts)
                .totalPages(totalPages)
                .build();
        
        // 4. 리스트 반환
        return orderPageDto;
    }

    // 2. 주문수정
    @Transactional
    public boolean putOrder(OrderDto orderdto) {

        // 1. 해당 PK로 주문 레코드 찾기
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderdto.getOno());

        // 2 만약에 해당 주문 레코드가 존재하면
        if(optionalOrderEntity.isPresent()) {

            // 1. 가져오기
            OrderEntity orderEntity = optionalOrderEntity.get();
            // 2. 해당 레코드의 상태값 수정하기
            orderEntity.setOstate(orderdto.getOstate());

            return true;
        }

        return false;
    }

    // 3. 등록시 제품 출력
    public List<ProductDto> getProduct() {
        // 1. 주문 관리 리스트 엔티티에서 모두 찾기
        List<ProductEntity> productEntityList = productRepository.findAll();

        // 2. 리스트로 담기 위한 가변 배열 생성하기
        List<ProductDto> productDtoList = new ArrayList<>();

        // 3. 반복문 돌려서 orderEntity 형식을 Dto형식으로 변환하여 배열에 담기
        productEntityList.forEach((productEntity) -> productDtoList.add(productEntity.allDto()));

        // 4. 리스트 반환
        return productDtoList;
    }
}
