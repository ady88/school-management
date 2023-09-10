package com.adrian.school.management.repository;

import com.adrian.school.management.domain.ShortNewsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShortNewsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShortNewsDataRepository extends JpaRepository<ShortNewsData, Long> {}
