package com.adrian.school.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adrian.school.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocsData.class);
        DocsData docsData1 = new DocsData();
        docsData1.setId(1L);
        DocsData docsData2 = new DocsData();
        docsData2.setId(docsData1.getId());
        assertThat(docsData1).isEqualTo(docsData2);
        docsData2.setId(2L);
        assertThat(docsData1).isNotEqualTo(docsData2);
        docsData1.setId(null);
        assertThat(docsData1).isNotEqualTo(docsData2);
    }
}
