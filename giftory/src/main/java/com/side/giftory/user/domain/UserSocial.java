package com.side.giftory.user.domain;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.oauth2.SocialType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
            name = "user_social",
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"social_type", "social_id"})
            }
    )
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "social_type", nullable = false , length = 20)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false , length = 80)
    private String socialId;
}
