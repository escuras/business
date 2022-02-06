package business.service.impl;

import business.domain.Action;
import business.exception.BusinessException;
import business.repository.ActionRepository;
import business.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    public List<Action> getAll() {
        return this.actionRepository.findAll();
    }

    @Override
    public Optional<Action> findById(@NotNull(message = "Id can't be null.") final Long id) {
        return this.actionRepository.findById(id);
    }

    @Override
    public Optional<Action> findByName(@NotNull(message = "Name can't be null.") final String name) {
        return this.actionRepository.findByName(name);
    }

    @Override
    public Action create(@NotNull(message = "Action can't be null.") final Action action) {
        if (!StringUtils.isEmpty(action.getName())) {
            return this.actionRepository.findByName(action.getName()).orElseGet(() -> this.actionRepository.save(action));
        }
        throw new BusinessException("Name of action should not be empty.");
    }

    @Override
    public void delete(@NotNull(message = "Id can't be null.") final Long id) {
        this.actionRepository.deleteById(id);
    }

    @Override
    public void update(@NotNull(message = "Id can't be null.") final Long id,
                       @NotNull(message = "Action can't be null.") final Action action) {
        final Action dbAction = this.actionRepository.findById(id).orElseGet(() -> {
            throw new BusinessException("Action not found with id: " + id + ".");
        });
        dbAction.setDescription(action.getDescription());
        dbAction.setName(action.getName());
        this.actionRepository.save(dbAction);
    }
}
