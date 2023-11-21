package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petplaza.model.dto.SchedulePageDto;
import petplaza.model.dto.ScheduleDto;
import petplaza.model.entity.ScheduleEntity;
import petplaza.model.repository.ScheduleRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

@Transactional
    //1. 일정등록
    public boolean registerSchedule(ScheduleDto dto){
        // 전달 받은 DTO를 ENTITY로 변환
            ScheduleEntity scheduleEntity = scheduleRepository.save(dto.toEntity());
            if (scheduleEntity.getSno() >= 1) {//레코드가 존재하면 true
                return true;
            }
        return false;
    }

    //2.일정전체출력 (전달 받은 페이지에 따라 출력되는 내용이 달라짐!)
@Transactional
    public SchedulePageDto allSchedule(int page, String key, String keyword){
        //JPA페이징처리 라이브러리 사용
        Pageable pageable= PageRequest.of(page-1,5);
        //등록된 모든 일정 조회 (JPA 사용을 위해 List->Page로 변경)
        Page<ScheduleEntity> scheduleEntities= scheduleRepository.findBySearch(key,keyword,pageable);
        //ENTITY-> DTO 변환 (리스트 생성)
        List<ScheduleDto> scheduleDtos= new ArrayList<>();
        scheduleEntities.forEach(e->{
            scheduleDtos.add(e.ToDto());
            });

        //총페이지수(페이징처리시 페이지 넘버 필요)
        int totalPages=scheduleEntities.getTotalPages();
        //총게시물수
        Long totalCount = scheduleEntities.getTotalElements();
        //DTO로 묶어서 반환
        SchedulePageDto pageDto= SchedulePageDto.builder()
                .scheduleDtos(scheduleDtos)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
        return pageDto;

    }

    //3. 일정수정
    @Transactional
    public boolean updateSchedule(ScheduleDto dto){
        //해당 엔티티 찾기
        Optional<ScheduleEntity> optionalScheduleEntity= scheduleRepository.findById(dto.getSno());
        if(optionalScheduleEntity.isPresent()){//엔티티가 존재한다면, 각 필드명 수정
            ScheduleEntity scheduleEntity=optionalScheduleEntity.get();
            scheduleEntity.setSclass(dto.getSclass());
            scheduleEntity.setStitle(dto.getStitle());
            scheduleEntity.setScontent(dto.getScontent());
            scheduleEntity.setSstate(dto.getSstate());
            scheduleEntity.setSstart(dto.getSstart());
            scheduleEntity.setSend(dto.getSend());
            return true;
        }
        return false;
    }
    //4. 일정삭제
@Transactional
    public boolean deleteSchedule(int sno) {
        //해당 엔티티 찾기
        Optional<ScheduleEntity> optionalScheduleEntity=scheduleRepository.findById(sno);
        if(optionalScheduleEntity.isPresent()){//존재하면 DELETE
            scheduleRepository.deleteById(sno);
            return true;
        }

        return false;
    }

    //5.일정개별출력(내용가져오기)
    @Transactional
    public ScheduleDto doGet(int sno){//js에서 쿼링스트링 방식으로 전달
        // 전달 받은 sno의 엔티티 찾기
        Optional<ScheduleEntity> optionalScheduleEntity=scheduleRepository.findById(sno);
        if(optionalScheduleEntity.isPresent()){//레코드가 존재하면
            //가져온 내용
            ScheduleEntity scheduleEntity= optionalScheduleEntity.get();
            //entity->dto 변환
            ScheduleDto scheduleDto=scheduleEntity.ToDto();
            return scheduleDto;
        }
    return null;
    }


}
