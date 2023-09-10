package com.adrian.school.management.repository;

import com.adrian.school.management.domain.StaffData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StaffData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffDataRepository extends JpaRepository<StaffData, Long> {}
