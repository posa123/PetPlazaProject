package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.ProductEntity;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    @Query(value = " select * from product where " +
            "if( :keyword = '' , true ," +
            "if(:key = 'pname' , pname like %:keyword% , true ))order by pprice asc"

    ,nativeQuery = true)
    Page<ProductEntity> findBySearch(String key, String keyword, Pageable pageable);


}
