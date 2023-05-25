package com.zano.authenticationservice.security;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {
    @Value("${password.length.min}")
    private int minimumPasswordLength;

    @Value("${password.length.max}")
    private int maximumPasswordLength;

    @Bean
    LengthRule lengthRule() {
        return new LengthRule(minimumPasswordLength, maximumPasswordLength);
    }

    @Bean
    CharacterRule upperCaseRule() {
        return new CharacterRule(EnglishCharacterData.UpperCase);
    }

    @Bean
    CharacterRule lowerCaseRule() {
        return new CharacterRule(EnglishCharacterData.LowerCase);
    }

    @Bean
    CharacterRule digitRule() {
        return new CharacterRule(EnglishCharacterData.Digit);
    }

    @Bean
    CharacterRule specialRule() {
        return new CharacterRule(EnglishCharacterData.Special);
    }

    @Bean
    PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
