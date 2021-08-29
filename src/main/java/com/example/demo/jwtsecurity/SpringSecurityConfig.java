package com.example.demo.jwtsecurity;

import com.example.demo.ajaxauth.AjaxAuthenticationProvider;
import com.example.demo.ajaxauth.AuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SIGN_UP_URL = "/registration";

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    JwtAuthenticationEntryPoint entryPoint;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/projects").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/tasks/all").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/tasks/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/project/{projectId}/tasks").permitAll()
                .antMatchers(HttpMethod.POST, "/project").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.DELETE, "/project/delete/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/task").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.POST, "/roles").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(entryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilter(new AuthenticationFilter(this.authenticationManager()));
        http.addFilter(new JwtAuthorizationFilter(this.authenticationManager(), userDetailsService));
        http.headers().cacheControl();
    }


}
