package com.side.giftory.gifticon.domain;

import com.side.giftory.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gifticon_usages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GifticonUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifticon_id", nullable = false)
    private Gifticon gifticon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount_used")
    private BigDecimal amountUsed;

    @Column(name = "used_at", nullable = false)
    private LocalDateTime usedAt;
}
