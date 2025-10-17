package com.deng.book_review_publishing.controller.admin.adminAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deng.book_review_publishing.entity.BookGenre;
import com.deng.book_review_publishing.service.BookGenreService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin/api/book-genres")
public class BookGenreController {
    // This class will handle HTTP requests related to book genres.
    // It will use the BookGenreService to perform operations such as creating, updating, deleting, and retrieving book genres.
    // The methods will return appropriate responses based on the success or failure of the operations.
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final BookGenreService bookGenreService;
    public BookGenreController(BookGenreService bookGenreService) {
        this.bookGenreService = bookGenreService;
    }

    // Example method signatures:
    
    /**
     * Get book genre by ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookGenreById(@PathVariable Long id) {
        try {
            if (id <= 0) {
                logger.warn("Invalid book genre ID provided: {}", id);
                return ResponseEntity.badRequest().body("Invalid book genre ID");
                
            }
            BookGenre bookGenre = bookGenreService.findBookGenreById(id);
            if (bookGenre == null) {
                logger.info("Book genre not found with ID: {}", id);
                return ResponseEntity.status(404).body("Book genre not found");
            }
            return ResponseEntity.ok(bookGenre);
        } catch (Exception e) {
            logger.error("Error fetching book genre with ID: {}", id, e);
            return ResponseEntity.status(500).body("Internal server error while fetching book genre");
        }
    }
    
    /**
     * Update book genre delete status
     * @param id
     * @param isDeleted 0: not deleted, 1: deleted
     * @return
     */
    @PutMapping("/{id}/isDeleted")
    public ResponseEntity<?> updateBookGenreIsDeleteStatus(@PathVariable Long id, @RequestParam(defaultValue = "0") byte isDeleted) {
        try {
            if (id <= 0) {
                logger.warn("Invalid book genre ID provided: {}", id);
                return ResponseEntity.badRequest().body("Invalid book genre ID");
            }
            BookGenre bookGenre = bookGenreService.findBookGenreById(id);
            if (bookGenre == null) {
                logger.info("Book genre not found with ID: {}", id);
                return ResponseEntity.status(404).body("Book genre not found");
            }
            if (isDeleted < 0 || isDeleted > 1) {
                logger.warn("Invalid isDeleted value provided: {}", isDeleted);
                return ResponseEntity.badRequest().body("Invalid isDeleted value, must be 0 or 1");
                
            }
            if (isDeleted == bookGenre.getIsDeleted()) {
                logger.info("Book genre with ID: {} already has isDeleted status: {}", id, isDeleted);
                return ResponseEntity.ok("Book genre already has the specified delete status");
                
            }
            boolean isUpdated = bookGenreService.updateBookGenreIsDeleteStatus(id, isDeleted);
            if (isUpdated) {
                logger.info("Book genre with ID: {} updated successfully with isDeleted status: {}", id, isDeleted);
                return ResponseEntity.ok("Book genre delete status updated successfully");
            } else {
                logger.warn("Failed to update book genre with ID: {}", id);
                return ResponseEntity.status(500).body("Failed to update book genre delete status");
            }

        } catch (Exception e) {
            logger.error("Error updating book genre delete status for ID: {}", id, e);
            return ResponseEntity.status(500).body("Internal server error while updating book genre delete status");
        }
    }
    
    /**
     * Get all book genres
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllBookGenres() {
        try {
            var bookGenres = bookGenreService.findAllBookGenres();
            if (bookGenres.isEmpty()) {
                logger.info("No book genres found");
                return ResponseEntity.status(404).body("No book genres found");
            }
            return ResponseEntity.ok(bookGenres);
        } catch (Exception e) {
            logger.error("Error fetching all book genres", e);
            return ResponseEntity.status(500).body("Internal server error while fetching book genres");
        }
    }
}
