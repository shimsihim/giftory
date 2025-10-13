//package com.side.giftory.user.validator.impl;
//
//import com.side.hanghae.users.validator.annotation.ValidPassword;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return false;
//        }
//
//        if (value.length() < 8 || value.length() > 15) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("비밀번호는 8자 이상, 15자 이하이어야 합니다.")
//                    .addConstraintViolation();
//            return false;
//        }
//
//        boolean hasUpper = false;
//        boolean hasLower = false;
//        boolean hasDigit = false;
//
//        for (char c : value.toCharArray()) {
//            if (Character.isUpperCase(c)) hasUpper = true;
//            else if (Character.isLowerCase(c)) hasLower = true;
//            else if (Character.isDigit(c)) hasDigit = true;
//
//            // 모두 true면 일찍 종료 가능
//            if (hasUpper && hasLower && hasDigit) {
//                break;
//            }
//        }
//
//        if (!(hasUpper && hasLower && hasDigit)) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("비밀번호는 대문자, 소문자, 숫자를 모두 포함해야 합니다.")
//                    .addConstraintViolation();
//            return false;
//        }
//
//        return true;
//    }
//}