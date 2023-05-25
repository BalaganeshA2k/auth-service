package com.zano.authenticationservice.otp;

import jakarta.validation.constraints.Email;

public record TotpRequest(@Email String email, String otp) {

}
