package com.deng.book_review_publishing.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.repository.AdminRepository;
import com.deng.book_review_publishing.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findById(Long id) {
        try {
            if (id == null || id <= 0) {
                log.error("Invalid admin ID provided: {}", id);
                throw new IllegalArgumentException("Admin ID must be a positive number");
            }
            log.debug("Finding admin by ID: {}", id);
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
            log.info("Admin found: {}", admin);
            return admin;
        } catch (Exception e) {
            log.error("Error finding admin by ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error finding admin", e);
        }
    }

    @Override
    public Boolean update(Long id, Admin admin) {
        try {
            if (id == null || id <= 0) {
                log.error("Invalid admin ID provided: {}", id);
                throw new IllegalArgumentException("Admin ID must be a positive number");
            }
            if (admin == null) {
                log.error("Admin object is null");
                throw new IllegalArgumentException("Admin object cannot be null");
            }
            if (!existsById(id)) {
                log.warn("Admin with ID {} does not exist", id);
                return false;
            }
            Admin existingAdmin = adminRepository.findById(id).orElseThrow(RuntimeException::new);
            existingAdmin.setLoginName(admin.getLoginName());
            existingAdmin.setAdminNickname(admin.getAdminNickname());
            existingAdmin.setLocked(admin.getLocked());
            log.debug("Updating admin with ID: {}", id);
            log.info("Admin updated successfully: {}", existingAdmin);
            boolean isUpdated = adminRepository.save(existingAdmin) != null;
            log.info("Admin updated resultat: {}", isUpdated);
            return isUpdated;
        } catch (Exception e) {
            log.error("Error updating admin with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error updating admin", e);
        }
    }  

    @Override
    public Boolean existsById(Long id) {
        try {
            if (id == null || id <= 0) {
                log.error("Invalid admin ID provided: {}", id);
                throw new IllegalArgumentException("Admin ID must be a positive number");
            }
            log.debug("Checking existence of admin with ID: {}", id);
            return adminRepository.existsById(id);
        } catch (Exception e) {
            log.error("Error checking admin existence for ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error checking admin existence", e);
        }
    }

    @Override
    @Cacheable(value = "adminCount")
    public Long count() {
        try {
            log.debug("Counting total number of admins");
            Long count = adminRepository.count();
            log.info("Total number of admins: {}", count);
            return count;
        } catch (Exception e) {
            log.error("Error counting admins: {}", e.getMessage());
            throw new RuntimeException("Error counting admins", e);
        }
    }

    @Override
    public Boolean updateAdminLockStatus(Long id, byte lockedStatus) {
        try {
            if (id == null) {
                log.debug("Received null id for deleteById");
                return false;
            }
            if (id <= 0) {
                log.error("Invalid admin ID provided: {}", id);
                return false;
            }
            if (lockedStatus != 0 && lockedStatus != 1) {
                log.error("Invalid locked status provided: {}", lockedStatus);
                return false;
            }

            if (!existsById(id)) {
                log.warn("Admin with ID {} does not exist", id);
                return false;
            }

            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
            System.out.println("admin = " + admin);
            if (admin == null) {
                log.warn("Admin with ID {} does not exist", id);
                return false;
            }
            if (admin.getLocked() == lockedStatus) {
                log.info("Admin with ID {} already has the specified locked status {}", id, lockedStatus);
                return true; // No update needed
            }
            int result = adminRepository.updateAdminLockStatus(id, lockedStatus); // Update the admin entity in the database
            System.out.println("result = " + result);
            if (result == 0) {
                log.warn("Admin with ID {} does not updated", id);
                return false;    
            }

            // Admin updatedAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Updated Admin not found")); // Refresh the admin entity from the database
            // System.out.println("updatedAdmin = " + updatedAdmin);
            // if (updatedAdmin == null) {
            //     log.warn("Failed to retrieve updated admin with ID: {}", id);
            //     return false;
                
            // }
            // if (updatedAdmin.getLocked() != lockedStatus) {
            //     log.warn("Admin with ID {} locked status update failed", id);
            //     return false; 
            // }
            
            log.info("Admin locked status set to {} for ID: {}", lockedStatus, id);
            return result == 1; // Return true if the update was successful
        
        } catch (Exception e) {
            log.error("Error setting admin locked status for ID {}: {}", id, e.getMessage());
            // throw new RuntimeException("Error setting admin locked status", e);
            return false;
        }
    }
    
}
