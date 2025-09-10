package com.deng.book_review_publishing.service;

import java.util.List;

import com.deng.book_review_publishing.entity.BookGenre;

public interface BookGenreService {
    // Define methods for managing book genres, such as:
    // - Create a new book genre
    // - Update an existing book genre
    // - Delete a book genre
    // - Find a book genre by ID
    // - List all book genres
    // - Search for book genres by name or description

    // Example method signatures:
    // BookGenre createBookGenre(BookGenre bookGenre);
    // BookGenre updateBookGenre(Long id, BookGenre bookGenre);
    // void deleteBookGenre(Long id);
    public BookGenre findBookGenreById(Long id);
    public List<BookGenre> findAllBookGenres();
    public BookGenre createBookGenre(BookGenre bookGenre);
    public boolean isExistsById(Long id);
    public Long countBookGenres();
    public boolean updateBookGenreIsDeleteStatus(Long id, byte isDeleted);
}
