package com.adrian.school.management.repository;

import com.adrian.school.management.domain.DocsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocsDataRepository extends JpaRepository<DocsData, Long> {}
