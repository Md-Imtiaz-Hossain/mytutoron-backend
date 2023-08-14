package imtiaz.sktech.mytutoron.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static imtiaz.sktech.mytutoron.constant.AppConstant.DEFAULT_TOKEN_VALIDITY_SECONDS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String[] staticResources = {"/css/**", "/images/**", "/fonts/**", "/scripts/**", "/plugins/**", "/frontimages/**", "/frontlayout/**",
                " /resources/**", "/js/**", "/login", "/api/**", "/", "/static/**", "/dist/css/**", "/dist/js/**", "/dist/img/**", "/dist/**"};

        // Disable CSRF for Testing
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/forgot-password", "/change-password", "/about/**", "/login").permitAll()
                .antMatchers("/api/users/**","/api/categories/**","/api/**", "/backend/api/users/**").permitAll()
                .antMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/", true)
                .usernameParameter("email")
                .permitAll()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()

                .and()
                .rememberMe()
                .key("AbcDefgHijKlmnOpqrs_1234567890") //this will be created session id (cookies) when login
                .tokenValiditySeconds(DEFAULT_TOKEN_VALIDITY_SECONDS);

        http.addFilterAfter(new LoggedInUserFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}



//        http.csrf().disable();
//        return http.build();