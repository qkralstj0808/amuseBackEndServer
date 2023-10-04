package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.person_enum.Gender;
import com.example.amusetravelproejct.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom {
    User findByUserId(String userId);
    boolean existsByUserId(String userId);

    boolean existsByUsernameAndBirthdayAndGender(@Size(max = 100) String username, String birthday, Gender gender);

    List<User> findByEmail(String email);

    User findByUsernameAndBirthdayAndGender(@Size(max = 100) String username, String birthday, Gender gender);

    User findByUserIdAndUsername(@NotNull @Size(max = 64) String userId, @Size(max = 100) String username);
}
