package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.General;
import com.adrian.school.management.repository.GeneralRepository;
import com.adrian.school.management.service.GeneralService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link General}.
 */
@Service
@Transactional
public class GeneralServiceImpl implements GeneralService {

    private final Logger log = LoggerFactory.getLogger(GeneralServiceImpl.class);

    private final GeneralRepository generalRepository;

    public GeneralServiceImpl(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
    }

    @Override
    public General save(General general) {
        log.debug("Request to save General : {}", general);
        return generalRepository.save(general);
    }

    @Override
    public General update(General general) {
        log.debug("Request to update General : {}", general);
        return generalRepository.save(general);
    }

    @Override
    public Optional<General> partialUpdate(General general) {
        log.debug("Request to partially update General : {}", general);

        return generalRepository
            .findById(general.getId())
            .map(existingGeneral -> {
                if (general.getSiteName() != null) {
                    existingGeneral.setSiteName(general.getSiteName());
                }
                if (general.getHomePageName() != null) {
                    existingGeneral.setHomePageName(general.getHomePageName());
                }
                if (general.getResourcesPageName() != null) {
                    existingGeneral.setResourcesPageName(general.getResourcesPageName());
                }
                if (general.getStaffPageName() != null) {
                    existingGeneral.setStaffPageName(general.getStaffPageName());
                }
                if (general.getAboutPageName() != null) {
                    existingGeneral.setAboutPageName(general.getAboutPageName());
                }
                if (general.getFacebookAddress() != null) {
                    existingGeneral.setFacebookAddress(general.getFacebookAddress());
                }
                if (general.getAddress() != null) {
                    existingGeneral.setAddress(general.getAddress());
                }
                if (general.getPhone() != null) {
                    existingGeneral.setPhone(general.getPhone());
                }
                if (general.getEmail() != null) {
                    existingGeneral.setEmail(general.getEmail());
                }
                if (general.getMotto() != null) {
                    existingGeneral.setMotto(general.getMotto());
                }
                if (general.getStructure1() != null) {
                    existingGeneral.setStructure1(general.getStructure1());
                }
                if (general.getStructure2() != null) {
                    existingGeneral.setStructure2(general.getStructure2());
                }
                if (general.getStructure3() != null) {
                    existingGeneral.setStructure3(general.getStructure3());
                }
                if (general.getStructure4() != null) {
                    existingGeneral.setStructure4(general.getStructure4());
                }
                if (general.getContactHeader() != null) {
                    existingGeneral.setContactHeader(general.getContactHeader());
                }
                if (general.getStructuresHeader() != null) {
                    existingGeneral.setStructuresHeader(general.getStructuresHeader());
                }
                if (general.getMapUrl() != null) {
                    existingGeneral.setMapUrl(general.getMapUrl());
                }

                return existingGeneral;
            })
            .map(generalRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<General> findAll() {
        log.debug("Request to get all Generals");
        return generalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<General> findOne(Long id) {
        log.debug("Request to get General : {}", id);
        return generalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete General : {}", id);
        generalRepository.deleteById(id);
    }
}
