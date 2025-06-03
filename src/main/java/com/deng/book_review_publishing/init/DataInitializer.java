package com.deng.book_review_publishing.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.entity.enums.Country;
import com.deng.book_review_publishing.entity.enums.Genre;
import com.deng.book_review_publishing.entity.enums.Language;
import com.deng.book_review_publishing.repository.AdminRepository;
import com.deng.book_review_publishing.repository.AuthorRepository;
import com.deng.book_review_publishing.repository.BookRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    
    public DataInitializer(AdminRepository adminRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.adminRepository = adminRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        
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
            author.setFirstName("Author First Name " + i);
            author.setLastName("Author Last Name " + i);
            author.setCountryName(Country.values()[random]);
            author.setAuthorGender((byte) (Math.random() * 2));
            author.setAuthorStatus((byte) (Math.random() * 2));
            author.setIsDeleted((byte) 0);
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



    }

    
    
}
