package com.spring.SecurityConfig;


import com.spring.service.userDetailsServicelmpl;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class sercurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private userDetailsServicelmpl userDetailsServicelmpl;

    @Autowired
    private MySuccessHandler mySuccessHandler;

    @Autowired
    private MyFailureHandler myFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServicelmpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/tologin").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/tologin")
                .loginProcessingUrl("/login")
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)

                .and()
                .logout()
                .logoutUrl("/loginout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)//清除身份认证信息，默认为true
                .invalidateHttpSession(true)//是否使session失效，默认为true

                .and()
                .rememberMe()
                .rememberMeParameter("RememberMe")
                .tokenValiditySeconds(60*60*24*7);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源过滤
        web.ignoring().antMatchers("/static/**");
    }
}
