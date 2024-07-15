//package homework.Seminar3.Security;
//
//import homework.Seminar3.model.Member;
//import homework.Seminar3.repository.UsersRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//    @Autowired
//    UsersRepository usersRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = usersRepository.findByName(username).
//                orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
//        log.info(member.toString());
//        return new User(member.getName(),passwordEncoder.encode(member.getPass()),
//                List.of(new SimpleGrantedAuthority(member.getRoles())));
//    }
//}
