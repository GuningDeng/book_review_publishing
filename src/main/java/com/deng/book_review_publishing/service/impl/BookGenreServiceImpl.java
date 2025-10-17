package com.deng.book_review_publishing.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deng.book_review_publishing.entity.BookGenre;
import com.deng.book_review_publishing.repository.BookGenreRepository;
import com.deng.book_review_publishing.service.BookGenreService;

@Service
public class BookGenreServiceImpl implements BookGenreService {

    // Implement methods from BookGenreService interface here
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final BookGenreRepository bookGenreRepository;
    public BookGenreServiceImpl(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }
    @Override
    public BookGenre findBookGenreById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book genre ID: {}", id);
                return null;                
            }
            return bookGenreRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Book genre not found with ID: " + id));
        } catch (Exception e) {
            logger.error("Error finding book genre by ID: {}", id, e);
            return null;
        }
    }
    @Override
    public List<BookGenre> findAllBookGenres() {
        try {
            return bookGenreRepository.findAll();
        } catch (Exception e) {
            logger.error("Error finding all book genres", e);
            return List.of(); // Return an empty list in case of error
        }
    }
    @Override
    public BookGenre createBookGenre(BookGenre bookGenre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBookGenre'");
    }
    @Override
    public boolean isExistsById(Long id) {
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book genre ID: {}", id);
                return false;                
            }
            return bookGenreRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error checking existence of book genre by ID: {}", id, e);
            return false;
        }
    }
    @Override
    public Long countBookGenres() {
        try {
            return bookGenreRepository.count();
        } catch (Exception e) {
            logger.error("Error counting book genres", e);
            return 0L; // Return 0 in case of error
        }
    }
    @Override
    public boolean updateBookGenreIsDeleteStatus(Long id, byte isDeleted) {
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book genre ID: {}", id);
                return false;                
            }
            if (isDeleted != 0 && isDeleted != 1) {
                logger.error("Invalid isDeleted value: {}", isDeleted);
                return false;                
            }
            BookGenre bookGenre = bookGenreRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Book genre not found with ID: " + id));
            if (isDeleted == bookGenre.getIsDeleted()) {
                logger.info("No change in delete status for book genre ID: {}", id);
                return true; // No change needed
                
            }
            bookGenre.setIsDeleted(isDeleted);
            bookGenreRepository.save(bookGenre); // Save the updated book genre

            // Refresh the entity from the database to get the latest state
            BookGenre updatedBookGenre = bookGenreRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Book genre not found with ID: " + id));
            
            if (updatedBookGenre.getIsDeleted() != isDeleted) {
                logger.warn("Book genre delete status update failed for ID: {}", id);
                return false;
            }   
            logger.info("Book genre delete status set to {} for ID: {}", isDeleted, id);
            
            return updatedBookGenre.getIsDeleted() == isDeleted; // Return true if the update was successful
        } catch (Exception e) {
            logger.error("Error updating book genre delete status for ID: {}", id, e);
            return false;
        }
    }

    
}
