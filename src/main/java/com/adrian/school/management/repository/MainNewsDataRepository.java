package com.adrian.school.management.repository;

import com.adrian.school.management.domain.MainNewsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MainNewsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MainNewsDataRepository extends JpaRepository<MainNewsData, Long> {}
