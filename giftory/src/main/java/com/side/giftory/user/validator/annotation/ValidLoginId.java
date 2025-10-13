//package com.side.giftory.user.validator.annotation;
//
//import com.side.hanghae.users.validator.impl.LoginIdValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = LoginIdValidator.class)
//@Target({ ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidLoginId {
//    String message() default "아이디 형식이 맞지 않습니다.";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}