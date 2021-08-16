package com.jmr.stream.demostream.model.repository;

import com.jmr.stream.demostream.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventEntityRepository extends JpaRepository<EventEntity, Long> {

}
