package com.side.giftory.gifticon.domain;

import com.side.giftory.group.domain.Group;
import com.side.giftory.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "gifticon_shares")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GifticonShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id", nullable = false)
    private Gifticon gifticon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_user_id", nullable = false)
    private User shareUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}