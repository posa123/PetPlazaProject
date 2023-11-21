package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.SchedulePageDto;
import petplaza.model.dto.ScheduleDto;
import petplaza.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    //일정등록
    @PostMapping("/register")
    public boolean registerSchedule(ScheduleDto dto){
        System.out.println("ScheduleController.registerSchedule");
        System.out.println("dto = " + dto);
        return scheduleService.registerSchedule(dto);
    }

    //일정전체출력
    @GetMapping("/list")
    public SchedulePageDto allSchedule(
                                        @RequestParam int page,
                                        @RequestParam String key,
                                        @RequestParam String keyword
                                        )
    {
        System.out.println("일정출력컨트롤러");
        return scheduleService.allSchedule(page,key,keyword);
    }

    //일정수정
    @PutMapping("/put")
    public boolean updateSchedule(ScheduleDto dto){
        System.out.println("수정컨트롤러");
        System.out.println("dto = " + dto);
        return scheduleService.updateSchedule(dto);
    }
    //일정삭제
    @DeleteMapping("/delete")
    public boolean deleteSchedule(@RequestParam int sno){
        System.out.println("ScheduleController.deleteSchedule");
        System.out.println("sno = " + sno);
        return scheduleService.deleteSchedule(sno);
    }

    //개별출력
    @GetMapping("/doGet")
    public ScheduleDto doGet(@RequestParam int sno){ //axios param으로 전달 받음
        System.out.println("ScheduleController.doGet");
        System.out.println("sno = " + sno);

        return scheduleService.doGet(sno);
    }



}

/*
 @PutMapping() 안에 아무것도 안써주면 get 호출됨.
*/