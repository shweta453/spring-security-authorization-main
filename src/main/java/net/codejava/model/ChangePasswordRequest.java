package net.codejava.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChangePasswordRequest {
    private String password;
    private String tempPassword;
    private String currentUserId;
}
