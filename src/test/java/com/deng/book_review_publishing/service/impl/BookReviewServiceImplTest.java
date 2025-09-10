package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.repository.BookReviewRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@SpringBootTest
public class BookReviewServiceImplTest {
    @Mock
    private BookReviewRepository bookReviewRepository;

    @InjectMocks
    private BookReviewServiceImpl bookReviewService;

    @Test
    void testCount() {
        // Arrange
        Long expectedCount = 10L;
        when(bookReviewRepository.count()).thenReturn(expectedCount);
        
        // Act
        Long actualCount = bookReviewService.count();
        
        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(bookReviewRepository, times(1)).count();

    }

    @Test
    void testModifyIsDeletedViewBatch() {
        // Arrange
        Long[] bookReviewIds = {1L, 2L, 3L};
        byte isDeleted = 1;
        when(bookReviewRepository.deleteBatch(bookReviewIds, isDeleted)).thenReturn(1);
        

        // Act
        Boolean actualValue = bookReviewService.modifyIsDeletedViewBatch(bookReviewIds, isDeleted);

        // Assert
        assertTrue(null != actualValue && actualValue);
        verify(bookReviewRepository, times(1)).deleteBatch(bookReviewIds, isDeleted);

    }

    @Test
    void testDeletedBookReviewCount() {
        // Arrange
        Long expectedCount = 5L;
        when(bookReviewRepository.countByIsDeleted((byte) 1)).thenReturn(expectedCount);
        
        // Act
        Long actualCount = bookReviewService.deletedBookReviewCount();
        
        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(bookReviewRepository, times(1)).countByIsDeleted((byte) 1);

    }

    @Test
    void testExistsById() {
        // Arrange
        Long bookReviewId = 1L;
        when(bookReviewRepository.existsById(bookReviewId)).thenReturn(true);
        
        // Act
        boolean exists = bookReviewService.existsById(bookReviewId);
        
        // Assert
        assertEquals(true, exists);
        verify(bookReviewRepository, times(1)).existsById(bookReviewId);

    }

    @Test
    void testFindBookReviewByFilters() {
        // Arrange
        int pageNum = 0;
        int pageSize = 10;
        byte authorStatus = 0;
        byte isDeleted = 0;
        String sortField = "id";
        String sortDirection = "asc";
        Sort sort = Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<BookReview> bookReviews = new ArrayList<>();
        BookReview bookReview1 = new BookReview();
        bookReview1.setId(1L);
        bookReview1.setBookId(2L);
        bookReview1.setReviewAuthor("author");
        bookReview1.setReviewAuthorId(1L);
        bookReview1.setReviewContent("content");
        bookReview1.setReviewTitle("title");
        bookReview1.setViews(111);
        bookReview1.setReviewStatus((byte) 0);
        bookReview1.setIsDeleted((byte) 0);
        
        // BookReview bookReview2 = new BookReview();
        // bookReview2.setId(2L);
        // BookReview bookReview3 = new BookReview();
        // bookReview3.setId(3L);
        bookReviews.add(bookReview1);
        // bookReviews.add(bookReview2);
        // bookReviews.add(bookReview3);

        Page<BookReview> bookReviewPage = new PageImpl<>(bookReviews, pageable, bookReviews.size());
        when(bookReviewRepository.findBookReviewByFilters(2L,"author",1L,"content","title",111,(byte) 0,(byte) 0, 2021,2,pageable)).thenReturn(bookReviewPage);

        // Act
        Page<BookReview> actualPage = bookReviewService.findBookReviewByFilters(pageNum, pageSize, sortField, sortDirection, 2L,"author", 1L, "content", "title", 111, authorStatus, isDeleted, 2021, 2);
        
        // Assert
        assertNotNull(actualPage);
        assertEquals(bookReviews.size(), actualPage.getContent().size());
        assertEquals(bookReview1.getId(), actualPage.getContent().get(0).getId());
        verify(bookReviewRepository, times(1)).findBookReviewByFilters(2L,"author",1L,"content","title",111,(byte) 0,(byte) 0, 2021,2,pageable);
        

    }

    @Test
    void testFindBookReviewById() {
        // Arrange
        Long bookReviewId = 1L;
        when(bookReviewRepository.findById(bookReviewId)).thenReturn(java.util.Optional.ofNullable(new BookReview()));
        
        // Act
        BookReview bookReview = bookReviewService.findBookReviewById(bookReviewId);
        
        // Assert
        assertNotNull(bookReview);
        verify(bookReviewRepository, times(1)).findById(bookReviewId);

    }

    @Test
    void testModifyStatusBatch() {
        // Arrange
        Long[] bookReviewIds = {1L, 2L, 3L};
        byte reviewStatus = 1;
        BookReview bookReview = new BookReview();
        bookReview.setId(1L);
        bookReview.setReviewStatus((byte) 0);
        BookReview bookReview2 = new BookReview();
        bookReview2.setId(2L);
        bookReview2.setReviewStatus((byte) 0);
        BookReview bookReview3 = new BookReview();
        bookReview3.setId(3L);
        bookReview3.setReviewStatus((byte) 0);

        // Mock the repository methods 
        when(bookReviewRepository.inactiveBatch(bookReviewIds, reviewStatus)).thenReturn(3);
        when(bookReviewRepository.findById(1L)).thenReturn(Optional.ofNullable(bookReview));
        when(bookReviewRepository.findById(2L)).thenReturn(Optional.ofNullable(bookReview2));
        when(bookReviewRepository.findById(3L)).thenReturn(Optional.ofNullable(bookReview3));
        
        // Set the expected status for each book review
        bookReview.setReviewStatus(reviewStatus);
        bookReview2.setReviewStatus(reviewStatus);
        bookReview3.setReviewStatus(reviewStatus);

        // Act
        Boolean actualValue = bookReviewService.modifyStatusBatch(bookReviewIds, reviewStatus);
        // byte expectedStatus = bookReview.getReviewStatus();
        // Assert
        // assertEquals(reviewStatus, expectedStatus);
        assertTrue(null != actualValue && actualValue);
        verify(bookReviewRepository, times(1)).inactiveBatch(bookReviewIds, reviewStatus);


    }

    @Test
    void testPublishedBookReviewCount() {
        // Arrange
        Long expectedCount = 8L;
        when(bookReviewRepository.countByReviewStatusAndIsDeleted((byte) 0, (byte) 0)).thenReturn(expectedCount);
        
        // Act
        Long actualCount = bookReviewService.publishedBookReviewCount();
        
        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(bookReviewRepository, times(1)).countByReviewStatusAndIsDeleted((byte) 0, (byte) 0);

    }

    @Test
    public void testSaveBookReview(){
        // Arrange
        BookReview bookReview = new BookReview();
        bookReview.setId(1L);
        bookReview.setBookId(2L);
        bookReview.setReviewAuthor("author");
        bookReview.setReviewAuthorId(3L);
        bookReview.setReviewContent("content");
        bookReview.setReviewTitle("title");
        
        Boolean expectedResult = false;
        when(bookReviewRepository.save(bookReview)).thenReturn(bookReview);

        // Act
        Boolean actualResult = bookReviewService.saveBookReview(bookReview);

        // Assert
        // AssertNotNull(actualResult);
        assertTrue(expectedResult != actualResult && actualResult);
        verify(bookReviewRepository, times(1)).save(bookReview);
    }

    @Test
    public void testUpdateBookReview() throws ParseException{
        // Arrange
        Long id = 1L;
        Long bookId = 2L;
        String newTitle = "New Title";
        String title = "Title";
        // Create a date object for createTime
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String sdfString = "2020-10-15 10:00:00";
        Date createTime = sdf.parse(sdfString);
      

        BookReview existingBookReview = new BookReview();
        existingBookReview.setId(id);
        existingBookReview.setBookId(bookId);
        existingBookReview.setReviewTitle(title);
        existingBookReview.setCreateTime(createTime);
        // existingBookReview.setUpdatedTime(updatedTime);

        when(bookReviewRepository.save(existingBookReview)).thenReturn(existingBookReview);
        when(bookReviewRepository.findById(id)).thenReturn(Optional.of(existingBookReview));

        // Create an updated book review object
        BookReview updatedBookReview = new BookReview();
        updatedBookReview.setId(id);
        updatedBookReview.setBookId(bookId);
        updatedBookReview.setReviewTitle(newTitle);
        updatedBookReview.setCreateTime(createTime);

        when(bookReviewService.updateBookReview(id, updatedBookReview)).thenReturn(true);


        // Act
        Boolean result = false;
        // Attempt to update a non-existing book review
        result = bookReviewService.updateBookReview(id, updatedBookReview);

        BookReview bookReview = bookReviewService.finBookReviewById(id);

        // Assert
        // assertNotNull(result);
        assertEquals(existingBookReview.getUpdatedTime(), new Date());
        assertEquals(bookReview.getReviewTitle(), newTitle);
        assertEquals(bookReview.getUpdatedTime(), existingBookReview.getUpdatedTime());
        assertEquals(createTime, bookReview.getCreateTime());
        assertEquals(existingBookReview.getCreateTime(), bookReview.getCreateTime());
        assertTrue(result);
        verify(bookReviewRepository, times(1)).save(existingBookReview);
        
    }

    @Test
    public void testFinBookReviewById() {
        // Arrange
        Long id = 1L;
        BookReview bookReview = new BookReview();
        bookReview.setId(id);
        when(bookReviewRepository.findById(id)).thenReturn(Optional.of(bookReview));

        // Act
        BookReview result = bookReviewService.finBookReviewById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(bookReview, result);
        verify(bookReviewRepository, times(1)).findById(id);
    }
}
