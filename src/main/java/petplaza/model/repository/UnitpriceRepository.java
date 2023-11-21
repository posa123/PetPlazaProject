package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.ClientEntity;
import petplaza.model.entity.UnitpriceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitpriceRepository extends JpaRepository<UnitpriceEntity , Integer> {

   /* UnitpriceEntity finByUname(String uname);*/
    Optional<UnitpriceEntity> findByUname(String uname);

    boolean existsByUname(String uname);
   //  List<UnitpriceEntity> finByUnameAll(String uname);
    // + 페이징처리
    Page<UnitpriceEntity> findByUname(String uname , Pageable pageable);
    @Query(value ="select * from unitprice where uname like %:keyword%", nativeQuery = true)
    Page<UnitpriceEntity> findBySearch(String key , String keyword ,Pageable pageable);
}
