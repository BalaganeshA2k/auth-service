package com.zano.authenticationservice.user;

import static jakarta.persistence.CascadeType.MERGE;

import java.util.Set;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.zano.authenticationservice.user.authority.UserAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetail {
  @Id
  private String username;

  @Column(unique = true, nullable = false)
  private String userEmail;
  @Column(nullable = false)

  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = { MERGE })
  @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "username"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority"))
  private Set<UserAuthority> authorities;

  public UserDetails toUserDetails() {
    return User.builder()
        .username(getUsername())
        .password(getPassword())
        .authorities(getAuthorities())
        .build();
  }

}
