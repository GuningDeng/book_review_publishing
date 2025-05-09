package com.deng.book_review_publishing.entity;

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
@Table(name = "tbl_author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long Id;
    @Column(name = "author_first_name")
    private String firstName;
    @Column(name = "author_last_name")
    private String lastName;
    @Column(name = "author_gender")
    private byte authorGender;
    @Column(name = "author_country")
    @Enumerated(EnumType.STRING)
    private Country countryName;
    @Column(name = "author_status")
    private byte authorStatus;
    @Column(name = "author_is_deleted")
    private byte isDeleted;
    
    public Long getId() {
        return Id;
    }


    public void setId(Long id) {
        Id = id;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName == null? null : lastName.trim();
    }


    public byte getAuthorGender() {
        return authorGender;
    }


    public void setAuthorGender(byte authorGender) {
        this.authorGender = authorGender;
    }


    public Country getCountryName() {
        return countryName;
    }


    public void setCountryName(Country countryName) {
        this.countryName = countryName;
    }


    public byte getAuthorStatus() {
        return authorStatus;
    }


    public void setAuthorStatus(byte authorStatus) {
        this.authorStatus = authorStatus;
    }


    public byte getIsDeleted() {
        return isDeleted;
    }


    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }   

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", Id=").append(Id);
        sb.append(", firstName=").append(firstName);
        sb.append(", lastName=").append(lastName);
        sb.append(", authorGender=").append(authorGender);
        sb.append(", countryName=").append(countryName);
        sb.append(", authorStatus=").append(authorStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
        
}
