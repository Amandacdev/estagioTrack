package br.edu.ifpb.pweb2.estagiotrack.config;

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

        UserDetails admin = User.withUsername("admin@gmail.com").password(passwordEncoder().encode("admin")).roles("ADMIN","ALUNO","EMPRESA").build();
        UserDetails estagioTrack = User.withUsername("estagiotrack@gmail.com").password(passwordEncoder().encode("estagioTrack")).roles("ADMIN").build();

        //Inserindo em Users alunos do insert
        UserDetails amanda = User.withUsername("amanda@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails george = User.withUsername("george@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails brian = User.withUsername("brian@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails olivia = User.withUsername("olivia@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails mariana = User.withUsername("mariana@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails daniel = User.withUsername("daniel@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails sofia = User.withUsername("sofia@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails andre = User.withUsername("andre@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails patricia = User.withUsername("patricia@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();
        UserDetails luana = User.withUsername("luana@gmail.com").password(passwordEncoder().encode("123")).roles("ALUNO").build();

        //Inserindo em Users empresas do insert
        UserDetails TechInnovateLTDA = User.withUsername("contato@techinnovate.com").password(passwordEncoder().encode("senhaSegura123")).roles("EMPRESA").build();
        UserDetails WebSolutionsLTDA = User.withUsername("contato@websolutions.com").password(passwordEncoder().encode("senhaSegura456")).roles("EMPRESA").build();
        UserDetails CloudDataSA = User.withUsername("contato@clouddata.com").password(passwordEncoder().encode("senhaSegura789")).roles("EMPRESA").build();
        UserDetails NextGenITLTDA = User.withUsername("contato@nextgenit.com").password(passwordEncoder().encode("senhaSegura101")).roles("EMPRESA").build();
        UserDetails CyberTechSecurityLTDA = User.withUsername("contato@cybertech.com").password(passwordEncoder().encode("senhaSegura202")).roles("EMPRESA").build();
        UserDetails DevMastersLTDA = User.withUsername("contato@devmasters.com").password(passwordEncoder().encode("senhaSegura303")).roles("EMPRESA").build();
        UserDetails DataAnalyticsLTDA = User.withUsername("contato@dataanalytics.com").password(passwordEncoder().encode("senhaSegura404")).roles("EMPRESA").build();


        if(!jdbcUserDetailsManager.userExists(admin.getUsername())){
            jdbcUserDetailsManager.createUser(admin);
            jdbcUserDetailsManager.createUser(estagioTrack);
            jdbcUserDetailsManager.createUser(amanda);
            jdbcUserDetailsManager.createUser(george);
            jdbcUserDetailsManager.createUser(brian);
            jdbcUserDetailsManager.createUser(olivia);
            jdbcUserDetailsManager.createUser(mariana);
            jdbcUserDetailsManager.createUser(daniel);
            jdbcUserDetailsManager.createUser(sofia);
            jdbcUserDetailsManager.createUser(andre);
            jdbcUserDetailsManager.createUser(patricia);
            jdbcUserDetailsManager.createUser(luana);
            jdbcUserDetailsManager.createUser(TechInnovateLTDA);
            jdbcUserDetailsManager.createUser(WebSolutionsLTDA);
            jdbcUserDetailsManager.createUser(CloudDataSA);
            jdbcUserDetailsManager.createUser(NextGenITLTDA);
            jdbcUserDetailsManager.createUser(CyberTechSecurityLTDA);
            jdbcUserDetailsManager.createUser(DevMastersLTDA);
            jdbcUserDetailsManager.createUser(DataAnalyticsLTDA);
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
