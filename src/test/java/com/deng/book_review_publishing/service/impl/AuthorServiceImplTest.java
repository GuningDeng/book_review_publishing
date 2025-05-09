package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.enums.Country;
import com.deng.book_review_publishing.repository.AuthorRepository;

@SpringBootTest
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    
    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;

    @Test
    void testCount() {
        // Arrange
        Long expectedCount = 10L;
        when(authorRepository.count()).thenReturn(expectedCount);

        // Act
        Long actualCount = authorServiceImpl.count();

        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(authorRepository, times(1)).count();

    }

    @Test
    void testCountByCountry() {
        // Arrange
        String countryName = "USA";
        Long expectedCount = 2L;
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setCountryName(Country.USA);
        Author author2 = new Author();
        author2.setCountryName(Country.USA);
        Author author3 = new Author();
        author3.setCountryName(Country.CANADA);
        Author author4 = new Author();
        author4.setCountryName(Country.UK);
        Author author5 = new Author();
        author5.setCountryName(Country.FRANCE);
        authors.add(author);
        authors.add(author2);
        authors.add(author3);
        authors.add(author4);
        authors.add(author5);

        // Mock the behavior of the repository method to return the expected count
        // when(authorRepository.saveAll(authors)).thenReturn(authors);
        // when(authorRepository.findAll()).thenReturn(authors);
        // Long count = authors.stream()
        //         .filter(a -> a.getCountryName().name().equalsIgnoreCase(countryName))
        //         .count();
        // when(authorServiceImpl.countByCountry(countryName)).thenReturn(expectedCount);

        // Act
        // Long actualCount = authorServiceImpl.countByCountry(countryName);

        // Assert
        // assertNotNull(actualCount);
        // assertEquals(actualCount, expectedCount);
        // verify(authorRepository, times(1)).findAll();

    }

    @Test
    void testCountByStatus() {

    }

    @Test
    void testUpdateIsDeletedById() {
        // Arrange
        Long authorId = 1L;
        Byte isDeleted = 1;
        Author author = new Author();
        author.setIsDeleted((byte) 0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorServiceImpl.updateIsDeletedById(authorId, isDeleted)).thenReturn(true);
        
        // Act
        Boolean result = authorServiceImpl.updateIsDeletedById(authorId, isDeleted);
        Author updatedAuthor = authorServiceImpl.findById(authorId);

        // Assert
        assertTrue(result);
        assertEquals(updatedAuthor.getIsDeleted(), isDeleted);
        
    }

    @Test
    void testExistsById() {

    }

    @Test
    void testFindAll() {
        
    }

    @Test
    void testFindAllActiveAuthors() {

    }

    @Test
    void testFindAllByConditions() {

    }

    @Test
    void testFindById() {
        // Arrange
        Long authorId = 1L;
        Author expectedAuthor = new Author();
        expectedAuthor.setId(authorId);
        expectedAuthor.setFirstName("John Doe");
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));

        // Act
        Author actualAuthor = authorServiceImpl.findById(authorId);

        // Assert
        assertNotNull(actualAuthor);
        assertEquals(expectedAuthor.getFirstName(), actualAuthor.getFirstName());
        verify(authorRepository, times(1)).findById(authorId);

    }

    @Test
    void testSave() {

    }

    @Test
    void testUpdate() {

    }
}
