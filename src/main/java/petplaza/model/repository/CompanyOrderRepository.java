package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.ClientEntity;
import petplaza.model.entity.CompanyOrderEntity;

@Repository // 해당 클래스 Repository로 사용
public interface CompanyOrderRepository extends JpaRepository<CompanyOrderEntity , Integer> {

    @Query(value = " select * from companyorder where " +
            "if(:keyword = '' , true , " +
            "if(:key = 'costate' , costate like %:keyword% , " +
            "if(:key ='cocontent' , cocontent like %:keyword% , true ))) order by cono asc"
            ,nativeQuery = true)
    Page<CompanyOrderEntity> findBySearch(String key, String keyword, Pageable pageable);

}
