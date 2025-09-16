package com.side.giftory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 엔티티 라이프사이클 이벤트 감지 → @CreatedDate/@LastModifiedDate 자동 적용
public class GiftoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiftoryApplication.class, args);
    }

}
