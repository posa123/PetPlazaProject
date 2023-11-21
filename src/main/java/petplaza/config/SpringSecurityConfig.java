package petplaza.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import petplaza.service.AdminService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //보안 관련 담당 메소드================================================================================
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("http 보완관련 config함수>  = " + http);
        http.csrf().disable();//모든 http에서 csrf 사용 안함

        //0. 인증(로그인)된 인가(권한/허가) 통해 페이지 접근 제한-------------------------------------------
        http.authorizeHttpRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/" , "/schedule","/schedule/*" ,
                        "/business", "/business/*" , "/MemberList" , "MemberUpdate" ,
                        "/order" , "/orderDetail" , "/orderWrite" , "/unitprice/*" ,
                        "/unitlogue" , "/client/*" , "/companyOrder/*" , "/product/*" ).hasRole("ADMIN");



        //1. 로그인커스텀--------------------------------------------------------------------------------
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProcessingUrl")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password");


        //2. 로그아웃커스텀-------------------------------------------------------------------------------
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
        http.csrf().disable();
    }
    // 보완관련메소드끝========================================================================================

    // 인증관련메소드커스텀 ====================================================================================
    @Autowired
    private AdminService adminService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
    }


}

