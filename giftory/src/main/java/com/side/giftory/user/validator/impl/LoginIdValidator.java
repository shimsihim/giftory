//package com.side.giftory.user.validator.impl;
//
//import com.side.hanghae.users.validator.annotation.ValidLoginId;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class LoginIdValidator implements ConstraintValidator<ValidLoginId, String> {
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (value == null) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("아이디를 입력해주세요.")
//                    .addConstraintViolation();
//            return false;
//        }
//
//        if (value.length() < 4 || value.length() > 10) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("비밀번호는 8자 이상, 15자 이하이어야 합니다.")
//                    .addConstraintViolation();
//            return false;
//        }
//
//        boolean hasLower = false;
//        boolean hasDigit = false;
//
//        for (char c : value.toCharArray()) {
//            if (Character.isLowerCase(c)) hasLower = true;
//            else if (Character.isDigit(c)) hasDigit = true;
//
//            // 모두 true면 일찍 종료 가능
//            if (hasLower && hasDigit) {
//                break;
//            }
//        }
//
//        if (!(hasLower && hasDigit)) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("아이디는 소문자, 숫자를 모두 포함해야 합니다.")
//                    .addConstraintViolation();
//            return false;
//        }
//
//        return true;
//    }
//}