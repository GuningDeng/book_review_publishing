package com.deng.book_review_publishing.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.repository.BookReviewRepository;
import com.deng.book_review_publishing.service.BookReviewService;
import com.deng.book_review_publishing.utils.ValidateUtil;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReviewServiceImpl implements BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    public BookReviewServiceImpl(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }
    @Override
    public BookReview findBookReviewById(Long id) {
        try {
            if (id == null) {
                logger.error("Book review ID is null");
                return null;
            }
            if (id <= 0) {
                logger.error("Book review ID is less than or equal to 0");
                return null;
            }
            BookReview bookReview = bookReviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Book review not found"));
            logger.info("Book review found: {}", bookReview);
            return bookReview;
        } catch (Exception e) {
            logger.error("Error finding book review by ID: {}", e.getMessage());
            return null;
        }
    }
    
    @Override
    public Long count() {
        try {
            Long count = bookReviewRepository.count();
            logger.info("Book review count: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Error counting books: {}", e.getMessage());
            return null;
        }
    }
    
    @Override
    public Long publishedBookReviewCount() {
        try {
            // Use count query instead of fetching actual records
            Long count = bookReviewRepository.countByReviewStatusAndIsDeleted((byte) 0, (byte) 0);
            logger.debug("Found {} published book reviews", count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to count published book reviews", e);
            throw new RuntimeException("Failed to count published book reviews", e);
        }
    }
    @Override
    public Long deletedBookReviewCount() {
        try {
            // Use count query instead of fetching actual records
            Long count = bookReviewRepository.countByIsDeleted((byte) 1);
            logger.debug("Found {} deleted book reviews", count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to count deleted book reviews", e);
            throw new RuntimeException("Failed to count deleted book reviews", e);
        }
    }
    @Override
    public Boolean existsById(Long id) {
        try {
            if (id == null) {
                logger.error("Book review ID is null");
                return false;
            }
            if (id <= 0) {
                logger.error("Book review ID is less than or equal to 0");
                return false;
            }
            Boolean exists = bookReviewRepository.existsById(id);
            logger.info("Book review exists: {}", exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking if book review exists: {}", e.getMessage());
            return false;
        }
    }
    @Override
    public Boolean modifyStatusBatch(Long[] ids, byte reviewStatus) {
        try {
            if (ids == null || ids.length == 0) {
                logger.error("IDs array is null or empty");
                return false;
            }
            if (reviewStatus < 0 || reviewStatus > 2) {
                logger.error("Invalid review status: {}", reviewStatus);
                return false;
            }
            for (Long id : ids) {
                if (id == null || id <= 0) {
                    logger.error("Invalid book review ID: {}", id);
                    return false;
                }
            }
            int updatedCount = bookReviewRepository.inactiveBatch(ids, reviewStatus);
            
            logger.debug("Updated {} book reviews to status {}", updatedCount, reviewStatus);

            if (updatedCount != ids.length) {
                logger.warn("Not all book reviews were updated. Expected: {}, Actual: {}", ids.length, updatedCount);
                return false;
            }
            
            logger.info("Modified status of book reviews with IDs: {}", (Object) ids);
            
            // Verify the status of updated authors
            boolean modifiedAllStatus = true;
            for (Long id : ids) {
                BookReview bookReview = bookReviewRepository.findById(id).orElse(null);
                if (bookReview == null || bookReview.getReviewStatus() != reviewStatus) {
                    logger.error("Book review with ID {} was not updated correctly", id);
                    modifiedAllStatus = false;
                    break;
                }
            }
            
            return modifiedAllStatus;
        } catch (Exception e) {
            logger.error("Error modifying status batch: {}", e.getMessage());
            return false;
        }
    }
    @Override
    public Boolean modifyIsDeletedViewBatch(Long[] ids, byte isDeleted) {
        try {
            if (ids == null || ids.length == 0) {
                logger.error("IDs array is null or empty");
                return false;
            }
            if (isDeleted < 0 || isDeleted > 1) {
                logger.error("Invalid isDeleted value: {}", isDeleted);
                return false;
            }
            for (Long id : ids) {
                if (id == null || id <= 0) {
                    logger.error("Invalid book review ID: {}", id);
                    return false;
                }
            }
            int updatedCount = bookReviewRepository.deleteBatch(ids, isDeleted);
            if (updatedCount != ids.length) {
                logger.warn("Not all book reviews were updated. Expected: {}, Actual: {}", ids.length, updatedCount);
                return false;
            }

            Boolean allSuccessed = true;
            for (Long id : ids) {
                BookReview bookReview = bookReviewRepository.findById(id).orElse(null);
                if (bookReview == null || bookReview.getIsDeleted() != isDeleted) {
                    logger.error("Book review with ID {} was not updated correctly", id);
                    allSuccessed = false;
                    break;
                }
            }
            logger.info("Updated {} book reviews to isDeleted = {}", updatedCount, isDeleted);
            return allSuccessed;
        } catch (Exception e) {
            logger.error("Error deleting or keeping book reviews in batch: {}", e.getMessage());
            return false;
        }
    }
    @Override
    public Page<BookReview> findBookReviewByFilters(int pageNum, int pageSize, String sortField, String sortDirection, 
    Long bookId, String reviewAuthor, Long reviewAuthorId, String reviewContent, String reviewTitle, 
    Integer views, byte reviewStatus, byte isDeleted, Integer year, Integer month) {
        
            try {
                pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
                pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
                sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookId", "reviewAuthor", "reviewAuthorId", "reviewTitle", "views", "reviewStatus", "isDeleted"}, "id");
                sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
                
                logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);
                
                if (isDeleted != 0 && isDeleted != 1) {
                    isDeleted = 0;                    
                }

                if (reviewStatus!=0 && reviewStatus!=1 && reviewStatus!=2) {
                    reviewStatus = 0;
                }

                Pageable pageable = PageRequest.of(pageNum, pageSize, ValidateUtil.createSort(sortField, sortDirection));
                Page<BookReview> bookReviewPage = bookReviewRepository.findBookReviewByFilters(bookId, reviewAuthor, reviewAuthorId, reviewContent, reviewTitle, views, reviewStatus, isDeleted, year, month, pageable);
                logger.info("book review found: {}", bookReviewPage);
                return bookReviewPage;

            } catch (Exception e) {
                logger.error("Error finding book reviews by filters: {}", e.getMessage());
                return null;
            }
    }
    @Override
    public Boolean saveBookReview(BookReview bookReview) {
        try {
            if (bookReview == null) {
                logger.error("New Bookreview is empty", bookReview);
                return false;
            }
            if (bookReview.getBookId() == null) {
                logger.error("Book ID is null", bookReview.getBookId());
                return false;    
            }
            if (bookReview.getBookId() <= 0) {
                logger.error("Invalid book ID", bookReview.getBookId());
                return false;
            }
            if (bookReview.getReviewTitle().isEmpty()) {
                logger.error("Book review title is empty", bookReview.getReviewTitle());
                return false;
            }
            if (bookReview.getReviewAuthor().isEmpty()) {
                logger.error("Book review author is empty", bookReview);
                return false;
            }
            if (bookReview.getReviewAuthorId() == null) {
                logger.error("Book review author ID is null", bookReview);
                return false;
            }
            if (bookReview.getReviewAuthorId() <= 0) {
                logger.error("Invalid book review author ID", bookReview);
                return false;
            }

            logger.info("Successfully added new BookReview", bookReview);
            return bookReviewRepository.save(bookReview) != null;
        } catch (Exception e) {
            logger.error("Error adding new BookRevoew: {}", e.getMessage());
            return false;
        }
    }
    @Override
    public Boolean updateBookReview(Long id, BookReview bookReview) {
        try {
            if (id == null) {
                logger.error("Book ID is null");
                return false;
            }
            if (id <= 0) {
                logger.error("Book ID is less than or equal to 0");
                return false;
            }
            if (bookReview == null) {
                logger.error("Book review is null");
                return false;
            }
            if (!bookReviewRepository.existsById(id)) {
                logger.error("Book review with ID {} does not exist", id);
                return false;
            }
            BookReview existingBookReview = bookReviewRepository.findById(id).orElse(null);
            if (existingBookReview == null) {
                logger.error("Book review with ID {} not found", id);
                return false;
            }
            // Update fields
            existingBookReview.setBookId(bookReview.getBookId());
            existingBookReview.setReviewAuthor(bookReview.getReviewAuthor());
            existingBookReview.setReviewAuthorId(bookReview.getReviewAuthorId());
            existingBookReview.setReviewContent(bookReview.getReviewContent());
            existingBookReview.setReviewTitle(bookReview.getReviewTitle());
            existingBookReview.setViews(bookReview.getViews());
            existingBookReview.setReviewStatus(bookReview.getReviewStatus());
            existingBookReview.setIsDeleted(bookReview.getIsDeleted());
            existingBookReview.setUpdatedTime(new Date());
            existingBookReview.setUpdatedBy(bookReview.getUpdatedBy());
            return bookReviewRepository.save(existingBookReview) != null;
        } catch (Exception e) {
            logger.error("Error updating book review: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookReview> findRecentBookReviewsByStatus(byte reviewStatus, int limit) {
        try {
            List<BookReview> recent = bookReviewRepository.findAll().stream()
                .filter(br -> br.getReviewStatus() == reviewStatus)
                .sorted((br1, br2) -> br2.getCreateTime().compareTo(br1.getCreateTime()))
                .limit(limit)
                .collect(Collectors.toList());
            if (recent == null || recent.isEmpty()) {
                return java.util.Collections.emptyList();
            }
            // List<BookReview> recent = all.stream().limit(limit).collect(Collectors.toList());
            logger.info("Fetched {} recent book reviews with status {}", recent.size(), reviewStatus);
            return recent;
        } catch (Exception e) {
            logger.error("Error fetching recent book reviews by status: {}", e.getMessage(), e);
            return java.util.Collections.emptyList();
        }
    }
    
}
