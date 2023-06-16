package com.zano.authenticationservice.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;
import com.zano.authenticationservice.user.UserDetail;
import com.zano.authenticationservice.user.UserDetailRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserDetailRepository userDetailRepository;
    private final UserNameNotFoundExceptionSupplier usernameNotFoundExceptionSupplier;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDetailRepository.findByUserEmail(email)
                .map(UserDetail::toUserDetails)
                .orElseThrow(usernameNotFoundExceptionSupplier);
    }

    public void setAuthenticationToContext(HttpServletRequest request, UserDetails userDetails) {
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        var detailSource = new WebAuthenticationDetailsSource().buildDetails(request);
        authentication.setDetails(detailSource);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
