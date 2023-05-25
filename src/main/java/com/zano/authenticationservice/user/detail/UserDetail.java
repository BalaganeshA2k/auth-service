package com.zano.authenticationservice.user.detail;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import java.util.Set;

import com.zano.authenticationservice.authority.Authority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String userEmail;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { PERSIST, MERGE })
  @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_detail_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<Authority> authorities;

}
