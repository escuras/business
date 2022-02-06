package business.service;

import business.domain.Profile;
import business.domain.User;
import business.dto.request.PaginationRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> findById(Long id);

    User create(User user);

    Page<User> findByPageable(PaginationRequest pagination);

    void delete(Long id);

    void update(Long id, User user);

    void profileActive(Long id, String profileName);

    void addProfile(Long id, String profileName);

    void deleteProfile(Long id, String profileName);

    Profile getUserActiveProfile(Long id);

    Set<Profile> getUSerProfiles(Long id);
}
