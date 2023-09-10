package com.adrian.school.management.repository;

import com.adrian.school.management.domain.General;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the General entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralRepository extends JpaRepository<General, Long> {}
