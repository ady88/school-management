package com.adrian.school.management.repository;

import com.adrian.school.management.domain.NewsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NewsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsDataRepository extends JpaRepository<NewsData, Long> {}
