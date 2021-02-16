package com.setge.talkingtoday.config;

import com.setge.talkingtoday.security.LoginSuccessHandler;
import com.setge.talkingtoday.security.MemberUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberUserDetailsService memberUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/member/member-info", "/movie/read", "/movie/register", "/board/**")
                .hasRole("USER")
                .antMatchers("/member/admin")
                .hasRole("ADMIN")
                .antMatchers( "/", "/**")
                .permitAll();

        http.formLogin()
                .loginPage("/customlogin")
                .loginProcessingUrl("/authenticate")
//                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .oauth2Login()
                .and()
                .rememberMe().tokenValiditySeconds(60 * 60 * 7).userDetailsService(memberUserDetailsService);

        http.logout()
                .logoutSuccessUrl("/");
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$a1tpiIedzvQcwEjravFxIuy.NzL19AYDjCSCK9Om7esB9TCYaefAS")
//                .roles("USER");
//    }
}
