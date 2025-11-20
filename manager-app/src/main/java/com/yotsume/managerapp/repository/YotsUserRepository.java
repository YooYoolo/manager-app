package com.yotsume.managerapp.repository;

import com.yotsume.managerapp.entity.YotsUser;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface YotsUserRepository extends CrudRepository<YotsUser, Integer> {

    Optional<YotsUser> findByUsername(String username);
}
