package com.deng.book_review_publishing.controller.admin.adminAPI;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.entity.ErrorResponse;
import com.deng.book_review_publishing.service.BookReviewService;
import com.deng.book_review_publishing.utils.ValidateUtil;

@RestController
@RequestMapping("/admin/api/bookReview")
public class BookReviewController {
    private static final Logger logger = LoggerFactory.getLogger(BookReviewController.class);
    private final BookReviewService bookReviewService;
    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    /**
     * get BookReview by id
     * @param id BookReview ID
     * @return ResponseEntity<?>
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookReviewById(@PathVariable Long id) {
        try {
            logger.debug("Fetching bookReview with id: {}", id);
            if (id == null) {
                logger.warn("Invalid review id provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid review id", HttpStatus.BAD_REQUEST));
            }
            BookReview bookReview = bookReviewService.findBookReviewById(id);
            if (bookReview == null) {
                logger.warn("BookReview with id: {} not found", id);
                return ResponseEntity.notFound().build();
            } 
            logger.info("Found bookReview: {}", bookReview);
            return ResponseEntity.ok(bookReview);
        } catch (Exception e) {
            logger.error("Error fetching bookReview with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * update BookReview isDeleted
     * @param id BookReview ID
     * @param isDeleted new isDeleted value, 0 means not deleted, 1 means deleted
     * @return ResponseEntity<?> 
     */
    @PutMapping("/{id}/updateIsDeleted")
    public ResponseEntity<?> updateBookReviewIsDeleted(@PathVariable Long id, @RequestParam(defaultValue = "0") Byte isDeleted) {
        try {
            logger.debug("Updating bookReview with id: {} isDeleted: {}", id, isDeleted);
            if (id == null) {
                logger.warn("Invalid review id provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid review id", HttpStatus.BAD_REQUEST));
            }
            BookReview bookReview = bookReviewService.findBookReviewById(id);
            if (bookReview == null) {
                logger.warn("BookReview with id: {} not found", id);
                return ResponseEntity.notFound().build();
            }
            if (isDeleted != 0 && isDeleted != 1) {
                logger.warn("Invalid isDeleted value provided: {}", isDeleted);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid isDeleted value", HttpStatus.BAD_REQUEST)); 
            } 
            if (bookReview.getIsDeleted() == isDeleted) {
                logger.warn("BookReview with id: {} is already in the desired state isDeleted: {}", id, isDeleted);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("BookReview is already in the desired state", HttpStatus.BAD_REQUEST));
            }
            bookReview.setIsDeleted(isDeleted);
            Boolean result = bookReviewService.updateBookReview(id, bookReview);
            if (!result) {
                logger.warn("Failed to update bookReview with id: {} isDeleted: {}", id, isDeleted);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Failed to update bookReview", HttpStatus.BAD_REQUEST));
            }
            logger.info("Updated bookReview: {}", bookReview);
            return ResponseEntity.ok(bookReview);
        } catch (Exception e) {
            logger.error("Error updating bookReview with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error updating bookReview", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * update BookReview batch reviewStatus
     * @param ids BookReview IDs
     * @param reviewStatus new reviewStatus value, 0 means draft, 1 means not published, 2 means published
     * @return ResponseEntity<?> 
     */
    @PutMapping("/batch/reviewStatus")
    public ResponseEntity<?> updateBookReviewBatchReviewStatusById(
       @RequestBody Long[] ids,
       @RequestParam(defaultValue = "0") Byte reviewStatus
    ) {
        try {
            logger.debug("Updating bookReview batch reviewStatus with ids: {} reviewStatus: {}", Arrays.toString(ids), reviewStatus);
            if (ids == null || ids.length == 0) {
                logger.warn("Invalid ids provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid ids provided", HttpStatus.BAD_REQUEST));
            }
            if (reviewStatus != 0 && reviewStatus != 1 && reviewStatus != 2) {
                logger.warn("Invalid reviewStatus value provided: {}", reviewStatus);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid reviewStatus value", HttpStatus.BAD_REQUEST));
            }
            
            Boolean result = bookReviewService.modifyStatusBatch(ids, reviewStatus);
            if (!result) {
                logger.warn("Failed to update bookReview batch reviewStatus with ids: {} reviewStatus: {}", ids, reviewStatus);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Failed to update bookReview batch reviewStatus", HttpStatus.BAD_REQUEST));
            }
            logger.info("Updated bookReview batch reviewStatus with ids: {} reviewStatus: {}", ids, reviewStatus);
            return ResponseEntity.ok().body(new ErrorResponse("Updated bookReview batch reviewStatus", HttpStatus.OK));
        } catch (Exception e) {
            logger.error("Error updating bookReview batch reviewStatus with ids {}: {}", ids, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error updating bookReview batch reviewStatus", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * update BookReview batch isDeletedView
     * @param ids BookReview IDs
     * @param isDeletedView new isDeletedView value, 0 means not deleted, 1 means deleted
     * @return ResponseEntity<?> 
     */
    @PutMapping("/batch/inactive")
    public ResponseEntity<?> updateBookReviewBatchInactiveById(
       @RequestBody Long[] ids,
       @RequestParam(defaultValue = "0") Byte isDeletedView
    ) {
        try {
            logger.debug("Updating bookReview batch inactive with ids: {}", Arrays.toString(ids));
            if (ids == null || ids.length == 0) {
                logger.warn("Invalid ids provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid ids provided", HttpStatus.BAD_REQUEST));
            }
            Boolean result = bookReviewService.modifyIsDeletedViewBatch(ids, (byte) 1);
            if (!result) {
                logger.warn("Failed to update bookReview batch inactive with ids: {}", Arrays.toString(ids));
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Failed to update bookReview batch inactive", HttpStatus.BAD_REQUEST));
            }
            logger.info("Updated bookReview batch inactive with ids: {}", Arrays.toString(ids));
            return ResponseEntity.ok().body(new ErrorResponse("Updated bookReview batch inactive", HttpStatus.OK));
        } catch (Exception e) {
            logger.error("Error updating bookReview batch inactive with ids {}: {}", Arrays.toString(ids), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error updating bookReview batch inactive", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * update BookReview reviewStatus
     * @param id BookReview ID
     * @param reviewStatus new reviewStatus value, 0 means draft, 1 means not published, 2 means published
     * @return ResponseEntity<?> 
     */
    @PutMapping("/{id}/updateReviewStatus")
    public ResponseEntity<?> updateBookReviewReviewStatus(@PathVariable Long id, @RequestParam(defaultValue = "0") Byte reviewStatus) {
        try {
            logger.debug("Updating bookReview with id: {} reviewStatus: {}", id, reviewStatus);
            if (id == null) {
                logger.warn("Invalid review id provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid review id", HttpStatus.BAD_REQUEST));
            }
            BookReview bookReview = bookReviewService.findBookReviewById(id);
            if (bookReview == null) {
                logger.warn("BookReview with id: {} not found", id);
                return ResponseEntity.notFound().build();
            }
            if (reviewStatus != 0 && reviewStatus != 1 && reviewStatus != 2) {
                logger.warn("Invalid reviewStatus value provided: {}", reviewStatus);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid reviewStatus value", HttpStatus.BAD_REQUEST));
            }
            if (bookReview.getReviewStatus() == reviewStatus) {
                logger.warn("BookReview with id: {} is already in the desired state reviewStatus: {}", id, reviewStatus);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("BookReview is already in the desired state", HttpStatus.BAD_REQUEST));
            }
            bookReview.setReviewStatus(reviewStatus);
            bookReviewService.updateBookReview(id, bookReview);
            logger.info("Updated bookReview: {}", bookReview);
            return ResponseEntity.ok(bookReview);
        } catch (Exception e) {
            logger.error("Error updating bookReview with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error updating bookReview", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * Add a new bookReview
     * @param bookReview the bookReview to add
     * @return ResponseEntity<?> with the created bookReview or an error response
     */
    @PostMapping("/addBookReview")
    public ResponseEntity<?> addBookReview(@RequestBody BookReview bookReview) {
        try {
            logger.debug("Creating bookReview: {}", bookReview);
            Boolean isSaved = bookReviewService.saveBookReview(bookReview);
            if (!isSaved) {
                logger.warn("Failed to create bookReview: {}", bookReview);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Failed to create bookReview", HttpStatus.BAD_REQUEST));
            }

            logger.info("Created bookReview: {}", bookReview);
            return ResponseEntity.ok(bookReview);
        } catch (Exception e) {
            logger.error("Error creating bookReview: {}", bookReview, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error creating bookReview", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * Get bookReviews by filters with pagination and sorting
     * @param pageNum
     * @param pageSize default 10, max 100
     * @param sortField
     * @param sortDirection default asc, other values: desc
     * @param bookId
     * @param reviewAuthor
     * @param reviewAuthorId
     * @param reviewContent
     * @param reviewTitle
     * @param views
     * @param reviewStatus default 0 (draft), Other values: 1 (published), 2 (not published)
     * @param isDeleted default 0 (no deleted), other values: 1 (deleted)
     * @param year
     * @param month
     * @param sortBy
     * @param sortOrder
     * @return
     */
    @GetMapping("/bookReviewByFilters")
    public ResponseEntity<?> bookReviewByFilters(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) Long bookId,
        @RequestParam(required = false) String reviewAuthor,
        @RequestParam(required = false) Long reviewAuthorId,
        @RequestParam(required = false) String reviewContent,
        @RequestParam(required = false) String reviewTitle,
        @RequestParam(required = false) Integer views,
        @RequestParam(required = false, defaultValue = "0") Byte reviewStatus,
        @RequestParam(required = false, defaultValue = "0") Byte isDeleted,
    @RequestParam(required = false) Integer year,
    @RequestParam(required = false) Integer month,
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String sortOrder
    ) {
        try {
            logger.debug("Fetching bookReviews by filters with pagination and sorting");
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookId", "reviewAuthor", "reviewAuthorId", "reviewTitle", "views", "reviewStatus", "isDeleted"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);

            if (reviewStatus != 0 && reviewStatus != 1 && reviewStatus != 2) {
                logger.error("Invalid reviewStatus value: {}", reviewStatus);
                reviewStatus = 1;
            }
            if (isDeleted != 0 && isDeleted != 1) {
                logger.error("Invalid isDeleted value: {}", isDeleted);
                isDeleted = 0;
            }
            // only validate year/month if provided; leave null to mean "no filter"
            if (year != null && year < 1900) {
                logger.error("Invalid year value: {}", year);
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid year value", HttpStatus.BAD_REQUEST));
            }
            if (month != null && (month < 1 || month > 12)) {
                logger.error("Invalid month value: {}", month);
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid month value", HttpStatus.BAD_REQUEST));
            }
            // if (views == null || views < 0) {
            //     logger.error("Invalid view value: {}", views);
            //     views = 0;
            // }

            Page<BookReview> bookReviewsPage = bookReviewService.findBookReviewByFilters(
                pageNum, pageSize, sortField, sortDirection, 
                bookId, reviewAuthor, reviewAuthorId, reviewContent, reviewTitle, 
                views, reviewStatus, isDeleted, year, month);
            
            logger.info("Filtered bookReviews: {}", bookReviewsPage);
            if (bookReviewsPage != null && bookReviewsPage.hasContent()) {
                logger.info("Found {} bookReviews", bookReviewsPage.getTotalElements());
                return ResponseEntity.ok(bookReviewsPage);
            } else {
                logger.warn("No bookReviews found");
                return ResponseEntity.noContent().build();  
            }

        } catch (Exception e) {
            logger.error("Error fetching books: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error fetching books");
        }
    }


    
}
