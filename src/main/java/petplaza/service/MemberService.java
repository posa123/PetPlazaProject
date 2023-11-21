package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import petplaza.model.dto.MemberDto;
import petplaza.model.dto.MemberPageDto;
import petplaza.model.entity.MemberEntity;
import petplaza.model.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    // 1. C

    // 2-1. R 회원정보 전체 출력
    @Transactional
    public MemberPageDto getAllMembers( int page , int view , String key , String keyword) {
        Pageable pageable = PageRequest.of(page-1 , view);
        // 모든 회원정보 호출
        Page<MemberEntity> members = memberRepository.findByMember(key,keyword,pageable);
        // dto 리스트에 담기
        List<MemberDto> memberDtoList = new ArrayList<>();
        members.forEach( e-> {
            memberDtoList.add(e.allToDto());});
        // 페이지 처리
        // 총 페이지수
        int totalPages = members.getTotalPages();
        // 출력할 회원정보 수
        Long totalCount = members.getTotalElements();
        // pageDto 구성해서 axios에게 전달
        MemberPageDto memberPageDto = MemberPageDto.builder()
                .memberDtoList(memberDtoList)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
        // 리턴
        return memberPageDto;
    }
    // 2-2. 개별조회
    @Transactional
    public MemberDto doGet( int mno ){
        // 1. optional 이용한 pk찾기
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(mno);
        if( memberEntityOptional.isPresent()){
            MemberEntity memberEntity = memberEntityOptional.get();
            // 엔티티 dto변환
            MemberDto memberDto = memberEntity.allToDto();
            return memberDto;
        }
        return null;
    }

    // 3. U 회원정보 수정
    @Transactional
    public boolean updateMember(MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberDto.getMno());
        if( memberEntity.isPresent()){
            MemberEntity member = memberEntity.get();
            member.setMname(memberDto.getMname()); // 이름
            member.setMpwd(memberDto.getMpwd());    // 비밀번호
            member.setMphone(memberDto.getMphone()); // 전화번호
            return true;
        }
        return false;
    }
    // 4. D 회원정보 삭제
    @Transactional
    public boolean deleteMember( @RequestParam int mno ){ // mno 를 받아
        System.out.println("mno = " + mno);
        // mno를 찾고
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findById(mno);
        // 찾으면 삭제
        if(optionalMemberEntity.isPresent()){
            memberRepository.delete(optionalMemberEntity.get());
            return true;
        }
        return false;
    }
}
