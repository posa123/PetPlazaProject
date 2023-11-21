package petplaza.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import petplaza.model.entity.ScheduleEntity;
@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Integer> {

//검색기능(중첩 조건문)--------------------------------------------------------------------------
    @Query(value = " select * from schedule where " +
            " if( :keyword='',true, "+ //true: 전체검색  [조건1]키워드가 공백이면 전체 출력
            " if( :key = 'stitle' , stitle like %:keyword%, " + //[조건2] 키가 제목이면
            " if( :key= 'scontent' , scontent like %:keyword%, true ) )) order by cdate desc" //[조건3] 키가 내용이면
            ,nativeQuery = true)
    Page<ScheduleEntity> findBySearch(String key, String keyword, Pageable pageable);

//완료되지 않은 일정만 가져오기----------------------------------------------------------------------



}
