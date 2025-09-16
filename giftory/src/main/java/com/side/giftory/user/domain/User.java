package com.side.giftory.user.domain;

import com.side.giftory.security.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Setter
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(name = "profile_url", length = 255)
    private String profileUrl;

    @Column(length = 20)
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
}
