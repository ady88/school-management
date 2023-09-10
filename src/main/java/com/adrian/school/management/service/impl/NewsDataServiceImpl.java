package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.NewsData;
import com.adrian.school.management.repository.NewsDataRepository;
import com.adrian.school.management.service.NewsDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NewsData}.
 */
@Service
@Transactional
public class NewsDataServiceImpl implements NewsDataService {

    private final Logger log = LoggerFactory.getLogger(NewsDataServiceImpl.class);

    private final NewsDataRepository newsDataRepository;

    public NewsDataServiceImpl(NewsDataRepository newsDataRepository) {
        this.newsDataRepository = newsDataRepository;
    }

    @Override
    public NewsData save(NewsData newsData) {
        log.debug("Request to save NewsData : {}", newsData);
        return newsDataRepository.save(newsData);
    }

    @Override
    public NewsData update(NewsData newsData) {
        log.debug("Request to update NewsData : {}", newsData);
        return newsDataRepository.save(newsData);
    }

    @Override
    public Optional<NewsData> partialUpdate(NewsData newsData) {
        log.debug("Request to partially update NewsData : {}", newsData);

        return newsDataRepository
            .findById(newsData.getId())
            .map(existingNewsData -> {
                if (newsData.getTitle() != null) {
                    existingNewsData.setTitle(newsData.getTitle());
                }
                if (newsData.getDescription() != null) {
                    existingNewsData.setDescription(newsData.getDescription());
                }
                if (newsData.getImage() != null) {
                    existingNewsData.setImage(newsData.getImage());
                }
                if (newsData.getImageContentType() != null) {
                    existingNewsData.setImageContentType(newsData.getImageContentType());
                }
                if (newsData.getLinkLabel() != null) {
                    existingNewsData.setLinkLabel(newsData.getLinkLabel());
                }
                if (newsData.getLinkUrl() != null) {
                    existingNewsData.setLinkUrl(newsData.getLinkUrl());
                }
                if (newsData.getOrderNews() != null) {
                    existingNewsData.setOrderNews(newsData.getOrderNews());
                }

                return existingNewsData;
            })
            .map(newsDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsData> findAll(Pageable pageable) {
        log.debug("Request to get all NewsData");
        return newsDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NewsData> findOne(Long id) {
        log.debug("Request to get NewsData : {}", id);
        return newsDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsData : {}", id);
        newsDataRepository.deleteById(id);
    }
}
