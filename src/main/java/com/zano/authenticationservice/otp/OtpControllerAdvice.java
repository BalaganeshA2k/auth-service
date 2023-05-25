package com.zano.authenticationservice.otp;

import static com.zano.authenticationservice.otp.TotpValidator.TotpValidationResult.EMAIL_AND_OTP_DOES_NOT_MATCH;
import static com.zano.authenticationservice.otp.TotpValidator.TotpValidationResult.OTP_EXPRIRED;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zano.authenticationservice.ApplicationErrorResponse;
import com.zano.authenticationservice.otp.exception.EmailAndOtpDoesNotMatchException;
import com.zano.authenticationservice.otp.exception.OtpExpiredException;
import com.zano.authenticationservice.otp.exception.OtpNotGeneratedException;

@RestControllerAdvice
public class OtpControllerAdvice {
    @ExceptionHandler(EmailAndOtpDoesNotMatchException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    ApplicationErrorResponse emailAndOtpDoesNotMatchExceptionHandler(EmailAndOtpDoesNotMatchException exception) {
        return new ApplicationErrorResponse(EMAIL_AND_OTP_DOES_NOT_MATCH.name());
    }

    @ExceptionHandler(OtpExpiredException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    ApplicationErrorResponse emailAndOtpDoesNotMatchExceptionHandler(OtpExpiredException exception) {
        return new ApplicationErrorResponse(OTP_EXPRIRED.name());
    }

    @ExceptionHandler(OtpNotGeneratedException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    ApplicationErrorResponse noOtpException(OtpNotGeneratedException exception) {
        return new ApplicationErrorResponse("OTP Not Generated");
    }
}
