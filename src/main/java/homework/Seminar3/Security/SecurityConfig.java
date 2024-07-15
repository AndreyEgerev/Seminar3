//package homework.Seminar3.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
////        return httpSecurity
////                .authorizeHttpRequests(authorization -> authorization
////                        .requestMatchers("/ui/readers/**").hasAnyAuthority("reader", "admin")
////                        .requestMatchers("/ui/books/**").authenticated()
////                        .requestMatchers("/ui/issues/**").hasAuthority("admin")
////                        .anyRequest().permitAll())
////                .formLogin(Customizer.withDefaults())
////                .build();
//        return httpSecurity
//                .authorizeHttpRequests(authorization -> authorization
//                        .anyRequest().permitAll())
//                .build();
//    }
//}
