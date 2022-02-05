package business.service;

import business.domain.Profile;
import business.domain.User;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Optional<Profile> findById(Long id);

    Profile create(Profile profile);

    List<Profile> findAll();

    void delete(Long id);

    void update(Long id, Profile profile);

    Profile getDefault();

    void addProfile(User user, Profile profile);

    void setProfileActive(final long userId, final String profileName);
}
