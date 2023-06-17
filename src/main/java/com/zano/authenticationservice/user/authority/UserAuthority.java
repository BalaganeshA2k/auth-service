package com.zano.authenticationservice.user.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
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
  private String authority;

  public SimpleGrantedAuthority asSimpleGrantedAuthority() {
    return new SimpleGrantedAuthority(authority);
  }

}
