package com.zano.authenticationservice.user.authority;

import static jakarta.persistence.GenerationType.SEQUENCE;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthority implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private int id;
  @Column(nullable = false, unique = true)
  private String authority;

  public SimpleGrantedAuthority asSimpleGrantedAuthority() {
    return new SimpleGrantedAuthority(authority);
  }

}
