package petplaza.service;
//시큐리티 사용을 위한 구현체
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import petplaza.model.dto.AdminDto;

import javax.transaction.Transactional;

@Service
public class AdminService implements UserDetailsService {
    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    //시큐리티인증처리메소드==============================
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails= User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .authorities("ROLE_ADMIN")
                .build();
       // System.out.println("userDetails> "+userDetails);
        return userDetails;

    }

    //로그인 여부 확인 메소드(=세션호출)==========================
    @Transactional
    @GetMapping
    public AdminDto getAdmin(){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(o.equals("anonymousUser")){return null;}
        else {
            UserDetails userDetails =(UserDetails)o;
            return AdminDto.builder()
                    .admin(userDetails.getUsername())
                    .build();
        }

    }




}
