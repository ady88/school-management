package com.adrian.school.management.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A General.
 */
@Entity
@Table(name = "general")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class General implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "site_name", nullable = false)
    private String siteName;

    @Column(name = "home_page_name")
    private String homePageName;

    @Column(name = "resources_page_name")
    private String resourcesPageName;

    @Column(name = "staff_page_name")
    private String staffPageName;

    @Column(name = "about_page_name")
    private String aboutPageName;

    @Column(name = "facebook_address")
    private String facebookAddress;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "motto")
    private String motto;

    @Column(name = "structure_1")
    private String structure1;

    @Column(name = "structure_2")
    private String structure2;

    @Column(name = "structure_3")
    private String structure3;

    @Column(name = "structure_4")
    private String structure4;

    @Column(name = "contact_header")
    private String contactHeader;

    @Column(name = "structures_header")
    private String structuresHeader;

    @Column(name = "map_url")
    private String mapUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public General id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public General siteName(String siteName) {
        this.setSiteName(siteName);
        return this;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getHomePageName() {
        return this.homePageName;
    }

    public General homePageName(String homePageName) {
        this.setHomePageName(homePageName);
        return this;
    }

    public void setHomePageName(String homePageName) {
        this.homePageName = homePageName;
    }

    public String getResourcesPageName() {
        return this.resourcesPageName;
    }

    public General resourcesPageName(String resourcesPageName) {
        this.setResourcesPageName(resourcesPageName);
        return this;
    }

    public void setResourcesPageName(String resourcesPageName) {
        this.resourcesPageName = resourcesPageName;
    }

    public String getStaffPageName() {
        return this.staffPageName;
    }

    public General staffPageName(String staffPageName) {
        this.setStaffPageName(staffPageName);
        return this;
    }

    public void setStaffPageName(String staffPageName) {
        this.staffPageName = staffPageName;
    }

    public String getAboutPageName() {
        return this.aboutPageName;
    }

    public General aboutPageName(String aboutPageName) {
        this.setAboutPageName(aboutPageName);
        return this;
    }

    public void setAboutPageName(String aboutPageName) {
        this.aboutPageName = aboutPageName;
    }

    public String getFacebookAddress() {
        return this.facebookAddress;
    }

    public General facebookAddress(String facebookAddress) {
        this.setFacebookAddress(facebookAddress);
        return this;
    }

    public void setFacebookAddress(String facebookAddress) {
        this.facebookAddress = facebookAddress;
    }

    public String getAddress() {
        return this.address;
    }

    public General address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public General phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public General email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotto() {
        return this.motto;
    }

    public General motto(String motto) {
        this.setMotto(motto);
        return this;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getStructure1() {
        return this.structure1;
    }

    public General structure1(String structure1) {
        this.setStructure1(structure1);
        return this;
    }

    public void setStructure1(String structure1) {
        this.structure1 = structure1;
    }

    public String getStructure2() {
        return this.structure2;
    }

    public General structure2(String structure2) {
        this.setStructure2(structure2);
        return this;
    }

    public void setStructure2(String structure2) {
        this.structure2 = structure2;
    }

    public String getStructure3() {
        return this.structure3;
    }

    public General structure3(String structure3) {
        this.setStructure3(structure3);
        return this;
    }

    public void setStructure3(String structure3) {
        this.structure3 = structure3;
    }

    public String getStructure4() {
        return this.structure4;
    }

    public General structure4(String structure4) {
        this.setStructure4(structure4);
        return this;
    }

    public void setStructure4(String structure4) {
        this.structure4 = structure4;
    }

    public String getContactHeader() {
        return this.contactHeader;
    }

    public General contactHeader(String contactHeader) {
        this.setContactHeader(contactHeader);
        return this;
    }

    public void setContactHeader(String contactHeader) {
        this.contactHeader = contactHeader;
    }

    public String getStructuresHeader() {
        return this.structuresHeader;
    }

    public General structuresHeader(String structuresHeader) {
        this.setStructuresHeader(structuresHeader);
        return this;
    }

    public void setStructuresHeader(String structuresHeader) {
        this.structuresHeader = structuresHeader;
    }

    public String getMapUrl() {
        return this.mapUrl;
    }

    public General mapUrl(String mapUrl) {
        this.setMapUrl(mapUrl);
        return this;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof General)) {
            return false;
        }
        return id != null && id.equals(((General) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "General{" +
            "id=" + getId() +
            ", siteName='" + getSiteName() + "'" +
            ", homePageName='" + getHomePageName() + "'" +
            ", resourcesPageName='" + getResourcesPageName() + "'" +
            ", staffPageName='" + getStaffPageName() + "'" +
            ", aboutPageName='" + getAboutPageName() + "'" +
            ", facebookAddress='" + getFacebookAddress() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", motto='" + getMotto() + "'" +
            ", structure1='" + getStructure1() + "'" +
            ", structure2='" + getStructure2() + "'" +
            ", structure3='" + getStructure3() + "'" +
            ", structure4='" + getStructure4() + "'" +
            ", contactHeader='" + getContactHeader() + "'" +
            ", structuresHeader='" + getStructuresHeader() + "'" +
            ", mapUrl='" + getMapUrl() + "'" +
            "}";
    }
}
