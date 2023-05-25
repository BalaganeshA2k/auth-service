package com.zano.authenticationservice.user;

import java.util.Set;

import com.zano.authenticationservice.ApplicationRoles;
import com.zano.authenticationservice.authority.Authority;


public record UserSignInRequest (String username,String password){
}
