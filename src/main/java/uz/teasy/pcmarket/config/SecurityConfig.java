package uz.teasy.pcmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //SUPER_ADMIN, MODERATOR va OPERATOR
        auth
                .inMemoryAuthentication()
                .withUser("director").password(passwordEncoder().encode("director")).roles("SUPER_ADMIN").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","DELETE_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MODERATOR").authorities("ADD_PRODUCT","EDIT_PRODUCT")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("OPERATOR").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","READ_ONE_PRODUCT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/product/*").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/api/product/**").hasAnyRole("DIRECTOR", "MANAGER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
