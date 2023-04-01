package net.codejava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private int userId;

    private String username;        // email

    @JsonIgnore
    private String password ;

    private String isEnabled;

    private String lockStatus;

    private String tempToken;  // msg : temp password email has been sent to the user.

    private Integer failedAttempts;

    private Timestamp createdOn;

    private Timestamp modifiedOn;

    private String empId;

    private Boolean isEmailActive;



}
