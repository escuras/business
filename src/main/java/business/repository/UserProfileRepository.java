package business.repository;

import business.domain.UserProfile;
import business.domain.UserProfileKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UserProfileKey> {
    Set<UserProfile> findByIdUserId(Long userId);

    Set<UserProfile> findByIdUserIdAndActive(Long userId, boolean active);
}
