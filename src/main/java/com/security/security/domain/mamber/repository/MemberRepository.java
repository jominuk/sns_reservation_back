package com.security.security.domain.mamber.repository;

import com.security.security.domain.mamber.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserId(String userId);

    MemberEntity findByUserIdAndPassword(String userId, String password);
}
