package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petplaza.model.dto.UnitLogueDto;
import petplaza.model.dto.UnitLoguePageDto;
import petplaza.model.entity.ProductEntity;
import petplaza.model.entity.UnitLogueEntity;
import petplaza.model.repository.ProductRepository;
import petplaza.model.repository.UnitLogueEntityRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitLogueService { // 재고로그 서비스
    @Autowired
    private UnitLogueEntityRepository unitLogueEntityRepository;
    @Autowired
    private ProductRepository productRepository;

 /*   @Autowired
    private UnitLogueService unitLogueService;*/
    // unitLogueEntityRepository 객체 생성
    /*
        언제   재고가 부족할때
        어디서 발주페이지에서 등록한다
    */

    // 1. 재고로그 등록
    @Transactional
    public boolean UnitLogueWrite( int pno , int uincrease , String urecord){

        //
        Optional<ProductEntity> productEntityOptional = productRepository.findById(pno);

        ProductEntity productEntity = productEntityOptional.get();

        //1. 인자값으로 dto 만들기
        UnitLogueDto unitLogueDto = UnitLogueDto.builder()
                .uincrease(uincrease)
                .urecord(urecord)
                .build();
        //2. dto를 엔티티로 변환후 세이브
        UnitLogueEntity unitLogueEntity = unitLogueDto.toUnitLogueEntity();
        // 단방향
        unitLogueEntity.setProductEntity(productEntity);
        // 양방향
        productEntity.getUnitLogueEntityList().add(unitLogueEntity);

        unitLogueEntity = unitLogueEntityRepository.save(unitLogueEntity);

        if (unitLogueEntity.getUlno() >= 1) {
            return true;
        }
        return false;
    }
    @Transactional
    // 2. 재고로그 출력
    public UnitLoguePageDto UnitLogueAll(int page , String key , String keyword , int view ){
        Pageable pageable = PageRequest.of(page -1 , view);
        // 1. 데이터베이스에서 모든 정보를 가져온다.
        Page<UnitLogueEntity> logueEntityList = unitLogueEntityRepository.findBySearch(key , keyword , pageable);
        UnitLogueDto unitLogueDto = new UnitLogueDto();
        // 2.UnitLogueDto객체의 리스트를 생성하는 코드 [ 빈 객체 선언]
        List<UnitLogueDto> dtos = new ArrayList<>();
        // 3. logueEntityList 에서 반복문 돌려 Entity->Dto 변환된 값을 dtoList 에 담아서 리턴
        logueEntityList.forEach(e->{
            dtos.add(e.toUnitLogueDto());
        });

        // 총 페이지수
        int totalPages = logueEntityList.getTotalPages();
        // 총 게시물수
        Long totalCounts = logueEntityList.getTotalElements();
        UnitLoguePageDto unitLoguePageDto = UnitLoguePageDto.builder()
                .dtos(dtos)
                .totalCounts(totalCounts)
                .totalPages(totalPages)
                .build();
        return unitLoguePageDto;
    }

    @Transactional
    // 3. 재고로그 수정
    public boolean UnitLoguePut(UnitLogueDto logueDto) {
        System.out.println("Service dto" + logueDto);
        Optional<UnitLogueEntity> unitLogueEntity = unitLogueEntityRepository.findById(logueDto.getUlno());

        if (unitLogueEntity.isPresent()) {
            UnitLogueEntity entity = unitLogueEntity.get();

            entity.setUincrease(logueDto.getUincrease());
            entity.setUrecord(logueDto.getUrecord());

            // 여기서 수정된 엔터티를 저장해야 합니다.
            unitLogueEntityRepository.save(entity);

            System.out.println("entity" + entity);
            return true;
        }
        return false;
    }
    // 4. 재고로그 삭제
    @Transactional
    public boolean  UnitLogueDelete( int ulno){
        // 1. 데이터베이스에서 ulno 라는 식별자를 사용하여 해당 식별자의 정보를 찾는 메소드호출
        Optional<UnitLogueEntity> unitLogueEntity = unitLogueEntityRepository.findById(ulno);
    
        // 2. 만약에 엔티티에 ulno가 존재하면
        if(unitLogueEntity.isPresent()) {

            UnitLogueEntity logueEntity = unitLogueEntity.get();
            // 3. 데이터베이스에 해당 하는 ulno가 존재하면 삭제한다.
            unitLogueEntityRepository.deleteById(ulno);
            // 4. 삭제성공시 true 반환
            return true;
        }
        return false;
    }

    // 5. 현재제품재고수량
    public int nowCount( int pno ){

        Optional<ProductEntity> productEntityOptional
                = productRepository.findById( pno );
        if( productEntityOptional.isPresent() ){

            ProductEntity productEntity = productEntityOptional.get();
            int sum = 0;
            for( UnitLogueEntity log  : productEntity.getUnitLogueEntityList() ){
                sum +=log.getUincrease();
            }
            return sum;
        }
        return 0;
    }
}


