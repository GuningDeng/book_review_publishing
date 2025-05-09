package com.deng.book_review_publishing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "login_name")
    private String loginName;
    @Column(name = "admin_nickname")
    private String adminNickname;
    @Column(name = "locked")
    private byte locked;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }
    public String getAdminNickname() {
        return adminNickname;
    }
    public void setAdminNickname(String adminNickname) {
        this.adminNickname = adminNickname == null? null : adminNickname.trim();
    }
    public byte getLocked() {
        return locked;
    }
    public void setLocked(byte locked) {
        this.locked = locked;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", adminNickname=").append(adminNickname);
        sb.append(", locked=").append(locked);
        sb.append("]");
        return sb.toString();
    }

    
    
    
}
