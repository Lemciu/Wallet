package pl.ml.wallet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/wallet").permitAll()
                .antMatchers("/**/*.css").permitAll()
                .antMatchers("/h2-console/**").permitAll()
//                .anyRequest().authenticated();
                .anyRequest().permitAll();

//        http
//                .authorizeRequests()
////                .antMatchers("/").permitAll()
//                .antMatchers("/**/*.css").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").successForwardUrl("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

}
