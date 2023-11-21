package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import petplaza.model.dto.BusinessDto;
import petplaza.model.dto.ProductDto;
import petplaza.model.entity.ProductEntity;
import petplaza.model.entity.UnitLogueEntity;
import petplaza.model.entity.UnitpriceEntity;
import petplaza.model.repository.OrderDetailRepository;
import petplaza.model.repository.ProductRepository;
import petplaza.model.repository.UnitLogueEntityRepository;
import petplaza.model.repository.UnitpriceRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UnitpriceRepository unitpriceRepository;
    @Autowired
    private UnitLogueEntityRepository unitLogueEntityRepository;
    @Autowired
    private UnitLogueService unitLogueService;

    //1. 월별 금년도 총 매출액 (SUM(판매가격*수량) 이용 )
    @Transactional
    public List<Map<Object,Object>> getTotalSales(int year){ //JS에서 해당연도 전달 받음.
        return orderDetailRepository.findByTotalSales(year);
    }

    //2. 상품 누적 판매량
    @Transactional
    public List<Map<Object,Object>>  getSalesVolume(){
        //1. 엔티티 정보 가져오기 (상품pk별 누적 판매수량)
        List<Map<Object,Object>>products=orderDetailRepository.findByTopSalesVolume();
        return products;
    }

    //3. 인기상품 top5(누적 판매 상품 중 상위 5)
    public List <Map<Object,Object>> topSalesVolume(){
        List<Map<Object,Object>>products=orderDetailRepository.findByTopSalesVolume();
        return products;
    }

    //4.판매 상품 정보 출력
    @Transactional
    public BusinessDto saleInfo(int pno){
        Optional<UnitLogueEntity> optionalUnitLogueEntity = unitLogueEntityRepository.findById(pno);

        //상품원가
            //1. 상품 이름 가져오기
        String pname="";
        Optional<ProductEntity> optionalProductEntity=productRepository.findById(pno);
        if(optionalProductEntity.isPresent()){//엔티티 존재하면 해당 엔티티 가져오기
            ProductEntity productEntity=optionalProductEntity.get();
            //entity->dto 변환
            ProductDto productDto=productEntity.allDto();
            //제품명 변수 대입
            pname= productDto.getPname();
        }
            //2. 동일한 이름의 제품의 원가 가져오기
        String cost="";
        List<UnitpriceEntity>optionalUnitpriceEntityList=unitpriceRepository.findAll();
        for(UnitpriceEntity e:optionalUnitpriceEntityList){
            if(e.getUname().equals(pname)){//반복문을 사용해 상품명과 재고물품의 이름이 동일한 제품 찾음
               //원가변수에 대입
                cost= e.toUnitpriceDto().getUoriginal();
               break;
            }
        }
        //상품가격(상품정보가져옴)
        int pprice= orderDetailRepository.findBySalesPrice(pno);

        //재고(로그를 사용한 재고 수량 구하기)
        int stock= unitLogueService.nowCount(pno);

        //누적판매량(재고로그에 없는 상품을 위해 안전성을 위한 Optional 사용. )
        Optional<Integer> salesVolume=orderDetailRepository.findBySalesVolume(pno);

        //새로운 DTO로 묶어 전달
        BusinessDto businessDto=BusinessDto.builder()
                        .pname(pname)
                        .pprice(pprice)
                        .salesVolume( salesVolume.isPresent() ? -salesVolume.get() : 0 ) //재고기록이 존재하지 않으면 0개로 표시.
                        .cost(cost)
                        .stock(stock)
                        .build();
        return businessDto;
    }


}
