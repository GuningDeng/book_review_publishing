package com.deng.book_review_publishing.init;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.entity.BookGenre;
import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.entity.CountryEntity;
import com.deng.book_review_publishing.entity.enums.Country;
import com.deng.book_review_publishing.entity.enums.Genre;
import com.deng.book_review_publishing.entity.enums.Language;
import com.deng.book_review_publishing.repository.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookReviewRepository bookReviewRepository;
    private final AdminRepository adminRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;
    private final CountryEntityRepository countryEntityRepository;
    
    public DataInitializer(AdminRepository adminRepository, AuthorRepository authorRepository, BookRepository bookRepository, BookGenreRepository bookGenreRepository, CountryEntityRepository countryEntityRepository, BookReviewRepository bookReviewRepository) {
        this.adminRepository = adminRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.countryEntityRepository = countryEntityRepository;
        this.bookReviewRepository = bookReviewRepository;
        
    }

    @Override
    public void run(String... args) throws Exception {
        // intialize data;
        // 
        for (int i = 0; i < 7; i++) {
            Admin admin = new Admin();
            admin.setAdminNickname("Admin Nickname " + i);
            admin.setLoginName("Admin Login Name " + i);
            adminRepository.save(admin);
            
        }
        
        for (int i = 0; i < 50; i++) {
            Author author = new Author();
            int random = (int) (Math.random() * Country.values().length);
            Random r = new Random();
            int max=6,min=1;
            int randomAdminId = r.nextInt(max - min + 1) + min; // Generates a random number between 1 and 6

            author.setFirstName("AuthorFirstName" + i);
            author.setLastName("AuthorLastName" + i);
            author.setCountryName(Country.values()[random]);
            author.setAuthorGender((byte) (Math.random() * 2));
            author.setAuthorStatus((byte) (Math.random() * 2));
            author.setIsDeleted((byte) 0);

            author.setCreateTime(new Date());
            // author.setCreatedByAdminId((long) (Math.random() * 7)); // Assuming admin IDs are from 0 to 6
            author.setCreatedByAdminId((long) randomAdminId); // Assuming admin IDs are from 1 to 6
            author.setUpdatedTime(new Date());
            authorRepository.save(author);
        }

        for (int i = 0; i < 100; i++) {
            Long genreRandom = (long) (Math.random() * Genre.values().length);
            Long genreRandom2 = (long) (Math.random() * Genre.values().length);
            Long genreRandom3 = (long) (Math.random() * Genre.values().length);
            Long authorIdRandom = (long) (Math.random() * 50);
            Long authorIdRandom2 = (long) (Math.random() * 50);
            Long authorIdRandom3 = (long) (Math.random() * 50);
            int publishYear = 1900 + (int) (Math.random() * 120);
            int randomLanguageName = (int) (Math.random() * Language.values().length);
            double randomPrice = Math.random() * 100;
            int randomStock = (int) (Math.random() * 100);
            int randomFormat = (int) (Math.random() * 5);
            Book book = new Book();
            book.setBookName("Book Name " + i);
            book.setBookISBN("Book ISBN " + i);
            book.setBookASIN("Book ASIN " + i);
            book.setBookDescription("Book Description " + i);
            book.setPublisher("Book Publisher " + i);
            book.setBookGenreIds(new Long[] { genreRandom, genreRandom2, genreRandom3 });
            book.setBookAuthorIds(new Long[] { authorIdRandom, authorIdRandom2, authorIdRandom3 });
            book.setPublisher("Book Publisher " + i);
            book.setPublishYear(publishYear);
            book.setIsPublished((byte) (Math.random() * 2));
            book.setLanguageName(Language.values()[randomLanguageName]);
            book.setBookPrice((double) Math.round(randomPrice * 100) / 100);
            book.setBookStock(randomStock);
            book.setCoverImageURL("Image URL" + i);
            book.setFormat("Book Format " + randomFormat);
            bookRepository.save(book);
        }

        for (int i = 0; i < Genre.values().length; i++) {
            BookGenre bookGenre = new BookGenre();
            // int randomGenre = (int) (Math.random() * Genre.values().length);
            bookGenre.setGenreName(Genre.values()[i]);
            bookGenre.setGenreDescription("Genre Description " + Genre.values()[i].getDescription());
            bookGenre.setIsDeleted((byte) 0);
            bookGenre.setCreateTime(new Date());
            bookGenreRepository.save(bookGenre);
        }

        for (int i = 0; i < Country.values().length; i++) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCountryName(Country.values()[i]);
            countryEntity.setCountryDescription("Country Description " + Country.values()[i].getDescription());
            countryEntity.setCountryCode(Country.values()[i].getCode());            
            countryEntity.setIsDeleted((byte) 0);
            countryEntity.setCreatedByAdminId(1L); // Assuming admin ID 1 is the creator
            countryEntity.setUpdatedByAdminId(1L); // Assuming admin ID 1 is the updater
            countryEntity.setCreateTime(new Date());
            countryEntity.setUpdatedTime(new Date());
            countryEntityRepository.save(countryEntity);
        }

        for (int i = 0; i < 100; i++) {
            BookReview bookReview = new BookReview();
            // Long bookIdRandom = (long) (Math.random() * 100);
            // int randomRating = 1 + (int) (Math.random() * 5); // Ratings between 1 and 5
            int publishYear = 1900 + (int) (Math.random() * 120);
            bookReview.setBookId((long) i+1); // Assuming book IDs start from 1
            bookReview.setReviewAuthor("AuthorFirstName" + i + " AuthorLastName" + i); // Just a placeholder
            bookReview.setReviewAuthorId((long) i+1); // Assuming author IDs start from 1
            bookReview.setReviewContent("This is a review content for book ID " + ((long) i+1) + ". The book is really interesting and engaging. I would highly recommend it to others.");
            bookReview.setReviewStatus((byte) (Math.random() * 3)); // Pending review
            bookReview.setReviewTitle("Review Title " + i); // Placeholder title 
            bookReview.setViews((int) (Math.random() * 30)); // Random views
            bookReview.setUpdatedBy((long) i+1); // Assuming admin IDs start from 1
            bookReview.setUpdatedTime(null); // No updates yet
            bookReview.setIsDeleted((byte) 0); // Not deleted
            // bookReview.setCreateTime(new Date()); // Current time
            bookReview.setCreateTime(new Date(publishYear - 1900, (int)(Math.random() * 12), (int)(Math.random() * 28) + 1)); // Random date

            bookReviewRepository.save(bookReview);
        }

    }

    
    
}
