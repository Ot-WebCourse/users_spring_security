package users.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import users.models.UserRoleEnum;
import users.services.UserDetailsServiceImpl;


@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    //private UserDetailsService userDetailsService;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers("/register").permitAll() // дозволити анонімним користувачам заходити на '/' 
            .antMatchers("/user-list").hasAuthority(UserRoleEnum.ADMIN.name()) //"ADMIN"
            //.antMatchers("/user-list").hasAnyRole("ROLE_ADMIN,ROLE_MODERATOR") // use only if you have roles  "ROLE_*" 
            .antMatchers("/user-profile/**").hasAnyAuthority("ADMIN,USER,MODERATOR") // 
            //.antMatchers("/user-profile/**").hasRole("ROLE_USER") // // use only if you have roles  "ROLE_*"
            .anyRequest().authenticated() // всі інші запити потребують аутентифікації
            .and()
        .formLogin()
            .loginPage("/login")  // URL логіну
            //  action з форми логіну
            .loginProcessingUrl("/login")
            // URL при невдалому логіні
            .failureUrl("/login?error")
            .usernameParameter("login") // назва параметру з логіном на формі
            .passwordParameter("password")
            .permitAll() // дозволити всім заходити на форму логіну
            .and()
        .logout()
        //.logoutUrl("/logout")
        //.logoutSuccessUrl("/login?logout")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
            .permitAll(); 
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(new BCryptPasswordEncoder()); 
    }

}
