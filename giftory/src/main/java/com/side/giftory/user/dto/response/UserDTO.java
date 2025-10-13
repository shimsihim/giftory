package com.side.giftory.user.dto.response;

import com.side.giftory.security.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String loginId;
    private String email;
    private String phoneNo;
    private RoleType role;
}




