package com.adrian.school.management.repository;

import com.adrian.school.management.domain.Other;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Other entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtherRepository extends JpaRepository<Other, Long> {}
