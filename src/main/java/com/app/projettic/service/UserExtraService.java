package com.app.projettic.service;

import com.app.projettic.service.dto.UserExtraDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.projettic.domain.UserExtra}.
 */
public interface UserExtraService {

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save.
     * @return the persisted entity.
     */
    UserExtraDTO save(UserExtraDTO userExtraDTO);

    /**
     * Get all the userExtras.
     *
     * @return the list of entities.
     */
    List<UserExtraDTO> findAll();

    /**
     * Get the "id" userExtra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExtraDTO> findOne(Long id);

    /**
     * Delete the "id" userExtra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the userExtra corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<UserExtraDTO> search(String query);
}
