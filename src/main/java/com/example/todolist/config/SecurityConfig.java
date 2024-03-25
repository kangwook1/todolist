package com.example.todolist.config;

import com.example.todolist.exception.CustomAccessDeniedHandler;
import com.example.todolist.repository.AccessTokenRepository;
import com.example.todolist.security.JwtAuthenticationFilter;
import com.example.todolist.security.JwtExceptionFilter;
import com.example.todolist.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccessTokenRepository accessTokenRepository;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());

    }

    /*스프링 시큐리티 3.0이상 버전에선 h2.console.enabled=true로 설정해놓으면 서블릿 컨텍스트에 h2-console이 등록되어
    dispatcherServlet과 매핑이 혼동되어 어떤 서블릿을 사용해야하는지 알수 없어 오류가 생긴다.
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        http
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(new MvcRequestMatcher(introspector, "/member/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/auth/reissue")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(introspector, "/mail/**")).permitAll()
                        //스프링 시큐리티는 자동으로 Role_접두어를 붙여준다.
                        .requestMatchers(new MvcRequestMatcher(introspector, "/**")).hasRole("USER")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*
                    UsernamePasswordAuthenticationFilter 전에 JwtAutenticationFilter를 실행
                    UsernamePasswordAuthenticationFilter는 form형식의 인증을 할 때 인증정보가 없거나 틀리면 로그인페이지로 리다이렉트하는 필터이다.
                    따라서 jwt인증필터를 이 필터 앞에서 실행시켜 토큰에 문제가 있으면 예외를 던져야한다.
                    jwt 토큰을 사용하면 UsernamePasswordAuthenticaitionFilter 이후의 필터는 통과된 것을 본다.
                */
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, accessTokenRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
                .exceptionHandling((exceptionconfig)-> exceptionconfig.accessDeniedHandler(customAccessDeniedHandler));



        //csrf:사이트간 위조 요청, disalbel 한 이유: jwt는 서버에 인증정보를 저장하지 않기 때문에 필요 없음.
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();

    }
}
