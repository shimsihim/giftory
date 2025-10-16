package com.side.giftory.gifticon.repository;

import com.side.giftory.gifticon.domain.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
    @Query("select g from Gifticon g where g.id in (:ids)")
    List<Gifticon> findAllByIds(Collection<Long> ids);
}