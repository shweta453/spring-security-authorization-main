package net.codejava.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login_status")
@Builder(toBuilder = true)
public class UserLoginStatus {

    @Id
    private int userId;

    private String username;

    private int loginCount;

}
