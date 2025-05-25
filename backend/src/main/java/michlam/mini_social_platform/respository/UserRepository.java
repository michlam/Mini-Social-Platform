package michlam.mini_social_platform.respository;

import jakarta.transaction.Transactional;
import michlam.mini_social_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @NativeQuery("DELETE FROM mini_social_platform.users WHERE username LIKE 'test%';")
    void testDeleteUsers();
}
