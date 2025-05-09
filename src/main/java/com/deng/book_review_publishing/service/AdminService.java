package com.deng.book_review_publishing.service;

import com.deng.book_review_publishing.entity.Admin;

public interface AdminService {
    public Admin findById(Long id);
    public Boolean update(Long id, Admin admin);
    public Boolean existsById(Long id);
    public Boolean updateAdminLockStatus(Long id, byte lockedStatus);
    public Long count();
}
