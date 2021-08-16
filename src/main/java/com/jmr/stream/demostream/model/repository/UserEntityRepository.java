package com.jmr.stream.demostream.model.repository;

import com.jmr.stream.demostream.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {


    /**
     * @param dni dni
     * @return UserEntity
     */
    UserEntity findByDni
    (
            String dni
    );


    /**
     * @param dni dni
     * @return UserEntity
     */
    UserEntity findFirstByDni
    (
            String dni
    );
}


