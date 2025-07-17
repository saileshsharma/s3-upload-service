package com.ch.carinspection.imgupl.service.repository;

import com.ch.carinspection.imgupl.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageGraphQLRepository extends JpaRepository<Image, Long> {}
