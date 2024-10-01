package com.security.security.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    // 필요한 메소드 정의
    //boolean existsByUserIdAndRefreshToken(String userId, String refreshUserId);

    Optional<TokenEntity> findByUserId(String userId);
}

