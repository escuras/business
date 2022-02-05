package business.service.impl;

import business.domain.Profile;
import business.domain.User;
import business.domain.UserProfile;
import business.dto.request.PaginationRequest;
import business.exception.BusinessException;
import business.repository.UserProfileRepository;
import business.repository.UserRepository;
import business.service.ProfileService;
import business.service.UserService;
import business.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Optional<User> findById(@NotNull(message = "Id can't be null.") final Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public User create(@NotNull(message = "User can't be null.") User user) {
        user.setId(null);
        user = this.userRepository.save(user);
        this.profileService.addProfile(user, this.profileService.getDefault());
        this.profileService.setProfileActive(user.getId(), Constants.DEFAULT_NAME_PROFILE);
        return user;
    }

    @Override
    public Page<User> findByPageable(@NotNull(message = "Pagination can't be null.") final PaginationRequest pagination) {
        if (pagination.getSize() < 1) {
            pagination.setSize(1);
        }
        final Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        return this.userRepository.findAll(pageable);
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null.") final Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(@NotNull(message = "Id can't be null.") final Long id,
                       @NotNull(message = "User can't be null.") final User user) {
        final User dbUser = this.userRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("User does not exist.");
        });
        dbUser.setActive(user.isActive());
        dbUser.setDocument(user.getDocument());
        dbUser.setEmail(user.getEmail());
        dbUser.setPerson(user.getPerson());
    }

    @Override
    public void profileActive(final Long id, final String profileName) {
        this.profileService.setProfileActive(id, profileName);
    }

    @Override
    public void addProfile(final Long id, final String profileName) {
        final Optional<Profile> optionalProfile = this.profileService.findAll().stream()
                .filter(e -> e.getName().equals(profileName))
                .findFirst();
        final Optional<User> optionalUser = this.findById(id);
        if (optionalUser.isPresent() && optionalProfile.isPresent()) {
            this.profileService.addProfile(optionalUser.get(), optionalProfile.get());
        }
    }

    @Override
    public Profile getUserActiveProfile(@NotNull final Long id) {
        final Set<UserProfile> userProfiles = this.userProfileRepository.findByIdUserIdAndActive(id, true);
        final Optional<UserProfile> optionalUserProfile = userProfiles.stream().filter(e -> e.isActive()).findFirst();
        if (optionalUserProfile.isPresent()) {
            return optionalUserProfile.get().getProfile();
        }
        return null;
    }

    @Override
    public Set<Profile> getUSerProfiles(@NotNull final Long id) {
        return this.userProfileRepository.findByIdUserId(id).stream()
                .map(e -> e.getProfile())
                .collect(Collectors.toSet());
    }
}
