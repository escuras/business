package business.service;

import business.domain.Action;

import java.util.List;
import java.util.Optional;

public interface ActionService {

    List<Action> getAll();

    Optional<Action> findById(Long id);

    Optional<Action> findByName(String name);

    Action create(Action action);

    void delete(Long id);

    void update(Long id, Action action);

}
