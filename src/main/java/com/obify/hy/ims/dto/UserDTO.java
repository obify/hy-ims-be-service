package com.obify.hy.ims.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
 
    @NotBlank
    @Email
    private String email;

    private String firstName;
    private String lastName;
    private Set<String> roles;
    private String locationId;
    private String squareToken;
    private String phone;
    private String pos;
    @NotBlank
    private String password;
}
