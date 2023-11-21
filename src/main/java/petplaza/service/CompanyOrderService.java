package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petplaza.model.dto.CompanyOrderDto;
import petplaza.model.dto.CompanyOrderPageDto;
import petplaza.model.entity.CompanyOrderEntity;
import petplaza.model.repository.CompanyOrderRepository;
import petplaza.model.repository.ProductRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // 해당 클래스 Service로 사용
public class CompanyOrderService { // 발주 서비스

    // servuce -> Service -> Repository
    // servuce <- Service <- Repository
    @Autowired
    private CompanyOrderRepository companyOrderRepository;

    @Autowired
    private UnitLogueService unitLogueService;

    @Autowired
    private ProductRepository productRepository;

    // 1. 발주 등록 [C]
        // 발주 목적: 재고발주(pno)
    @Transactional
    public boolean postCompany(CompanyOrderDto companyOrderDto ){
        System.out.println("companyOrderDto = " + companyOrderDto);
        // 1. dto -> entity 변경후 repository 통한 insert 후 결과 entity 받기
        CompanyOrderEntity companyOrderEntity = companyOrderRepository.save( companyOrderDto.comEntity() );

        // pno를 가지고 엔티티를 찾는다.
            /*
               리포스토리(인터페이스) -> 엔티티를 조작함
               findById - pk를 이용해서 엔티티를 찾겠다.
               findById는 바로 엔티티를 주지 않는다.
            */
        // 발주기록
        unitLogueService.UnitLogueWrite( companyOrderDto.getPno() , companyOrderDto.getCoamount() , companyOrderDto.getCocontent() );

        return true;
    }

    // 2. 발주 호출 [R]
    // 리스트 안에 페이지 디티오를 넣어야하는거아닌가요ㅏ?
    @Transactional
    public CompanyOrderPageDto getCompany(int page , String key , String keyword , int view){
        System.out.println("getCompanyService");

        Pageable pageble = PageRequest.of(page-1 , view);
        
        // 1. 모든 발주 엔티티 호출
        Page<CompanyOrderEntity> companyOrders = companyOrderRepository.findBySearch(key , keyword , pageble);

        // 2. dto 리스트 생성
        List<CompanyOrderDto> companyOrderDtoList = new ArrayList<>();

        // 3. 반환되는 dto 여러개를 담을 리스트
        companyOrderDtoList = companyOrders.stream().map( (c) -> { return c.companyDto(); } ).collect(Collectors.toList());

        // 총 페이지 수
        int totalPages = companyOrders.getTotalPages();

        // 출력할 발주정보 수
        Long totalCount = companyOrders.getTotalElements();

        // pageDto 구성해서 axios에게 전달
        CompanyOrderPageDto companyOrderPageDto = CompanyOrderPageDto.builder()
                .companyOrderDtoList(companyOrderDtoList)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();

        return companyOrderPageDto;

    }

    // 2-2 개별 발주목록 출력
    @Transactional
    public CompanyOrderDto doGetCompany(int cono){
        System.out.println("doGetCompanyService");
        // 1. pk 번호에 해당하는 엔티티 찾기
        Optional<CompanyOrderEntity> companyOrderEntityOptional
                = companyOrderRepository.findById(cono);

        // 2. 검색된 엔티티가 존재하면
        if( companyOrderEntityOptional.isPresent() ){
            // 3. 엔티티 꺼내기
            CompanyOrderEntity companyOrderEntity = companyOrderEntityOptional.get();
            // 4. 엔티티 -> 디티오 변환
            CompanyOrderDto companyOrderDto = companyOrderEntity.companyDto();
            // 5. dto 변환
            return companyOrderDto;

        }

        return null;
    }

    // 3. 발주 수정 [U]
    @Transactional
    public boolean putCompany(CompanyOrderDto companyOrderDto){
        System.out.println("putCompanyService");
        System.out.println("companyOrderDto = " + companyOrderDto);

        // 1. 수정할 엔티티 찾기 [ cono 해서 ]
        Optional<CompanyOrderEntity> optionalCompanyOrderEntity = companyOrderRepository.findById(companyOrderDto.getCono() );

        System.out.println("optionalCompanyOrderEntity = " + optionalCompanyOrderEntity);
        // 2. 만약에 수정할 엔티티가 존재하면?
        if(optionalCompanyOrderEntity.isPresent() ){

            // 3. 엔티티 꺼내기
            CompanyOrderEntity companyOrderEntity = optionalCompanyOrderEntity.get();

            // 4. 엔티티 수정
            companyOrderEntity.setCoamount( companyOrderDto.getCoamount() );
            companyOrderEntity.setCostate( companyOrderDto.getCostate() );
            companyOrderEntity.setCocontent( companyOrderDto.getCocontent() );

        }


        return true;
    }


}// class e
