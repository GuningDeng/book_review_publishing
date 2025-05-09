package com.deng.book_review_publishing.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.enums.Country;
import com.deng.book_review_publishing.repository.AdminRepository;
import com.deng.book_review_publishing.repository.AuthorRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final AuthorRepository authorRepository;
    
    public DataInitializer(AdminRepository adminRepository, AuthorRepository authorRepository) {
        this.adminRepository = adminRepository;
        this.authorRepository = authorRepository;
        
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

    }

    
    
}
