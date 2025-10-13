package com.side.giftory.user.validator.impl;

import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.validator.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistUserDTO> {

    @Override
    public boolean isValid(RegistUserDTO dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
