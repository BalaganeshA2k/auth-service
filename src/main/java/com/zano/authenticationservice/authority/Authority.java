package com.zano.authenticationservice.authority;

import static jakarta.persistence.FetchType.LAZY;
import java.util.Set;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import com.zano.authenticationservice.user.UserDetail;

import jakarta.persistence.Column;
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
public class Authority implements GrantedAuthority{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true,nullable = false)
  private String name;
  @ManyToMany(fetch = LAZY,mappedBy = "authorities")
  @Lazy
  private Set<UserDetail> userDetails;
  @Override
  public String getAuthority() {
    return this.name;
  }

}
