//package br.com.sorveteria.sistema_sorveteria.auth;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//
//        if (!"admin".equals(username)) {
//            throw new UsernameNotFoundException("Usuário não encontrado");
//        }
//
//        return User.builder()
//                .username("admin")
//                .password("1234")
//                .roles("ADMIN")
//                .build();
//    }
//}
