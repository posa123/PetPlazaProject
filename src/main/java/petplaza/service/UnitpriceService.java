package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petplaza.model.dto.UnitPricePageDto;
import petplaza.model.dto.UnitpriceDto;
import petplaza.model.entity.ClientEntity;
import petplaza.model.entity.UnitpriceEntity;
import petplaza.model.repository.ClientRepository;
import petplaza.model.repository.UnitpriceRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// 제발 야ㅐㅇ방향 등록해줘
@Service

public class UnitpriceService {
    @Autowired
    private UnitpriceRepository unitpriceRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    // 1. 거래처 재고 등록
    public boolean UnitpriceWrite(UnitpriceDto unitpriceDto) {
        // cno를 사용하여 ClientEntity를 가져옵니다.
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(unitpriceDto.getCno());


        ClientEntity clientEntity = clientEntityOptional.get(); // 해당 거래처 엔티티 가져오기

        // UnitpriceEntity 생성
        UnitpriceEntity unitprice = unitpriceDto.tounitpriceEntity();

        // 양방향 관계 설정
        unitprice.setClientEntity(clientEntity); // UnitpriceEntity에서 ClientEntity 설정
        clientEntity.getUnitpriceEntityList().add(unitprice); // ClientEntity의 UnitpriceEntity 목록에 추가

        // UnitpriceEntity 저장
        unitprice = unitpriceRepository.save(unitprice);
        // 만약에 unitprice에 저장된 uno 값이 1보다 크면 true 리턴 시키겠다.
        //  메서드를 호출하여 반환된 값이 1 이상인지 확인후 이상이면 true 반환  1이하이면 false반환
        if (unitprice.getUno() >= 1) {
            return true;
        }
        return  false;
    }


        @Transactional
    // 2. 거래처 재고 리스트 출력
    public UnitPricePageDto UnitpriceAll(int page , String key , String keyword , int view) {
            // 1. Pageable : 페이지 인터페이스 ( 구현체 : 구현[추상메소드(인터페이스 가지는 함수)를 구현해주는 객체)
            // 사용이유 : Repository 인터페이스가 페이징처리할때 사용하는 인터페이스
            // 2. PageRequest: 구현체
            // of (현제페이지 , 현재 게시물 수
            // 현제피이지 : 0 부터 시작
            // 페이지별게시물수 : 2
            // Page : list 와 마찬가지로 여러개의 객체를 저장하는 타입

            Pageable pageable = PageRequest.of(page - 1, view);
            // 1. page 매개변수 잘받았는지 확인하는 작업!
            System.out.println("service page: " + page);
            // 2. 데이터베이스에서 모든 재고리스트를 가져온다.
           /* Page<UnitpriceEntity> unitpriceEntities = unitpriceRepository.findAll(pageable);*/
            Page<UnitpriceEntity> unitpriceEntities = unitpriceRepository.findBySearch(key, keyword ,pageable);
            // 3. UnitpriceDto 객체의 리스트를 생성하는 코드 [ 빈 객체 선언]
            List<UnitpriceDto> dtos = new ArrayList<>();
            // 4. unitpriceEntities 에서 반복문을돌려 Entity -> Dto 변환된 값을 dtos라는 객체에 담는다.
            unitpriceEntities.forEach(e -> {
                dtos.add(e.toUnitpriceDto());
            });

            // 5. 총페이지수
            int totalPages = unitpriceEntities.getTotalPages();
            // 6. 총 게시물수
            Long totalCounts = unitpriceEntities.getTotalElements(); // 요소 : 게시물 1개
            UnitPricePageDto unitPricePageDto = UnitPricePageDto.builder()
                    .dtos(dtos)
                    .totalCounts(totalCounts)
                    .totalPages(totalPages)
                    .build();
            // 7.dtos객체 리턴
            return unitPricePageDto;
    }

    @Transactional
    // 3. 거래처 재고 수정
    public boolean UnitpriceUpdate(UnitpriceDto dto){
        // 1. 수정할 엔티티 찾기 [uno 해서]
        Optional<UnitpriceEntity> unitpriceEntity = unitpriceRepository.findById(dto.getUno());
        // 2. 만약에 수정할 엔티티가 존재하면
        if(unitpriceEntity.isPresent()){
            UnitpriceEntity entity =unitpriceEntity.get();
            // 4. 엔티티 객체를 수정하면 테이블내 레코드도 같이 수정 [ * 매핑 =>ORM ]
            entity.setUname(dto.getUname());
            entity.setUoriginal(dto.getUoriginal());
            return true;
        }
        return false;
    }

    @Transactional
    // 4. 거래처 재고 삭제
    public boolean Unitpricedelete( int uno){
        // 1. 데이터베이스에서 uno라는 식별자를 사용하여 해당 식별자의 정보를 찾는 메소드 호출
        Optional<UnitpriceEntity> unitpriceEntity = unitpriceRepository.findById(uno);
        // 2. 만약에 엔티티에 uno가 존재하면
        if(unitpriceEntity.isPresent()){
            // 데이터베이스에 해당 하는 uno기 있으면 삭제한다.
            unitpriceRepository.deleteById(uno);
            return true;
        }
        return false;
    }
}
