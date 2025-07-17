package com.ch.carinspection.imgupl.service.repository;

import com.ch.carinspection.imgupl.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link Image} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard database operations
 * such as save, find, delete, and pagination for Image objects.
 * </p>
 *
 * This repository is automatically implemented by Spring Data JPA.
 *
 * @author YourName
 * @version 1.0
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
