package com.zano.authenticationservice.authority;

import static jakarta.persistence.FetchType.LAZY;

import java.util.Set;

import com.zano.authenticationservice.user.UserDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder 
public class Authority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(fetch = LAZY,mappedBy = "authorities")
  private Set<UserDetail> userDetails;
  
}
