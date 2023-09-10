package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShortNewsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShortNewsData.class);
        ShortNewsData shortNewsData1 = new ShortNewsData();
        shortNewsData1.setId(1L);
        ShortNewsData shortNewsData2 = new ShortNewsData();
        shortNewsData2.setId(shortNewsData1.getId());
        assertThat(shortNewsData1).isEqualTo(shortNewsData2);
        shortNewsData2.setId(2L);
        assertThat(shortNewsData1).isNotEqualTo(shortNewsData2);
        shortNewsData1.setId(null);
        assertThat(shortNewsData1).isNotEqualTo(shortNewsData2);
    }
}
