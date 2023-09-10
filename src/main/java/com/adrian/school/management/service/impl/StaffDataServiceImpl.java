package com.adrian.school.management.service.impl;

import com.adrian.school.management.domain.StaffData;
import com.adrian.school.management.repository.StaffDataRepository;
import com.adrian.school.management.service.StaffDataService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StaffData}.
 */
@Service
@Transactional
public class StaffDataServiceImpl implements StaffDataService {

    private final Logger log = LoggerFactory.getLogger(StaffDataServiceImpl.class);

    private final StaffDataRepository staffDataRepository;

    public StaffDataServiceImpl(StaffDataRepository staffDataRepository) {
        this.staffDataRepository = staffDataRepository;
    }

    @Override
    public StaffData save(StaffData staffData) {
        log.debug("Request to save StaffData : {}", staffData);
        return staffDataRepository.save(staffData);
    }

    @Override
    public StaffData update(StaffData staffData) {
        log.debug("Request to update StaffData : {}", staffData);
        return staffDataRepository.save(staffData);
    }

    @Override
    public Optional<StaffData> partialUpdate(StaffData staffData) {
        log.debug("Request to partially update StaffData : {}", staffData);

        return staffDataRepository
            .findById(staffData.getId())
            .map(existingStaffData -> {
                if (staffData.getOrderStaff() != null) {
                    existingStaffData.setOrderStaff(staffData.getOrderStaff());
                }
                if (staffData.getLastName() != null) {
                    existingStaffData.setLastName(staffData.getLastName());
                }
                if (staffData.getFirstName() != null) {
                    existingStaffData.setFirstName(staffData.getFirstName());
                }
                if (staffData.getJobType() != null) {
                    existingStaffData.setJobType(staffData.getJobType());
                }
                if (staffData.getUnitName() != null) {
                    existingStaffData.setUnitName(staffData.getUnitName());
                }

                return existingStaffData;
            })
            .map(staffDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffData> findAll(Pageable pageable) {
        log.debug("Request to get all StaffData");
        return staffDataRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StaffData> findOne(Long id) {
        log.debug("Request to get StaffData : {}", id);
        return staffDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StaffData : {}", id);
        staffDataRepository.deleteById(id);
    }
}
