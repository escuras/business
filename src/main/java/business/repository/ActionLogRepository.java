package business.repository;

import business.domain.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    List<ActionLog> findByPersonIdAndInclusionDateBetween(Long personId, LocalDateTime after, LocalDateTime before);

    List<ActionLog> findByInclusionDateBetween(LocalDateTime after, LocalDateTime before);

    List<ActionLog> findByActionNameAndInclusionDateBetween(String actionName, LocalDateTime after, LocalDateTime before);

}
