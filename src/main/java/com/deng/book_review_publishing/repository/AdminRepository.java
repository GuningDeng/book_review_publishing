package com.deng.book_review_publishing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deng.book_review_publishing.entity.Admin;

import jakarta.transaction.Transactional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    /**
     * update admin lock status
     * @param adminId
     * @param lockedStatus
     * @return 
     * Returns the number of rows affected (should be 1 if successful, 0 if no admin found)
     */
    @Modifying
    @Transactional
    @Query("UPDATE Admin a SET a.locked = :lockedStatus WHERE a.id = :adminId")
    public int updateAdminLockStatus(@Param("adminId") Long adminId, @Param("lockedStatus") byte lockedStatus);
}
