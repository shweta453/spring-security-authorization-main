package net.codejava.controller;

import lombok.SneakyThrows;
import net.codejava.entity.UserLoginStatus;
import net.codejava.model.*;
import net.codejava.repository.UserLoginStatusRepository;
import net.codejava.service.CustomUserDetailsService;
import net.codejava.service.LoginService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class UserManagementController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserLoginStatusRepository userLoginStatusRepository;

    //save user
    @PostMapping("/register")
    public UserDtoResponse saveUser(@Valid @RequestBody UserDto user) throws Exception {
        UserDtoResponse userDtoResponse = customUserDetailsService.save(user);
        return userDtoResponse;
    }

    //login api
    @PostMapping("/login")
    private LoginResponse loginResponse(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.getResponse(loginRequest);
    }

    @GetMapping("/logout1")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            UserLoginStatus uls = userLoginStatusRepository.findByUsername(auth.getName());
            if (uls != null) {
                UserLoginStatus uls1 = UserLoginStatus.builder().userId(uls.getUserId()).username(uls.getUsername()).loginCount(0).build();
                userLoginStatusRepository.save(uls1);
            }
        }
        return "logged out";
        /*return "redirect:/";*/
    }

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerServices customerService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {

    }

    @PostMapping("/forgot_password")
    public String processForgotPassword() {
    }

    public void sendEmail(){

    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm() {

    }

    @PostMapping("/reset_password")
    public String processResetPassword() {

    }

}