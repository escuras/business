package business.service.impl;

import business.domain.Action;
import business.domain.ActionLog;
import business.domain.User;
import business.repository.ActionLogRepository;
import business.service.ActionLogService;
import business.service.ActionService;
import business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionLogServiceImpl implements ActionLogService {

    private final ActionLogRepository actionLogRepository;
    private final UserService userService;
    private final ActionService actionService;

    @Override
    public void save(final User user, final Action action, final String description, final String reason) {
        final ActionLog actionLog = ActionLog.builder()
                .action(action)
                .person(user.getPerson())
                .profileName(user.getActiveProfile().getName())
                .userId(user.getId())
                .description(description)
                .reason(reason)
                .build();
        this.actionLogRepository.save(actionLog);
    }

    @Override
    public void test(final Long userId, final String actionName, final String description, final String reason) {
        final Optional<User> optionalUser = this.userService.findById(userId);
        final Optional<Action> optionalAction = this.actionService.findByName(actionName);
        if (optionalAction.isPresent() && optionalUser.isPresent()) {
            this.save(optionalUser.get(), optionalAction.get(), description, reason);
        }
    }

    @Override
    public List<ActionLog> getPersonLogs(@NotNull final Long personId,
                                         @NotNull final LocalDateTime after,
                                         @NotNull final LocalDateTime before) {
        return this.actionLogRepository.findByPersonIdAndInclusionDateBetween(personId, after, before);
    }

    @Override
    public List<ActionLog> getLogs(@NotNull final LocalDateTime after, @NotNull final LocalDateTime before) {
        return this.actionLogRepository.findByInclusionDateBetween(after, before);
    }

    @Override
    public List<ActionLog> getLogsByAction(@NotNull final String actionName,
                                           @NotNull final LocalDateTime after,
                                           @NotNull final LocalDateTime before) {
        return this.actionLogRepository.findByActionNameAndInclusionDateBetween(actionName, after, before);
    }
}
