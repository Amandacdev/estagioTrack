package br.edu.ifpb.pweb2.estagiotrack.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**","/auth/login", "/acesso-negado","/alunos/form","/empresas/form","/alunos/save","/empresas/save").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth")
                        .defaultSuccessUrl("/home",true)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/auth/logout"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);

        UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN","ALUNO","EMPRESA").build();
        UserDetails estagioTrack = User.withUsername("estagioTrack").password(passwordEncoder().encode("estagioTrack")).roles("ADMIN").build();
        UserDetails amanda = User.withUsername("amanda").password(passwordEncoder().encode("amanda")).roles("ALUNO").build();
        UserDetails george = User.withUsername("george").password(passwordEncoder().encode("george")).roles("ALUNO").build();
        UserDetails brian = User.withUsername("brian").password(passwordEncoder().encode("brian")).roles("ALUNO").build();
        UserDetails olivia = User.withUsername("olivia").password(passwordEncoder().encode("olivia")).roles("ALUNO").build();

        if(!jdbcUserDetailsManager.userExists(admin.getUsername())){
            jdbcUserDetailsManager.createUser(admin);
            jdbcUserDetailsManager.createUser(estagioTrack);
            jdbcUserDetailsManager.createUser(amanda);
            jdbcUserDetailsManager.createUser(george);
            jdbcUserDetailsManager.createUser(brian);
            jdbcUserDetailsManager.createUser(olivia);

        }

        return jdbcUserDetailsManager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jdbcUserDetailsManager(dataSource)); // Usar o JdbcUserDetailsManager aqui
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
