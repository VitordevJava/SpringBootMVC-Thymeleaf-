package projeto.Users.boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebConfigSecurity {

    /* Configura as solicitações de acesso por HTTP */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) /* Desativa CSRF */
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/materialize/**" , "/login").permitAll() /*Permite acesso livre a essas URLs*/ 
                .anyRequest().authenticated() /* Qualquer outra requisição precisa estar autenticada */
            )
            .formLogin(login -> login
                .loginPage("/login") /* Define a página de login personalizada */
                .defaultSuccessUrl("/", true)
                .permitAll() /* Permite qualquer usuário acessar a página de login */
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) /* Configuração de logout */
                .logoutSuccessUrl("/") /* Redireciona após logout */
                .permitAll()
            );

        return http.build();
    }

    /* Cria a autenticação do usuário em memória */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("vitor")
            .password(passwordEncoder().encode("123")) /* Senha criptografada */
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    /* Define o método de criptografia da senha */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}