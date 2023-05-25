package com.zano.authenticationservice.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @Builder @AllArgsConstructor
@Entity @Table(name = "user_details")
public class UserDetail {
  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true,nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;
  
}
