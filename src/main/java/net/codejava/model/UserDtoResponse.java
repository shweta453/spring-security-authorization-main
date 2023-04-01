package net.codejava.model;

import lombok.*;
import net.codejava.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDtoResponse {

    private User user;
    private String msg;

}
