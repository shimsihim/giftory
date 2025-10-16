package com.side.giftory.gifticon.domain;

import com.side.giftory.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "gifticons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gifticon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String brand;

    @Column(name = "product_name", length = 100)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private GifticonType type;

    @Column(name = "image_path", length = 100)
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private GifticonStatus status;

    @Column(name = "remaining_amount")
    private Integer remainingAmount;

    @Lob // TEXT 타입 매핑
    private String description;

    private LocalDate expiration;
}