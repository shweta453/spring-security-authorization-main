package net.codejava.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.codejava.entity.User;
import net.codejava.entity.UserLoginStatus;
import net.codejava.model.LoginRequest;
import net.codejava.model.LoginResponse;
import net.codejava.repository.UserLoginStatusRepository;
import net.codejava.repository.UserRepository;
import net.codejava.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthService {

    @Autowired
    private UserLoginStatusRepository repository;

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserLoginStatusRepository userLoginStatusRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AuthenticationManager authenticationManager;
    public LoginResponse getResponse(LoginRequest loginRequest) {
        LoginResponse loginResponse = LoginResponse.builder().build();
        try {
            boolean b = repository.existsByUsername(loginRequest.getUsername());
            UserLoginStatus loginStatus = repository.findByUsername(loginRequest.getUsername());
            User user = userRepository.findByUsername(loginRequest.getUsername());
            UserLoginStatus userLoginStatus = UserLoginStatus.builder().build();

            if (user != null) {
                if (b == false) {
                    userLoginStatus = UserLoginStatus.builder().userId(user.getUserId()).username(user.getUsername()).loginCount(0).build();
                    userLoginStatus = userLoginStatusRepository.save(userLoginStatus);
                    loginStatus = repository.findByUsername(userLoginStatus.getUsername());
                }
                if (loginStatus.getLoginCount() < 1) {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));
                    if (authentication.isAuthenticated()) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        loginResponse = LoginResponse.builder().msg("User signed-in successfully!.").build();
                        int status = 0;
                        status = userLoginStatus.getLoginCount();
                        status = status + 1;
                        UserLoginStatus userLoginStatus1 = UserLoginStatus.builder().userId(user.getUserId()).username(user.getUsername()).loginCount(status).build();
                        userLoginStatusRepository.save(userLoginStatus1);
                    }
                } else {
                    loginResponse = LoginResponse.builder().msg("User Already logged in from another device").build();
                }
            } else {
                loginResponse = LoginResponse.builder().msg("Invalid username or password").build();
            }
        } catch (Exception e) {
            loginResponse = LoginResponse.builder().msg(" " + e.getMessage()).build();
        }
        return loginResponse;
    }

}
