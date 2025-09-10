package com.deng.book_review_publishing.entity;

import java.util.Date;

import com.deng.book_review_publishing.entity.enums.Country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;
    
    @Column(name = "country_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Country countryName;
    @Column(name = "country_description")
    private String countryDescription;
    @Column(name = "is_deleted", nullable = false)
    private byte isDeleted;
    @Column(name = "created_by_admin_id")
    private Long createdByAdminId;
    @Column(name = "updated_by_admin_id")
    private Long updatedByAdminId;
    @Column(name = "country_code", nullable = false, unique = true)
    private String countryCode;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "updated_time")
    private Date updatedTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Country getCountryName() {
        return countryName;
    }
    public void setCountryName(Country countryName) {
        this.countryName = countryName;
    }
    public String getCountryDescription() {
        return countryDescription;
    }
    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }
    public byte getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Long getCreatedByAdminId() {
        return createdByAdminId;
    }
    public void setCreatedByAdminId(Long createdByAdminId) {
        this.createdByAdminId = createdByAdminId;
    }
    public Long getUpdatedByAdminId() {
        return updatedByAdminId;
    }
    public void setUpdatedByAdminId(Long updatedByAdminId) {
        this.updatedByAdminId = updatedByAdminId;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    @Override
    public String toString() {
        return "CountryEntity [id=" + id + ", countryName=" + countryName + ", countryDescription=" + countryDescription
                + ", isDeleted=" + isDeleted + ", createdByAdminId=" + createdByAdminId + ", updatedByAdminId="
                + updatedByAdminId + ", countryCode=" + countryCode + ", createTime=" + createTime + ", updatedTime="
                + updatedTime + ", getId()=" + getId() + ", getCountryName()=" + getCountryName()
                + ", getCountryDescription()=" + getCountryDescription() + ", getIsDeleted()=" + getIsDeleted()
                + ", getCreatedByAdminId()=" + getCreatedByAdminId() + ", getUpdatedByAdminId()="
                + getUpdatedByAdminId() + ", getCountryCode()=" + getCountryCode() + ", getClass()=" + getClass()
                + ", getCreateTime()=" + getCreateTime() + ", getUpdatedTime()=" + getUpdatedTime() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

    

}
