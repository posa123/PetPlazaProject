package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.OrderDetailEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
    // 1. 주문관리 페이지에서 해당 주문번호 리스트 중 한 가지를 눌렀을때 그 주문번호를 가지는 상세 관리 리스트만을 출력하기 위한 메소드
    // - 상세관리가 주문관리를 참조하고 있기에 상세관리는 주문관리번호인 ono를 fk로 가짐.
    List<OrderDetailEntity> findByOrderEntityOno(int ono);



    //1. 금년도 총 매출액 출력 [231115_고연진]
    @Query(value = "select month(cdate) as month, sum(damount*dprice) as monthSales from orderdetail" +
            " where year(cdate)= :year " +
            "group by month(cdate)" , nativeQuery = true)
    List <Map<Object,Object>> findByTotalSales(int year);

    //2. 제품별 누적 판매량(많은거에서 적은걸로)
    @Query(value = "select pno,sum(uincrease) as amount from unitlogue where uincrease<0  group by pno order by amount asc",nativeQuery = true)
    List<Map<Object,Object>> findByGetSalesVolume();

    //3. 인기상품 top5
    @Query(value = "select p.pname,sum(unit.uincrease) as amount from unitlogue unit inner join product as p on unit.pno=p.pno and unit.uincrease<0 group by p.pno order by amount asc limit 5",nativeQuery = true)
    List<Map<Object,Object>> findByTopSalesVolume();

    //3. 특정 제품의 누적 판매량(null값 대비를 위해 Optinal 사용)
    @Query(value = "select sum(uincrease) as amount from unitlogue where uincrease<0 and pno= :pno  group by pno",nativeQuery = true)
    Optional<Integer> findBySalesVolume(int pno);

    //4. 특정 제품의 제품명
    @Query(value = "select pprice from product where pno= :pno",nativeQuery = true)
    int findBySalesPrice(int pno);
    
//    // 5. 상세 주문 페이징 처리
//    @Query(value = "select * from orderdetail where pno like %:keyword%", nativeQuery = true)
//    Page<OrderDetailEntity> findBySearch(String key , String keyword , Pageable pageable, List<OrderDetailEntity> orderDetail);
}//c


