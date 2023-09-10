package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.ShortNewsData;
import com.adrian.school.management.repository.ShortNewsDataRepository;
import com.adrian.school.management.service.ShortNewsDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShortNewsData}.
 */
@Service
@Transactional
public class ShortNewsDataServiceImpl implements ShortNewsDataService {

    private final Logger log = LoggerFactory.getLogger(ShortNewsDataServiceImpl.class);

    private final ShortNewsDataRepository shortNewsDataRepository;

    public ShortNewsDataServiceImpl(ShortNewsDataRepository shortNewsDataRepository) {
        this.shortNewsDataRepository = shortNewsDataRepository;
    }

    @Override
    public ShortNewsData save(ShortNewsData shortNewsData) {
        log.debug("Request to save ShortNewsData : {}", shortNewsData);
        return shortNewsDataRepository.save(shortNewsData);
    }

    @Override
    public ShortNewsData update(ShortNewsData shortNewsData) {
        log.debug("Request to update ShortNewsData : {}", shortNewsData);
        return shortNewsDataRepository.save(shortNewsData);
    }

    @Override
    public Optional<ShortNewsData> partialUpdate(ShortNewsData shortNewsData) {
        log.debug("Request to partially update ShortNewsData : {}", shortNewsData);

        return shortNewsDataRepository
            .findById(shortNewsData.getId())
            .map(existingShortNewsData -> {
                if (shortNewsData.getTitle() != null) {
                    existingShortNewsData.setTitle(shortNewsData.getTitle());
                }
                if (shortNewsData.getLinkUrl() != null) {
                    existingShortNewsData.setLinkUrl(shortNewsData.getLinkUrl());
                }
                if (shortNewsData.getOrderNews() != null) {
                    existingShortNewsData.setOrderNews(shortNewsData.getOrderNews());
                }

                return existingShortNewsData;
            })
            .map(shortNewsDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShortNewsData> findAll(Pageable pageable) {
        log.debug("Request to get all ShortNewsData");
        return shortNewsDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShortNewsData> findOne(Long id) {
        log.debug("Request to get ShortNewsData : {}", id);
        return shortNewsDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShortNewsData : {}", id);
        shortNewsDataRepository.deleteById(id);
    }
}
