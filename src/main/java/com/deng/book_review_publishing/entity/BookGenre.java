package com.deng.book_review_publishing.entity;

import java.util.Date;

import com.deng.book_review_publishing.entity.enums.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_book_genre")
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_genre_id")
    private Long id;

    @Column(name = "book_genre_name")
    @Enumerated(EnumType.STRING)
    private Genre genreName;
    @Column(name = "book_genre_description")
    private String genreDescription;
    @Column(name = "is_deleted")
    private byte isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Brussels")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "created_by_admin_id")
    private Long createdByAdminId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Brussels")
    @Column(name = "updated_time")
    private Date updatedTime;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenreName() {
        return genreName;
    }

    public void setGenreName(Genre genreName) {
        this.genreName = genreName;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatedByAdminId() {
        return createdByAdminId;
    }

    public void setCreatedByAdminId(Long createdByAdminId) {
        this.createdByAdminId = createdByAdminId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", categoryName=").append(genreName);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createTime=").append(createTime);
        sb.append(", createdByAdminId=").append(createdByAdminId);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
    
}
