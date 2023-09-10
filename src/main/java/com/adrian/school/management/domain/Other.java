package com.adrian.school.management.domain;

import com.adrian.school.management.domain.enumeration.Theme;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Other.
 */
@Entity
@Table(name = "other")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Other implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "use_top_image")
    private Boolean useTopImage;

    @Lob
    @Column(name = "top_image")
    private byte[] topImage;

    @Column(name = "top_image_content_type")
    private String topImageContentType;

    @Column(name = "use_main_image")
    private Boolean useMainImage;

    @Lob
    @Column(name = "main_image")
    private byte[] mainImage;

    @Column(name = "main_image_content_type")
    private String mainImageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private Theme theme;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Other id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUseTopImage() {
        return this.useTopImage;
    }

    public Other useTopImage(Boolean useTopImage) {
        this.setUseTopImage(useTopImage);
        return this;
    }

    public void setUseTopImage(Boolean useTopImage) {
        this.useTopImage = useTopImage;
    }

    public byte[] getTopImage() {
        return this.topImage;
    }

    public Other topImage(byte[] topImage) {
        this.setTopImage(topImage);
        return this;
    }

    public void setTopImage(byte[] topImage) {
        this.topImage = topImage;
    }

    public String getTopImageContentType() {
        return this.topImageContentType;
    }

    public Other topImageContentType(String topImageContentType) {
        this.topImageContentType = topImageContentType;
        return this;
    }

    public void setTopImageContentType(String topImageContentType) {
        this.topImageContentType = topImageContentType;
    }

    public Boolean getUseMainImage() {
        return this.useMainImage;
    }

    public Other useMainImage(Boolean useMainImage) {
        this.setUseMainImage(useMainImage);
        return this;
    }

    public void setUseMainImage(Boolean useMainImage) {
        this.useMainImage = useMainImage;
    }

    public byte[] getMainImage() {
        return this.mainImage;
    }

    public Other mainImage(byte[] mainImage) {
        this.setMainImage(mainImage);
        return this;
    }

    public void setMainImage(byte[] mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImageContentType() {
        return this.mainImageContentType;
    }

    public Other mainImageContentType(String mainImageContentType) {
        this.mainImageContentType = mainImageContentType;
        return this;
    }

    public void setMainImageContentType(String mainImageContentType) {
        this.mainImageContentType = mainImageContentType;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public Other theme(Theme theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Other)) {
            return false;
        }
        return id != null && id.equals(((Other) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Other{" +
            "id=" + getId() +
            ", useTopImage='" + getUseTopImage() + "'" +
            ", topImage='" + getTopImage() + "'" +
            ", topImageContentType='" + getTopImageContentType() + "'" +
            ", useMainImage='" + getUseMainImage() + "'" +
            ", mainImage='" + getMainImage() + "'" +
            ", mainImageContentType='" + getMainImageContentType() + "'" +
            ", theme='" + getTheme() + "'" +
            "}";
    }
}
