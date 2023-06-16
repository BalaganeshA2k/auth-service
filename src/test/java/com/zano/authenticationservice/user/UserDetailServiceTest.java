package com.zano.authenticationservice.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zano.authenticationservice.commons.exception.UserNameNotFoundExceptionSupplier;
import com.zano.authenticationservice.jwt.JwtDetailsExtractor;
import com.zano.authenticationservice.user.authority.UserAuthorityService;
import com.zano.authenticationservice.user.dto.NewUser;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {
    @Mock
    private UserDetailRepository userDetailRepository;
    @Mock
    private UserAuthorityService authorityService;
    @Mock
    private UserNameNotFoundExceptionSupplier userNameNotFoundExceptionSupplier;
    @Mock
    private JwtDetailsExtractor jwtDetailsExtractor;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailService userDetailService;
    private String mockUsername = "username";
    private String mockPassword = "password";
    private String mockEmail = "ex.xmail.com";
    private UserDetail userDetail = new UserDetail(mockUsername, mockEmail, mockPassword, Set.of());

    @Test
    void getUserDetailByEmailShouldReturnUserDetailForExistingEmail() {
        when(userDetailRepository.findByUserEmail( mockEmail))
        .thenReturn(Optional.of(userDetail));
        assertThat(userDetailService.getUserDetailByEmail( mockEmail))
        .isNotNull()
        .isEqualTo(userDetail);
    }

    @Test
    void getUserDetailByEmailShouldThrowUserNameNotFoundExpectionWhenEmailNotExist() {
        when(userDetailRepository.findByUserEmail( mockEmail))
                .thenReturn(Optional.empty());
        when(userNameNotFoundExceptionSupplier.get())
        .thenReturn( new UsernameNotFoundException("User Not Registered"));
        Assertions.assertThatThrownBy(()->{
            userDetailService.getUserDetailByEmail( mockEmail);
        })
        .isExactlyInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("User Not Registered");
    }

    private String mockAuthHeader = "ab.bc.cd";
    private NewUser mockNewUser = new NewUser(mockUsername, mockPassword);
    private String mockEncodedPassword = "passworden";
    private UserDetail mockUserDetail = UserDetail.builder()
            .userEmail(mockEmail)
            .username(mockUsername)
            .password(mockEncodedPassword)
            .authorities(Set.of())
            .build();
    @Captor
    private ArgumentCaptor<UserDetail> userDetailCaptor;

    @Test
    void saveNewUserShouldSaveUserDetailWithEncodedPasswordForValidJwt() {
        when(jwtDetailsExtractor.extractSubjectFromBearerToken(mockAuthHeader))
        .thenReturn(mockEmail);
        when(passwordEncoder.encode(mockNewUser.password()))
        .thenReturn(mockEncodedPassword);
        when(authorityService.getDefaultUserAuthority())
        .thenReturn(Set.of());
        userDetailService.saveNewUser(mockNewUser, mockAuthHeader);
        verify(userDetailRepository).save(userDetailCaptor.capture());
        assertEquals(mockUserDetail,userDetailCaptor.getValue());
    }

    @Test
    void saveNewUserShouldThrowExceptionForInValidJwt() {
        when(jwtDetailsExtractor.extractSubjectFromBearerToken(mockAuthHeader))
        .thenThrow(RuntimeException.class);
        Assertions.assertThatThrownBy(()->{
            userDetailService.saveNewUser(mockNewUser, mockAuthHeader);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    void isUserNameTakenShouldReturnTrueIfUserNameIsTaken(){
        when(userDetailRepository.existsById(any()))
            .thenReturn(true);
        assertThat(userDetailService.getAvailabilityOfUserName(mockUsername))
        .isTrue();
    }

    @Test
    void isUserNameTakenShouldReturnFalseIfUserNameIsNotTaken(){
        when(userDetailRepository.existsById(any()))
            .thenReturn(false);
        assertThat(userDetailService.getAvailabilityOfUserName(mockUsername))
        .isFalse();
    }

    @Test
    void isEmailRegisteredShouldReturnTrueIfEmailIsRegistered(){
        when(userDetailRepository.existsByUserEmail(any()))
            .thenReturn(true);
        assertThat(userDetailService.isEmailRegistered(mockEmail))
            .isTrue();
    }

    @Test
    void isEmailRegisteredShouldReturnFalseIfEmailIsRegistered(){
        when(userDetailRepository.existsByUserEmail(any()))
            .thenReturn(false);
        assertThat(userDetailService.isEmailRegistered(mockEmail))
            .isFalse();
    }
}
