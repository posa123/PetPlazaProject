package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.ClientEntity;
import petplaza.model.entity.ProductEntity;

@Repository // 해당 클래스 Repository로 사용
public interface ClientRepository extends JpaRepository<ClientEntity , Integer > { // 거래처 Repository


    @Query(value = " select * from client where " +
            "if(:keyword = '' , true ," +
            "if(:key = 'cname' , cname like %:keyword% , true ))order by cno asc"
            ,nativeQuery = true)
    Page<ClientEntity> findBySearch(String key, String keyword, Pageable pageable);

}
