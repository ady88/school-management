package com.adrian.school.management.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocsData.
 */
@Entity
@Table(name = "docs_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "doc", nullable = false)
    private byte[] doc;

    @NotNull
    @Column(name = "doc_content_type", nullable = false)
    private String docContentType;

    @NotNull
    @Column(name = "orderdoc", nullable = false)
    private Integer orderdoc;

    @NotNull
    @Column(name = "resourcedate", nullable = false)
    private Instant resourcedate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocsData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DocsData name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public DocsData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getDoc() {
        return this.doc;
    }

    public DocsData doc(byte[] doc) {
        this.setDoc(doc);
        return this;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return this.docContentType;
    }

    public DocsData docContentType(String docContentType) {
        this.docContentType = docContentType;
        return this;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public Integer getOrderdoc() {
        return this.orderdoc;
    }

    public DocsData orderdoc(Integer orderdoc) {
        this.setOrderdoc(orderdoc);
        return this;
    }

    public void setOrderdoc(Integer orderdoc) {
        this.orderdoc = orderdoc;
    }

    public Instant getResourcedate() {
        return this.resourcedate;
    }

    public DocsData resourcedate(Instant resourcedate) {
        this.setResourcedate(resourcedate);
        return this;
    }

    public void setResourcedate(Instant resourcedate) {
        this.resourcedate = resourcedate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocsData)) {
            return false;
        }
        return id != null && id.equals(((DocsData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocsData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", doc='" + getDoc() + "'" +
            ", docContentType='" + getDocContentType() + "'" +
            ", orderdoc=" + getOrderdoc() +
            ", resourcedate='" + getResourcedate() + "'" +
            "}";
    }
}
