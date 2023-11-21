package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import petplaza.model.entity.UnitLogueEntity;
import petplaza.model.entity.UnitpriceEntity;

import java.util.Optional;

public interface UnitLogueEntityRepository extends JpaRepository<UnitLogueEntity, Integer> {
    /* UnitpriceEntity finByUname(String uname);*/
    Optional<UnitLogueEntity> findByUrecord (String urecord);

    boolean existsByUrecord (String urecord);
    //  List<UnitpriceEntity> finByUnameAll(String uname);
    // + 페이징처리
    Page<UnitLogueEntity> findByUrecord (String urecord , Pageable pageable);
    @Query(value = "select * from unitlogue where urecord like %:keyword%", nativeQuery = true)
    Page<UnitLogueEntity> findBySearch(String key , String keyword ,Pageable pageable);


    //단가기록increase가 +면 수량*제품명의 제품가격
    //단가 -면 수량*제품명의원가
    //동일 제품명끼리 합산한 가격.
    //제품명-가격


}
