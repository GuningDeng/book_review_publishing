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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        Country country = Country.USA;
        Country country2 = Country.CANADA;
        Country country3 = Country.FRANCE;
        Country country4 = Country.UK;
        Long expectedCount = 2L;
        List<Author> authors = new ArrayList<>();
        Author author = new Author(){
            {
                setFirstName("John");
                setLastName("Doe");
                setCountryName(country);
            }
        };
        Author author2 = new Author(){
            {
                setFirstName("Jane");
                setLastName("Smith");
                setCountryName(country2);
            }
        };
        Author author3 = new Author(){
            {
                setFirstName("Alice");
                setLastName("Johnson");
                setCountryName(country3);
            }
        };
        Author author4 = new Author(){
            {
                setFirstName("Bob");
                setLastName("Brown");
                setCountryName(country4);
            }
        };
        Author author5 = new Author(){
            {
                setFirstName("Charlie");
                setLastName("Davis");
                setCountryName(country);
            }
        };
        authors.add(author);
        authors.add(author2);
        authors.add(author3);
        authors.add(author4);
        authors.add(author5);
        when(authorRepository.findAll()).thenReturn(authors);
        // when(authorServiceImpl.countByCountry(country.getDisplayName())).thenReturn(expectedCount);
        // Act
        Long actualCount = authorServiceImpl.countByCountry(country.getDisplayName());

        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(authorRepository, times(1)).findAll();

    }

    @Test
    void testCountByStatus() {
        // Arrange
        Byte status = 0;
        Long expectedCount = 2L;
        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setAuthorStatus(status);
        Author author2 = new Author();
        author2.setAuthorStatus(status);
        Author author3 = new Author();
        author3.setAuthorStatus((byte) 1);
        authors.add(author);
        authors.add(author2);
        authors.add(author3);
        when(authorRepository.findAll()).thenReturn(authors);
        // when(authorServiceImpl.countByStatus(status)).thenReturn(expectedCount);
        
        // Act
        Long actualCount = authorServiceImpl.countByStatus(status);

        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(authorRepository, times(1)).findAll();

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
        // Arrange
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(true);

        // Act
        Boolean exists = authorServiceImpl.existsById(authorId);

        // Assert
        assertNotNull(exists);
        assertEquals(true, exists);
        verify(authorRepository, times(1)).existsById(authorId);

    }

    @Test
    void testFindAllPage() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        String sortField = "id";
        String sortDirection = "asc";
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("John Doe");
        authors.add(author);
        Author author2 = new Author();
        author2.setId(2L);
        author2.setFirstName("Jane Smith");
        authors.add(author2);
        Author author3 = new Author();
        author3.setId(3L);
        author3.setFirstName("Alice Johnson");
        authors.add(author3);

        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());
        when(authorRepository.findAllPage(pageable)).thenReturn(authorPage);

        // Act
        Page<Author> result = authorServiceImpl.findAllPage(pageNum, pageSize, sortField, sortDirection);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(authors.size(), result.getContent().size());
        
    }

    @Test
    void testFindAllActiveAuthors() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        byte authorStatus = 0;
        byte isDeleted = 0;
        String sortField = "id";
        String sortDirection = "asc";
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("John");
        author.setAuthorStatus(authorStatus);
        author.setIsDeleted(isDeleted);
        authors.add(author);
        Author author2 = new Author();
        author2.setId(2L);
        author2.setFirstName("Jane");
        author2.setIsDeleted(isDeleted);
        author2.setAuthorStatus(authorStatus);
        authors.add(author2);
        Author author3 = new Author();
        author3.setId(3L);
        author3.setFirstName("Alice");
        author3.setAuthorStatus(authorStatus);
        author3.setIsDeleted(isDeleted);
        authors.add(author3);

        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());
        when(authorRepository.findAllActiveAuthors(pageable)).thenReturn(authorPage);

        // Act
        Page<Author> result = authorServiceImpl.findAllActiveAuthors(pageNum, pageSize, sortField, sortDirection);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(authors.size(), result.getContent().size());

    }

    @Test
    void testFindAllByConditions() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        String sortField = "id";
        String sortDirection = "asc";
        String firstName = "John";
        String lastName = "Doe";
        String countryName = "USA";
        byte authorStatus = 0;
        byte isDeleted = 0;
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(1L);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setCountryName(Country.USA);
        author.setAuthorGender((byte) 0);
        author.setAuthorStatus(authorStatus);
        author.setIsDeleted(isDeleted);
        authors.add(author);
        
        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());
        when(authorRepository.findAllByConditions("John", "Doe", countryName, (byte) 0, isDeleted, authorStatus, pageable)).thenReturn(authorPage);

        // Act
        Page<Author> result = authorServiceImpl.findAllByConditions(pageNum, pageSize, sortField, sortDirection, "John", "Doe", countryName, (byte) 0, isDeleted, authorStatus);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(authors.size(), result.getContent().size());

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
        assertEquals(expectedAuthor, actualAuthor);
        verify(authorRepository, times(1)).findById(authorId);

    }

    @Test
    void testSave() {
        // Arrange
        Author author = new Author();
        author.setFirstName("John Doe");
        author.setLastName("Smith");
        author.setAuthorGender((byte) 1);
        author.setCountryName(Country.USA);
        author.setAuthorStatus((byte) 0);
        author.setIsDeleted((byte) 0);
        when(authorRepository.save(author)).thenReturn(author);

        // Act
        Boolean result = authorServiceImpl.save(author);

        // Assert
        assertTrue(result);
        verify(authorRepository, times(1)).save(author);

    }

    @Test
    void testUpdate() {
        // Arrange
        Long authorId = 1L;
        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);
        existingAuthor.setFirstName("John");
        existingAuthor.setAuthorStatus((byte) 0);
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        
        Author updatedAuthor = new Author();
        updatedAuthor.setFirstName("John");
        existingAuthor.setAuthorStatus((byte) 1);
        when(authorServiceImpl.update(authorId, updatedAuthor)).thenReturn(true);

        // Act
        Boolean result = authorServiceImpl.update(authorId, updatedAuthor);

        // Assert
        assertTrue(result);
        verify(authorRepository, times(1)).save(existingAuthor);

    }

    @Test
    void testInactiveBatch() {
        // Arrange
        Long[] authorIds = {1L, 2L};
        
        // Create test authors
        Author author1 = new Author();
        author1.setId(1L);
        author1.setAuthorStatus((byte) 0);
        
        Author author2 = new Author();
        author2.setId(2L);
        author2.setAuthorStatus((byte) 0);
        
        // Mock repository responses
        when(authorRepository.inactiveBatch(authorIds)).thenReturn(2);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author2));
        
        // Update mock authors' status after inactivation
        author1.setAuthorStatus((byte) 1);
        author2.setAuthorStatus((byte) 1);
    
        // Act
        Boolean result = authorServiceImpl.inactiveBatch(authorIds);
    
        // Assert
        assertTrue(result);
        verify(authorRepository, times(1)).inactiveBatch(authorIds);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).findById(2L);
        assertEquals((byte) 1, author1.getAuthorStatus());
        assertEquals((byte) 1, author2.getAuthorStatus());
    }
}
