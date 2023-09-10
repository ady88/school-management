package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StaffDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffData.class);
        StaffData staffData1 = new StaffData();
        staffData1.setId(1L);
        StaffData staffData2 = new StaffData();
        staffData2.setId(staffData1.getId());
        assertThat(staffData1).isEqualTo(staffData2);
        staffData2.setId(2L);
        assertThat(staffData1).isNotEqualTo(staffData2);
        staffData1.setId(null);
        assertThat(staffData1).isNotEqualTo(staffData2);
    }
}
