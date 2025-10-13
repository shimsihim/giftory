//package com.side.giftory.user.validator.annotation;
//
//import com.side.hanghae.users.validator.impl.PasswordValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = PasswordValidator.class)
//@Target({ ElementType.FIELD })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidPassword {
//    String message() default "비밀번호는 8자 이상이어야 합니다.";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}