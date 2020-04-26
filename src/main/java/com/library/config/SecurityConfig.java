package com.library.config;

import com.library.AuthenticateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private AuthenticateHandler authenticateHandler;
   @Autowired
   private DataSource dataSource; // Бин создается автоматически через @Autowired

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
      authManagerBuilder.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from library.user where username = ?")
            .authoritiesByUsernameQuery("select username, role from library.user_roles where username = ?")
            .passwordEncoder(passwordEncoder());
   }

   /**
    * Настройка ограничений доступа к страницам
    */
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/pages/directory.xhtml").hasRole("ADMIN") // К названию роли автоматически добавляется префикс ROLE_
            .antMatchers("/pages/books.xhtml").hasAnyRole("ADMIN", "USER")
            .and()
            .exceptionHandling().accessDeniedPage("/index.xhtml") // При ошибке доступа - перенаправление на страницу авторизации
            .and()
            // Отключение защиты от CSRF атак.
            // Для включения настраивается отдельный конфиг - (CsrfSecurityConfig extends WebSecurityConfigurerAdapter).
            .csrf().disable()
            // Форма аутентификации
            .formLogin()
            .loginPage("/index.xhtml")
            .failureHandler(authenticateHandler)
            .defaultSuccessUrl("/pages/books.xhtml")
            .loginProcessingUrl("/login")
            .passwordParameter("password")
            .usernameParameter("username")
            .and()
            // Выход пользователя из системы
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/index.xhtml")
            .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
            .invalidateHttpSession(true);
   }
}