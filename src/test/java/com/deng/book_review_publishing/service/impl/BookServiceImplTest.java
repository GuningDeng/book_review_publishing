package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.repository.BookRepository;

@SpringBootTest
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    
    @Test
    void testCount() {
        // Arrange
        Long expectedCount = 10L;
        when(bookRepository.count()).thenReturn(expectedCount);
        // Act
        Long actualCount = bookService.count();
        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(bookRepository, times(1)).count();

    }

    @Test
    void testExistsById() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);
        // Act
        Boolean exists = bookService.existsById(bookId);
        // Assert
        assertNotNull(exists);
        assertEquals(true, exists);
        verify(bookRepository, times(1)).existsById(bookId);

    }

    @Test
    void testFindAllBooksByIsPublishedStatus() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        String sortField = "id";
        String sortDirection = "asc";
        byte isPublished = 1;
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1L);
        book.setBookName("Book 1");
        book.setIsPublished(isPublished);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setBookName("Book 2");
        book2.setIsPublished(isPublished);
        Book book3 = new Book();
        book3.setId(3L);
        book3.setBookName("Book 3");
        book3.setIsPublished(isPublished);
        books.add(book);
        books.add(book2);
        books.add(book3);

        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAllBooksByIsPublishedStatus(isPublished, pageable)).thenReturn(bookPage);
        // Act
        Page<Book> actualBooks = bookService.findAllBooksByIsPublishedStatus(pageNum, pageSize, sortField, sortDirection, isPublished);
        // Assert
        assertNotNull(actualBooks);
        assertEquals(bookPage.getSize(), actualBooks.getSize());
        assertEquals(bookPage.getTotalElements(), actualBooks.getTotalElements());
        verify(bookRepository, times(1)).findAllBooksByIsPublishedStatus(isPublished, pageable);
        // Verify the contents of the page
        List<Book> actualContent = actualBooks.getContent();
        assertEquals(books.size(), actualContent.size());

    }

    @Test
    void testFindBookById() {
        // Arrange
        Long bookId = 1L;
        Book expectedBook = new Book();
        expectedBook.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));
        // Act
        Book actualBook = bookService.findBookById(bookId);
        // Assert
        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
        verify(bookRepository, times(1)).findById(bookId);

    }

    @Test
    void testFindBooksByFilters() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        String sortField = "id";
        String sortDirection = "asc";
        String bookName = "Book 1";
        String publisherName = "Publisher 1";
        byte isPublished = 1;
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1L);
        book.setBookName(bookName);
        book.setBookAuthorIds(new Long[]{1L, 2L});
        book.setBookGenreIds(new Long[]{1L, 3L});
        book.setPublisher(publisherName);
        book.setIsPublished(isPublished);
        books.add(book);

        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findBooksByFilters(bookName, null, null, null, null, publisherName, null,isPublished, pageable)).thenReturn(bookPage);
        // Act
        Page<Book> actualBooks = bookService.findBooksByFilters(pageNum, pageSize, sortField, sortDirection, bookName, null, null, null, null, publisherName, null,isPublished);
        // Assert
        assertNotNull(actualBooks);
        assertEquals(bookPage.getSize(), actualBooks.getSize());
        assertEquals(bookPage.getTotalElements(), actualBooks.getTotalElements());
        verify(bookRepository, times(1)).findBooksByFilters(bookName, null, null, null, null, publisherName, null,isPublished, pageable);

    }
}
