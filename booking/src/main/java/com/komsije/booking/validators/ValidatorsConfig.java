package com.komsije.booking.validators;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorsConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
