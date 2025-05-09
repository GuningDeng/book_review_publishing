package com.deng.book_review_publishing.controller.admin.adminAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.entity.ErrorResponse;
import com.deng.book_review_publishing.service.AdminService;

@RestController
@RequestMapping("/admin/api")
@Validated
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        if (id <= 0) {
            logger.warn("Invalid admin ID provided: {}", id);
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid admin ID", HttpStatus.BAD_REQUEST));
        }

        try {
            logger.debug("Fetching admin with ID: {}", id);
            Admin admin = adminService.findById(id);
            if (admin == null) {
                logger.info("Admin not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Admin not found", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            logger.error("Error fetching admin with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        if (id <= 0) {
            logger.warn("Invalid admin ID provided: {}", id);
            return ResponseEntity.badRequest()
               .body(new ErrorResponse("Invalid admin ID", HttpStatus.BAD_REQUEST));
        }

        try {
            if (admin == null) {
                logger.warn("Admin object is null");
                return ResponseEntity.badRequest()
                  .body(new ErrorResponse("Admin object cannot be null", HttpStatus.BAD_REQUEST));
            }
            if (!adminService.existsById(id)) {
                logger.warn("Admin with ID {} does not exist", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponse("Admin not found", HttpStatus.NOT_FOUND));
            }
            logger.debug("Updating admin with ID: {}", id);
            boolean isUpdated = adminService.update(id, admin);
            if (isUpdated) {
                logger.info("Admin updated successfully with ID: {}", id);
                return ResponseEntity.ok().build();
            }
            else {
                logger.warn("Admin not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                  .body(new ErrorResponse("Update admin is failed", HttpStatus.NOT_IMPLEMENTED));
            }

        }
        catch (Exception e) {
            logger.error("Error updating admin with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAdminStatus(@PathVariable Long id, @RequestParam(defaultValue = "0") byte locked) {
        if (id <= 0) {
            logger.warn("Invalid admin ID provided: {}", id);
            return ResponseEntity.badRequest()
              .body(new ErrorResponse("Invalid admin ID", HttpStatus.BAD_REQUEST));
        }
        if (locked != 0 && locked != 1) {
            logger.warn("Invalid admin status provided: {}", locked);
            return ResponseEntity.badRequest()
              .body(new ErrorResponse("Invalid admin status", HttpStatus.BAD_REQUEST));
            
        }

        try {
            logger.debug("Updating admin status with ID: {}", id);
            Admin admin = adminService.findById(id);
            if (admin == null) {
                logger.warn("Admin not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(new ErrorResponse("Admin not found", HttpStatus.NOT_FOUND));
            }
            if (admin.getLocked() == locked) {
                logger.warn("Admin status is already set to: {}", locked);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Admin status is already set to: " + locked, HttpStatus.BAD_REQUEST));
            }

            boolean isUpdated = adminService.updateAdminLockStatus(id, locked);
            if (isUpdated) {
                logger.info("Admin status updated successfully with ID: {}", id);
                return ResponseEntity.ok().build();
            }
            else {
                logger.warn("Admin status update failed with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ErrorResponse("Update admin status is failed", HttpStatus.NOT_IMPLEMENTED));
            }
        }
        catch (Exception e) {
            logger.error("Error updating admin status with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body(new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}


