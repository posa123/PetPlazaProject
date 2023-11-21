package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = " select * from ordertable where " +
            "if( :keyword = '' , true ," +
            "if(:key = 'ostate' , ostate like %:keyword% , true ))order by ono asc"

            ,nativeQuery = true)
    Page<OrderEntity> findBySearch(String key, String keyword, Pageable pageable);
}
