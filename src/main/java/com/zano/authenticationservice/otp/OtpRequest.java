package com.zano.authenticationservice.otp;

import jakarta.validation.constraints.Email;

public record OtpRequest(@Email String email, String otp) {

}
