package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NewsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsData.class);
        NewsData newsData1 = new NewsData();
        newsData1.setId(1L);
        NewsData newsData2 = new NewsData();
        newsData2.setId(newsData1.getId());
        assertThat(newsData1).isEqualTo(newsData2);
        newsData2.setId(2L);
        assertThat(newsData1).isNotEqualTo(newsData2);
        newsData1.setId(null);
        assertThat(newsData1).isNotEqualTo(newsData2);
    }
}
