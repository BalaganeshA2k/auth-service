package com.zano.authenticationservice.authority;


public record AuthorityRequest(String authorityName){
    
    Authority toAuthority(){
        return Authority.builder()
            .name(authorityName)
            .build();
    }
} 
