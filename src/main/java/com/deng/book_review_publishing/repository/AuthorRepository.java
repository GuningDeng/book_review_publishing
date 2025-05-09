package com.deng.book_review_publishing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deng.book_review_publishing.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    /**
     * Find all authors matching the given criteria with pagination support.
     * The search is case-insensitive for text fields and supports partial matches for firstName and countryName.
     *
     * @param gender       Author's gender (null for any)
     * @param firstName   First name to search for (partial, case-insensitive match)
     * @param lastName    Last name to match (exact match)
     * @param countryName Country name to search for (partial, case-insensitive match)
     * @param authorStatus Author's status (0 for active)
     * @param pageable    Pagination information
     * @return Page of matching Author entities
     */
    @Query(value = "SELECT DISTINCT a FROM Author a WHERE " +
           "(:gender IS NULL OR a.authorGender = :gender) AND " +
           "(:firstName IS NULL OR a.firstName LIKE CONCAT('%', UPPER(:firstName), '%')) AND " +
           "(:lastName IS NULL OR a.lastName = :lastName) AND " +
           "(:isDeleted IS NULL OR a.isDeleted = :isDeleted) AND " +
           "(:authorStatus IS NULL OR a.authorStatus = :authorStatus) AND " +
           "(:countryName IS NULL OR a.countryName LIKE CONCAT('%', UPPER(:countryName), '%'))")
    public Page<Author> findAllByConditions(
        @Param("firstName") String firstName,
        @Param("lastName") String lastName,
        @Param("countryName") String countryName,
        @Param("gender") Byte gender,
        @Param("isDeleted") Byte isDeleted,
        @Param("authorStatus") Byte authorStatus,
        Pageable pageable
    );

    /**
     * Find all authors with pagination support.
     * The search is case-insensitive for text fields and supports partial matches for firstName and countryName.
     *
     * @param pageable Pagination information
     * @return Page of all Author entities
     */
    @Query("SELECT DISTINCT a FROM Author a WHERE a.isDeleted = 0 AND a.authorStatus = 0")
    public Page<Author> findAllActiveAuthors(Pageable pageable);
    
    /**
     * Find all authors with pagination support.
     * The search is case-insensitive for text fields and supports partial matches for firstName and countryName.
     *
     * @param pageable Pagination information
     * @return Page of all Author entities
     */
    @Query(value = "SELECT DISTINCT a FROM Author a")
    public Page<Author> findAllPage(Pageable pageable);
}
