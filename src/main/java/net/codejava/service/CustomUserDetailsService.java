package net.codejava.service;

import javassist.NotFoundException;
import net.codejava.entity.Role;
import net.codejava.entity.User;
import net.codejava.model.UserDto;
import net.codejava.model.UserDtoResponse;
import net.codejava.repository.RoleRepository;
import net.codejava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override//UserDetails is an interface so implement it
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("User not found!" +username);
        }
        else{
            return new MyUserDetails(user);
        }
    }

    public UserDtoResponse save(UserDto user) throws SQLException {
        UserDtoResponse utr=UserDtoResponse.builder().build();
        boolean u=userRepository.existsByUsername(user.getUsername());
        if (u==false) {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setEnabled(true);
            newUser = userRepository.save(newUser);
            setRoles(user.getRoles(), newUser);
            utr=UserDtoResponse.builder().user(newUser).msg("User Registered Successfully...!").build();
        }else {
            utr=UserDtoResponse.builder()
                    .msg("Username Already taken")
                    .build();
        }
        return utr;
    }
    public void setRoles(Set<Role> roles, User user) throws SQLException {
        for (Role r:roles) {
            Role r1=roleRepository.findByName(r.getName().toUpperCase());
            Optional<User> u=userRepository.findByUserId(user.getUserId());
            User u1=u.get();
            if(r1!=null && u1!=null){
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales", "root",
                        "root");
                CallableStatement callableStatement = con.prepareCall("{call insertintousers_roles(?,?)}");

                callableStatement.setLong(1,u1.getUserId());
                callableStatement.setLong(2,r1.getId());
                callableStatement.executeUpdate();
            }
        }
    }

    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new NotFoundException("cannot find any user with email "+email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}
