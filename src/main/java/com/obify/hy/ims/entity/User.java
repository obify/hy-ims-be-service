package com.obify.hy.ims.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

  @Id
  private String id;

  @NotBlank
  @Size(max = 120)
  private String firstName;

  @NotBlank
  @Size(max = 120)
  private String lastName;

  @NotBlank
  @Size(max = 150)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;
  private Set<Role> roles = new HashSet<>();
  private boolean active;
  private String locationId;
  private String squareToken;
  private String pos;
  private String phone;
}
