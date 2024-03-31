package ru.kpfu.itis.lobanov.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.lobanov.security.AuthProvider;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan("ru.kpfu.itis.lobanov.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProvider authProvider;
    private final DaoAuthenticationProvider provider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/profile", "/favourite", "/create-post", "/edit-profile", "/about-us").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http
                .formLogin()
                .loginPage("/test")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl(ServerResources.ABOUT_US_URL, true)
                .failureUrl("/test?error=true")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies(ServerResources.CURRENT_USER, "SESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl("/test")
                .and()
                .exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
}
