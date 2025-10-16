package com.side.giftory.security;

public enum RoleType {
    ROLE_ADMIN,  // 관리자 권한
    ROLE_USER,   // 회원가입 완료
    ROLE_GUEST   // 소셜 로그인만 한 임시 권한 => 별도의 회원가입 필요 
}
