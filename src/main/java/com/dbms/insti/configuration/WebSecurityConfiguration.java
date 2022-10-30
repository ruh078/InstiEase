package com.dbms.insti.configuration;


import com.dbms.insti.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }*/

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers( "/register").permitAll()
        						.antMatchers("/admin", "/admin/*").access("hasAuthority('1')")
        						.antMatchers("/medical", "/medical/*").access("hasAuthority('2')")
        						.antMatchers("/student", "/student/*").access("hasAuthority('3')")
        						.antMatchers("/warden", "/warden/*").access("hasAuthority('4')")
        						.antMatchers("/mess", "/mess/*").access("hasAuthority('5')")
        						.antMatchers("/washerman", "/washerman/*").access("hasAuthority('6')");
        						
        http.authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .usernameParameter("email_id").passwordParameter("psw")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");

    }

}