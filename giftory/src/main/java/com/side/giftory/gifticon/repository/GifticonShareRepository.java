package com.side.giftory.gifticon.repository;

import com.side.giftory.gifticon.domain.GifticonShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifticonShareRepository extends JpaRepository<GifticonShare, Long> {
    List<GifticonShare> findByGroupId(Long groupId);
    List<GifticonShare> findByGroupIdAndShareUserId(Long groupId, Long shareUserId);
    List<GifticonShare> findByGifticonId(Long gifticonId);
}