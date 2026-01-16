package br.com.sorveteria.sistema_sorveteria.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (!username.equals("admin")) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username("admin")
                //  HASH FIXO (NÃO gerar de novo)
                .password("$2a$12$KdhCpoeLCikcV2i7R6/NVeKd.E7IeDsCREDtA1209daRAZgRFV63.")
                .roles("USER")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
