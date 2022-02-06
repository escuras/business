package business.service.impl;

import business.domain.Profile;
import business.domain.User;
import business.domain.UserProfile;
import business.domain.UserProfileKey;
import business.exception.BusinessException;
import business.repository.ProfileRepository;
import business.repository.UserProfileRepository;
import business.service.ProfileService;
import business.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Optional<Profile> findById(@NotNull(message = "Id can't be null") final Long id) {
        return this.profileRepository.findById(id);
    }

    @Override
    public Profile create(@NotNull(message = "Profile can't be null") final Profile profile) {
        profile.setId(null);
        return this.profileRepository.save(profile);
    }

    @Override
    public List<Profile> findAll() {
        return this.profileRepository.findAll();
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null") final Long id) {
        this.profileRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(@NotNull(message = "Id can't be null") final Long id,
                       @NotNull(message = "Profile can't be null") final Profile profile) {
        final Profile dbProfile = this.profileRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("Profile doesn't exist to update.");
        });
        if (dbProfile.getName().equals(Constants.DEFAULT_NAME_PROFILE)) {
            return;
        }
        dbProfile.setName(profile.getName());
        dbProfile.setDescription(profile.getDescription());
    }

    @Override
    public Profile getDefault() {
        return this.profileRepository.findByName(Constants.DEFAULT_NAME_PROFILE)
                .orElseGet(() -> {
                    final Profile profile = Profile.builder()
                            .name(Constants.DEFAULT_NAME_PROFILE)
                            .description(Constants.DEFAULT_DESCRIPTION_PROFILE)
                            .build();
                    return this.profileRepository.save(profile);
                });
    }

    @Override
    public void addProfile(final User user, final Profile profile) {
        final UserProfile userProfile = UserProfile.builder().build();
        final UserProfileKey profileKey = new UserProfileKey();
        profileKey.setProfileId(profile.getId());
        profileKey.setUserId(user.getId());
        userProfile.setId(profileKey);
        userProfile.setProfile(profile);
        userProfile.setUser(user);
        if (CollectionUtils.isEmpty(user.getUserProfiles())) {
            userProfile.setActive(true);
        }
        this.userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public void setProfileActive(final long userId, final String profileName) {
        Set<UserProfile> userProfiles = this.userProfileRepository.findByIdUserId(userId);
        if (!CollectionUtils.isEmpty(userProfiles)) {
            final Optional<UserProfile> optionalUserProfileToActivate = userProfiles.stream()
                    .filter(e -> e.getProfile().getName().equals(profileName))
                    .findFirst();
            if (optionalUserProfileToActivate.isPresent()) {
                userProfiles = this.disableAll(userProfiles);
                final UserProfile userProfile = optionalUserProfileToActivate.get();
                userProfile.setActive(true);
                this.userProfileRepository.saveAll(userProfiles);

            }
        }
    }

    private Set<UserProfile> disableAll(final Set<UserProfile> userProfiles) {
        userProfiles.stream().filter(e -> e.isActive()).forEach(e -> {
            e.setActive(false);
        });
        return userProfiles;
    }
}
