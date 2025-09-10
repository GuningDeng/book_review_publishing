package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deng.book_review_publishing.entity.BookGenre;
import com.deng.book_review_publishing.entity.enums.Genre;
import com.deng.book_review_publishing.repository.BookGenreRepository;

@SpringBootTest
public class BookGenreServiceImplTest {
    @Mock
    private BookGenreRepository bookGenreRepository;

    @InjectMocks
    private BookGenreServiceImpl bookGenreService;

    @Test
    void testCountBookGenres() {
        // This test will be implemented later
        // It should verify the count of book genres in the repository
        // For now, we can leave it empty or throw an UnsupportedOperationException
        // throw new UnsupportedOperationException("Unimplemented method 'testCountBookGenres'");
        // Arrange
        Long expectedCount = 10L; // Example expected count
        // Mock the repository method to return the expected count
        when(bookGenreRepository.count()).thenReturn(expectedCount);
        
        // Act
        Long actualCount = bookGenreService.countBookGenres();

        // Assert
        assert actualCount.equals(expectedCount) : "Expected count does not match actual count";
        assertTrue(expectedCount == actualCount, "Expected count does not match actual count");
        verify(bookGenreRepository, times(1)).count(); // Verify that the count method was called on the repository

    }

    @Test
    void testCreateBookGenre() {

    }

    @Test
    void testFindAllBookGenres() {

    }

    @Test
    void testFindBookGenreById() {
        // This test will be implemented later
        // It should verify the retrieval of a book genre by its ID from the repository
        // For now, we can leave it empty or throw an UnsupportedOperationException
        // throw new UnsupportedOperationException("Unimplemented method 'testFindBookGenreById'");
        
        // Arrange
        Long genreId = 1L; // Example genre ID
        BookGenre expectedGenre = new BookGenre(); // Example book genre object
        expectedGenre.setId(genreId); // Set the ID of the expected genre
        expectedGenre.setGenreName(Genre.BIOGRAPHY); // Set a name for the expected genre
        expectedGenre.setGenreDescription(Genre.BIOGRAPHY.getDescription()); // Set a description for the expected genre
        expectedGenre.setIsDeleted((byte) 0); // Set the deletion status for the expected genre
        // Mock the repository method to return a book genre object
        when(bookGenreRepository.save(expectedGenre)).thenReturn(expectedGenre);
        when(bookGenreRepository.findById(genreId)).thenReturn(Optional.of(expectedGenre));

        // Act
        BookGenre bookGenre = bookGenreService.findBookGenreById(genreId);

        // Assert
        assert bookGenre != null : "Book genre should not be null";
        verify(bookGenreRepository, times(1)).findById(genreId); // Verify that the findById method was called on the repository

    }

    @Test
    void testIsExistsById() {
        // This test will be implemented later
        // It should verify the existence of a book genre by its ID in the repository
        // For now, we can leave it empty or throw an UnsupportedOperationException
        // throw new UnsupportedOperationException("Unimplemented method 'testIsExistsById'");
        
        // Arrange
        Long genreId = 1L; // Example genre ID
        when(bookGenreRepository.existsById(genreId)).thenReturn(true); // Mock the repository method to return true

        // Act
        boolean exists = bookGenreService.isExistsById(genreId);

        // Assert
        assertTrue(exists, "Book genre should exist");
        verify(bookGenreRepository, times(1)).existsById(genreId); // Verify that the existsById method was called on the repository

    }

    @Test
    void testUpdateBookGenreIsDeleteStatus() {
        // This test will be implemented later
        // It should verify the update of a book genre's deletion status in the repository
        // For now, we can leave it empty or throw an UnsupportedOperationException
        // throw new UnsupportedOperationException("Unimplemented method 'testUpdateBookGenreIsDeleteStatus'");
        
        // Arrange
        Long genreId = 1L; // Example genre ID
        byte isDeleted = 1; // Example deletion status
        BookGenre existingGenre = new BookGenre(); // Example book genre object
        existingGenre.setId(genreId); // Set the ID of the existing genre
        existingGenre.setIsDeleted((byte) 0); // Set the current deletion status for the existing genre
        when(bookGenreRepository.findById(genreId)).thenReturn(Optional.of(existingGenre)); // Mock the repository method to return the existing genre

        // Act
        boolean updated = bookGenreService.updateBookGenreIsDeleteStatus(genreId, isDeleted);

        // Assert
        assertTrue(updated, "Book genre deletion status should be updated successfully");
        verify(bookGenreRepository, times(1)).findById(genreId); // Verify that the findById method was called on the repository

    }
}
