package com.side.giftory.user.domain;

import com.side.giftory.common.BaseEntity;
import com.side.giftory.security.RoleType;
import com.side.giftory.user.dto.request.RegistUserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    /**
     * 필수 사용자 입력값 : username , password , phoneNo
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    @Setter
    private String username;


    @Column(length = 50)
    private String email;

    @Column(unique = true, length = 100)
    @Setter
    private String loginId;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String phoneNo;

    @Column(name = "profile_url", length = 255)
    private String profileUrl;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Setter
    private RoleType role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

/*    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
     => BaseEntity의 @EntityListeners(AuditingEntityListener.class) 도 대체..
 */

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }

    public void registUser(RegistUserDTO registUserDTO){
        this.loginId = registUserDTO.getLoginId();
        this.password = registUserDTO.getPassword();
        this.phoneNo = registUserDTO.getPhoneNo();
        this.email = registUserDTO.getEmail();
        this.username = registUserDTO.getName();
        this.role = RoleType.ROLE_USER;
    }

    public void updateUserInfo(RegistUserDTO registUserDTO){
        this.password = registUserDTO.getPassword();
        this.phoneNo = registUserDTO.getPhoneNo();
        this.email = registUserDTO.getEmail();
        this.username = registUserDTO.getName();
    }
}
