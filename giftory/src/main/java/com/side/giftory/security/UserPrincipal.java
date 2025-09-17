package com.side.giftory.security;

import com.side.giftory.security.oauth2.SocialType;
import com.side.giftory.user.domain.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@Getter
@ToString
public class UserPrincipal implements UserDetails, OAuth2User {

    private Long id;
    private String username;
    private String email;
    private String phoneNo;
    private final String password;
    private final SocialType socialType;
    private final String socialId;
    @Setter
    private RoleType roleType;
    private final Map<String,Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    //일반 로그인 시 생성
    public UserPrincipal( String username, String email , String password , String phoneNo , RoleType roleType) {
        this( username , email ,password, phoneNo ,null , "" ,roleType, Collections.emptyMap());
    }

    //소셜 로그인 시 생성
    public UserPrincipal( String username, String email ,String passWord, String phoneNo , SocialType socialType , String socialId ,RoleType roleType , Map<String,Object> attributes) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = passWord;
        this.socialType = socialType;
        this.socialId = socialId;
        this.roleType = roleType;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleType.ROLE_GUEST.name())); // 사용자 db조회 전 guest로 초기화
    }

    public void setByUser(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNo = user.getPhoneNo();
    }

    // UserDetails
    @Override
    public String getUsername() { return username; }
    @Override
    public String getPassword() { return password; } // OAuth2 로그인 시 null
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    // OAuth2User
    @Override
    public Map<String,Object> getAttributes() { return attributes; }
    @Override
    public String getName() { return String.valueOf(id); }
}
