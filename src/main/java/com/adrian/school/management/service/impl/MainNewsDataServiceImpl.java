package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.MainNewsData;
import com.adrian.school.management.repository.MainNewsDataRepository;
import com.adrian.school.management.service.MainNewsDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MainNewsData}.
 */
@Service
@Transactional
public class MainNewsDataServiceImpl implements MainNewsDataService {

    private final Logger log = LoggerFactory.getLogger(MainNewsDataServiceImpl.class);

    private final MainNewsDataRepository mainNewsDataRepository;

    public MainNewsDataServiceImpl(MainNewsDataRepository mainNewsDataRepository) {
        this.mainNewsDataRepository = mainNewsDataRepository;
    }

    @Override
    public MainNewsData save(MainNewsData mainNewsData) {
        log.debug("Request to save MainNewsData : {}", mainNewsData);
        return mainNewsDataRepository.save(mainNewsData);
    }

    @Override
    public MainNewsData update(MainNewsData mainNewsData) {
        log.debug("Request to update MainNewsData : {}", mainNewsData);
        return mainNewsDataRepository.save(mainNewsData);
    }

    @Override
    public Optional<MainNewsData> partialUpdate(MainNewsData mainNewsData) {
        log.debug("Request to partially update MainNewsData : {}", mainNewsData);

        return mainNewsDataRepository
            .findById(mainNewsData.getId())
            .map(existingMainNewsData -> {
                if (mainNewsData.getTitle() != null) {
                    existingMainNewsData.setTitle(mainNewsData.getTitle());
                }
                if (mainNewsData.getDescription() != null) {
                    existingMainNewsData.setDescription(mainNewsData.getDescription());
                }
                if (mainNewsData.getImage() != null) {
                    existingMainNewsData.setImage(mainNewsData.getImage());
                }
                if (mainNewsData.getImageContentType() != null) {
                    existingMainNewsData.setImageContentType(mainNewsData.getImageContentType());
                }
                if (mainNewsData.getOrderNews() != null) {
                    existingMainNewsData.setOrderNews(mainNewsData.getOrderNews());
                }

                return existingMainNewsData;
            })
            .map(mainNewsDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MainNewsData> findAll(Pageable pageable) {
        log.debug("Request to get all MainNewsData");
        return mainNewsDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MainNewsData> findOne(Long id) {
        log.debug("Request to get MainNewsData : {}", id);
        return mainNewsDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MainNewsData : {}", id);
        mainNewsDataRepository.deleteById(id);
    }
}
