package ec.tcs.banktransactions.application.service;

import ec.tcs.banktransactions.application.exception.DataNotFound;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class that provides basic CRUD operations for a given entity
 *
 * @param <T> Entity Type
 * @param <ID> Primary Key Type
 * @param <DTO> Data Transfer Object
 * @param <R> Repository Type
 */
@RequiredArgsConstructor
public abstract class AbstractService<T, ID, DTO, R extends JpaRepository<T, ID>> {

    protected final R repository;
    protected final Logger log;
    private final String className;

    /**
     * Saves a new entity
     */
    @Transactional
    public DTO save(DTO dto) {
        T entity = preSave(mapToEntity(dto));
        T savedEntity = repository.save(entity);
        return postSave(mapToDTO(savedEntity));
    }

    /**
     * Updates an existing entity, if it exists
     */
    @Transactional
    public DTO update(ID id, DTO dto) {
        if (!repository.existsById(id)) {
            throw new DataNotFound(className + " with ID " + id + " not found");
        }
        T entity = preUpdate(mapToEntity(dto));
        T updatedEntity = repository.save(entity);
        return postUpdate(mapToDTO(updatedEntity));
    }

    /**
     * Reads an entity by ID
     */
    public DTO read(ID id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() ->
                        new DataNotFound(className + " with ID " + id + " not found"));
    }

    /**
     * Reads all entities
     */
    public List<DTO> readAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an entity by ID
     */
    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new DataNotFound(className + " with ID " + id + " not found");
        }
        repository.deleteById(preDelete(id));
        postDelete(id);
    }

    // Abstract methods to be implemented in subclasses
    public abstract DTO mapToDTO(T entity);
    public abstract T mapToEntity(DTO dto);

    // Hooks for customization
    public T preSave(T entity) { return entity; }
    public DTO postSave(DTO dto) { return dto; }
    public T preUpdate(T entity) { return entity; }
    public DTO postUpdate(DTO dto) { return dto; }
    public ID preDelete(ID id) { return id; }
    public void postDelete(ID id) {}
}
