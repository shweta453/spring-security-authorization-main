package net.codejava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResetRequest {

    private List<String> userNames;
    @JsonIgnore
    private String currentUser;

    @JsonIgnore
    private String authorizationHeader;


}
