package business.service;

import business.domain.Action;
import business.domain.ActionLog;
import business.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ActionLogService {

    void save(User user, Action action, String description, String reason);

    List<ActionLog> getPersonLogs(Long personId, LocalDateTime after, LocalDateTime before);

    void test(final Long userId, final String actionName, String description, String reason);

    List<ActionLog> getLogs(LocalDateTime after, LocalDateTime before);

    List<ActionLog> getLogsByAction(String actionName, LocalDateTime after, LocalDateTime before);
}
