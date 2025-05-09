package com.deng.book_review_publishing.service;

import org.springframework.data.domain.Page;

import com.deng.book_review_publishing.entity.Author;

public interface AuthorService {
    public Boolean save(Author author);
    public Author findById(Long id);
    public Boolean update(Long id, Author author);
    public Boolean updateIsDeletedById(Long id, Byte isDeleted);
    public Boolean existsById(Long id);
    public Long count();
    public Long countByStatus(Byte status);    
    public Long countByCountry(String countryName);
    
    public Page<Author> findAllByConditions(int pageNum, int pageSize, String sortField, String sortDirection, String firstName, String lastName, String countryName, Byte gender, Byte isDeleted, Byte authorStatus);

    public Page<Author> findAllActiveAuthors(int pageNum, int pageSize, String sortField, String sortDirection);

    public Page<Author> findAll(int pageNum, int pageSize, String sortField, String sortDirection);
}


