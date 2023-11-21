package petplaza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.MemberDto;
import petplaza.model.dto.MemberPageDto;
import petplaza.service.MemberService;

import java.util.List;

@RestController // responsebody
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 1. Cdd

    // 2. R 회원정보 출력
    @GetMapping("/get")
    public MemberPageDto getAllMembers(@RequestParam int page , @RequestParam int view , @RequestParam String key , @RequestParam String keyword) {
        return memberService.getAllMembers(page,view,key,keyword);
     }
    // 2-1 개별 조회
    @GetMapping("/doGet")
    public MemberDto doGet(@RequestParam int mno){
        return memberService.doGet(mno);

    }

    // 3. U 회원정보 수정
    @PutMapping("/put")
    public boolean updateMember(  MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        boolean result = memberService.updateMember(memberDto);
        return result;
    }
    // 4. D 회원정보 삭제
    @DeleteMapping("/delete")
    public boolean deleteMember( @RequestParam int mno ){
        System.out.println("mno = " + mno);
        boolean result = memberService.deleteMember(mno);
        return result;
    }
}
