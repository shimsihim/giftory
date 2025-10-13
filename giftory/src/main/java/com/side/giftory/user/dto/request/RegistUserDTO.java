package com.side.giftory.user.dto.request;

import com.side.giftory.user.validator.annotation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@PasswordMatches
public class RegistUserDTO {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하로 입력해주세요.")
    private String name;

    // 2. 로그인 ID (loginId)
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 영어 소문자와 숫자만 사용 가능합니다.")
    private String loginId;

    // 3. 비밀번호 (password)
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자리여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호와 동일한 문자를 입력해주세요.")
    private String confirmPassword;

    // 4. 이메일 (email)
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @Size(max = 30, message = "이메일은 30자 이하로 입력해주세요.")
    private String email;

    // 5. 전화번호 (phoneNo)
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^(01[016789])-?([0-9]{3,4})-?([0-9]{4})$",
            message = "유효하지 않은 전화번호 형식입니다.")
    private String phoneNo;
}
