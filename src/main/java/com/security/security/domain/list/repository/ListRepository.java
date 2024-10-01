package com.security.security.domain.list.repository;

import com.security.security.domain.list.entity.ListEntity;
import com.security.security.domain.mamber.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> {
    List<ListEntity> findByMember_UserId(String userId);

    List<ListEntity> findByMember(MemberEntity member);
}
