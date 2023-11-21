package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    @Query(value = " select * from member where if( :keyword = '' , true ,if(:key = 'mid' , mid like %:keyword% , if( :key = 'mname' , mname like %:keyword% , true))) order by cdate desc", nativeQuery = true )
    Page<MemberEntity> findByMember( String key , String keyword , Pageable pageable);

}
