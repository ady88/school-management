package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.Other;
import com.adrian.school.management.repository.OtherRepository;
import com.adrian.school.management.service.OtherService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Other}.
 */
@Service
@Transactional
public class OtherServiceImpl implements OtherService {

    private final Logger log = LoggerFactory.getLogger(OtherServiceImpl.class);

    private final OtherRepository otherRepository;

    public OtherServiceImpl(OtherRepository otherRepository) {
        this.otherRepository = otherRepository;
    }

    @Override
    public Other save(Other other) {
        log.debug("Request to save Other : {}", other);
        return otherRepository.save(other);
    }

    @Override
    public Other update(Other other) {
        log.debug("Request to update Other : {}", other);
        return otherRepository.save(other);
    }

    @Override
    public Optional<Other> partialUpdate(Other other) {
        log.debug("Request to partially update Other : {}", other);

        return otherRepository
            .findById(other.getId())
            .map(existingOther -> {
                if (other.getUseTopImage() != null) {
                    existingOther.setUseTopImage(other.getUseTopImage());
                }
                if (other.getTopImage() != null) {
                    existingOther.setTopImage(other.getTopImage());
                }
                if (other.getTopImageContentType() != null) {
                    existingOther.setTopImageContentType(other.getTopImageContentType());
                }
                if (other.getUseMainImage() != null) {
                    existingOther.setUseMainImage(other.getUseMainImage());
                }
                if (other.getMainImage() != null) {
                    existingOther.setMainImage(other.getMainImage());
                }
                if (other.getMainImageContentType() != null) {
                    existingOther.setMainImageContentType(other.getMainImageContentType());
                }
                if (other.getTheme() != null) {
                    existingOther.setTheme(other.getTheme());
                }

                return existingOther;
            })
            .map(otherRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Other> findAll() {
        log.debug("Request to get all Others");
        return otherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Other> findOne(Long id) {
        log.debug("Request to get Other : {}", id);
        return otherRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Other : {}", id);
        otherRepository.deleteById(id);
    }
}
