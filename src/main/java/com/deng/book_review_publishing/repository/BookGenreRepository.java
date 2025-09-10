package com.deng.book_review_publishing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deng.book_review_publishing.entity.BookGenre;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    
    // Additional query methods can be defined here if needed
    
}
