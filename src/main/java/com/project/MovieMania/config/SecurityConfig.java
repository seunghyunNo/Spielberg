package com.project.MovieMania.config;

import com.project.MovieMania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private UserService userService;


    @Autowired
    private PrincipalDetailsService principalDetailsService;

//    @Bean
//    public PasswordEncoder encoder(){
//        return new BCryptPasswordEncoder();
//    }

    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/user/changePw","/user/profileUpdate","/user/myPage").hasAnyRole("MEMBER","ADMIN")
                        .requestMatchers("/user/login","/user/register","user/findUsername","user/findPw").anonymous()
                        .requestMatchers("/admin/movie","/admin/show","/admin/user","admin/report").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form->form
                        .loginPage("/user/login") // 로그인 상황 시 url 로 request 발생
                        .loginProcessingUrl("/user/login")  // "/user/login" url 로 POST request 시 시큐리티가 대신 로그인해줌
                        .defaultSuccessUrl("/") // 직접 /login -> /loginOk 에서 성공하면 / 로 이동

                        // 로그인 성공 시 홈으로 이동
                        .successHandler(new CustomLoginSuccessHandler("/"))

                        // 로그인 실패 시
                        .failureHandler(new CustomLoginFailureHandler())

                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/user/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .invalidateHttpSession(true)    //
                        .deleteCookies("JSESSIONID")    //쿠키 삭제
                )

                .rememberMe(rememberMe-> rememberMe
                        .key("key")     // 토큰 키 값
                        .rememberMeParameter("rememberMe")  // 파라미터 값
                        .tokenValiditySeconds(60*60*24*7)   // 토큰 유효시간 1주일
                        .userDetailsService(principalDetailsService)
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler())   // 접근권한
                )
                .build();

    }


}
