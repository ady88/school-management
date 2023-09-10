package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.DocsData;
import com.adrian.school.management.repository.DocsDataRepository;
import com.adrian.school.management.service.DocsDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocsData}.
 */
@Service
@Transactional
public class DocsDataServiceImpl implements DocsDataService {

    private final Logger log = LoggerFactory.getLogger(DocsDataServiceImpl.class);

    private final DocsDataRepository docsDataRepository;

    public DocsDataServiceImpl(DocsDataRepository docsDataRepository) {
        this.docsDataRepository = docsDataRepository;
    }

    @Override
    public DocsData save(DocsData docsData) {
        log.debug("Request to save DocsData : {}", docsData);
        return docsDataRepository.save(docsData);
    }

    @Override
    public DocsData update(DocsData docsData) {
        log.debug("Request to update DocsData : {}", docsData);
        return docsDataRepository.save(docsData);
    }

    @Override
    public Optional<DocsData> partialUpdate(DocsData docsData) {
        log.debug("Request to partially update DocsData : {}", docsData);

        return docsDataRepository
            .findById(docsData.getId())
            .map(existingDocsData -> {
                if (docsData.getName() != null) {
                    existingDocsData.setName(docsData.getName());
                }
                if (docsData.getDescription() != null) {
                    existingDocsData.setDescription(docsData.getDescription());
                }
                if (docsData.getDoc() != null) {
                    existingDocsData.setDoc(docsData.getDoc());
                }
                if (docsData.getDocContentType() != null) {
                    existingDocsData.setDocContentType(docsData.getDocContentType());
                }
                if (docsData.getOrderdoc() != null) {
                    existingDocsData.setOrderdoc(docsData.getOrderdoc());
                }
                if (docsData.getResourcedate() != null) {
                    existingDocsData.setResourcedate(docsData.getResourcedate());
                }

                return existingDocsData;
            })
            .map(docsDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocsData> findAll(Pageable pageable) {
        log.debug("Request to get all DocsData");
        return docsDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocsData> findOne(Long id) {
        log.debug("Request to get DocsData : {}", id);
        return docsDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocsData : {}", id);
        docsDataRepository.deleteById(id);
    }
}
