package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtherTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Other.class);
        Other other1 = new Other();
        other1.setId(1L);
        Other other2 = new Other();
        other2.setId(other1.getId());
        assertThat(other1).isEqualTo(other2);
        other2.setId(2L);
        assertThat(other1).isNotEqualTo(other2);
        other1.setId(null);
        assertThat(other1).isNotEqualTo(other2);
    }
}
