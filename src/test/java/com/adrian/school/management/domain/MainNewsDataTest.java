package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MainNewsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MainNewsData.class);
        MainNewsData mainNewsData1 = new MainNewsData();
        mainNewsData1.setId(1L);
        MainNewsData mainNewsData2 = new MainNewsData();
        mainNewsData2.setId(mainNewsData1.getId());
        assertThat(mainNewsData1).isEqualTo(mainNewsData2);
        mainNewsData2.setId(2L);
        assertThat(mainNewsData1).isNotEqualTo(mainNewsData2);
        mainNewsData1.setId(null);
        assertThat(mainNewsData1).isNotEqualTo(mainNewsData2);
    }
}
