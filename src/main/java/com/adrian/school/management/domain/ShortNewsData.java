package com.adrian.school.management.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ShortNewsData.
 */
@Entity
@Table(name = "short_news_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShortNewsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "link_url")
    private String linkUrl;

    @NotNull
    @Column(name = "order_news", nullable = false)
    private Integer orderNews;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ShortNewsData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public ShortNewsData title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public ShortNewsData linkUrl(String linkUrl) {
        this.setLinkUrl(linkUrl);
        return this;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getOrderNews() {
        return this.orderNews;
    }

    public ShortNewsData orderNews(Integer orderNews) {
        this.setOrderNews(orderNews);
        return this;
    }

    public void setOrderNews(Integer orderNews) {
        this.orderNews = orderNews;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShortNewsData)) {
            return false;
        }
        return id != null && id.equals(((ShortNewsData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShortNewsData{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", linkUrl='" + getLinkUrl() + "'" +
            ", orderNews=" + getOrderNews() +
            "}";
    }
}
