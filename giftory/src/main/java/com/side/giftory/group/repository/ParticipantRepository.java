package com.side.giftory.group.repository;

import com.side.giftory.group.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByUserId(Long userId);
    List<Participant> findByGroupId(Long groupId);
    Optional<Participant> findByUserIdAndGroupId(Long userId, Long groupId);
}