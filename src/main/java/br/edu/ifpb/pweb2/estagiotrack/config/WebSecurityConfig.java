package br.edu.ifpb.pweb2.estagiotrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**","/auth/login", "/acesso-negado","/alunos/form","/empresas/form").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth")
                        .defaultSuccessUrl("/home",true)
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/auth/logout"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails amanda = User.withUsername("amanda").password(passwordEncoder().encode("amanda")).roles("CLIENTE").build();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("CLIENTE", "ADMIN").build();
        UserDetails george = User.withUsername("george").password(passwordEncoder().encode("george")).roles("CLIENTE").build();
        UserDetails brian = User.withUsername("brian").password(passwordEncoder().encode("brian")).roles("CLIENTE").build();
        UserDetails olivia = User.withUsername("olivia").password(passwordEncoder().encode("olivia")).roles("CLIENTE").build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if(!users.userExists(amanda.getUsername())){
            users.createUser(amanda);
            users.createUser(admin);
            users.createUser(george);
            users.createUser(brian);
            users.createUser(olivia);
        }
        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}