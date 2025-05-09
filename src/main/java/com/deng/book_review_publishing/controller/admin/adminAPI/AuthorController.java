package com.deng.book_review_publishing.controller.admin.adminAPI;

import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.ErrorResponse;
import com.deng.book_review_publishing.service.AuthorService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/admin/api/author")
@Validated
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/allActiveAuthors")
    public ResponseEntity<?> getAllActiveAuthors(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        try {
            logger.debug("Fetching active authors with pageNum: {}, pageSize: {}, sortField: {}, sortDirection: {}",
                pageNum, pageSize, sortField, sortDirection);

            // Validate sort field
            if (!Arrays.asList("id", "firstName", "lastName", "countryName").contains(sortField)) {
                logger.warn("Invalid sort field: {}", sortField);
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid sort field", HttpStatus.BAD_REQUEST));
            }

            Page<Author> authors = authorService.findAllActiveAuthors(pageNum, pageSize, sortField, sortDirection);
            
            if (authors.isEmpty()) {
                logger.info("No active authors found");
                return ResponseEntity.ok()
                    .body(new PageResponse<>(authors, "No active authors found"));
            }

            logger.debug("Successfully retrieved {} active authors", authors.getTotalElements());
            // return ResponseEntity.ok()
            //     .body(new PageResponse<>(authors, "Active authors retrieved successfully"));
            return ResponseEntity.ok(authors);

        } catch (Exception e) {
            logger.error("Error fetching active authors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error fetching active authors", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }  
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllAuthors(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection  
    ){
        try {
            logger.debug("Fetching all authors with pageNum: {}, pageSize: {}, sortField: {}, sortDirection: {}",
                pageNum, pageSize, sortField, sortDirection);

            // Validate sort field
            if (!Arrays.asList("id", "firstName", "lastName", "countryName").contains(sortField)) {
                logger.warn("Invalid sort field: {}", sortField);
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid sort field", HttpStatus.BAD_REQUEST));
            }

            Page<Author> authors = authorService.findAll(pageNum, pageSize, sortField, sortDirection);

            if (authors.isEmpty()) {
                logger.info("No authors found");
                return ResponseEntity.ok()
                   .body(new PageResponse<>(authors, "No authors found"));
            } 
            logger.debug("Successfully retrieved {} authors", authors.getTotalElements());

            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            logger.error("Error fetching authors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ErrorResponse("Error fetching authors", HttpStatus.INTERNAL_SERVER_ERROR)); 
        }

    }

    @GetMapping("/allByConditions")
    public ResponseEntity<?> getAllAuthorsByConditions(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String countryName,
        @RequestParam(required = false) Byte gender,
        @RequestParam(required = false) Byte isDeleted,
        @RequestParam(required = false) Byte authorStatus 
    ){
        try {
            logger.debug("Fetching authors with conditions: pageNum: {}, pageSize: {}, sortField: {}, sortDirection: {}, firstName: {}, lastName: {}, countryName: {}, gender: {}, status: {}",
                pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, gender, isDeleted, authorStatus);

            // Validate sort field
            if (!Arrays.asList("id", "firstName", "lastName", "countryName").contains(sortField)) {
                logger.warn("Invalid sort field: {}", sortField);
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid sort field", HttpStatus.BAD_REQUEST));
            }

            Page<Author> authors = authorService.findAllByConditions(pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, gender, isDeleted, authorStatus); 
            if (authors.isEmpty()) {
                logger.info("No authors found with the given conditions");
                return ResponseEntity.ok()
                   .body(new PageResponse<>(authors, "No authors found with the given conditions")); 
            }
            logger.debug("Successfully retrieved {} authors", authors.getTotalElements());

            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            logger.error("Error fetching authors", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("Error fetching authors", HttpStatus.INTERNAL_SERVER_ERROR)); 
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable Long id) {
        try {
            logger.debug("Fetching author with id: {}", id);
            Author author = authorService.findById(id);
            if (author == null) {
                logger.warn("Author with id: {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Author not found", HttpStatus.NOT_FOUND));
            }
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            logger.error("Error fetching author with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error fetching author", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    

    @PutMapping("/{id}/isDeleted")
    public ResponseEntity<?> updateIsDeletedById(@PathVariable Long id, @RequestParam(defaultValue = "0") Byte isDeleted) {
        try {
            logger.debug("Updating isDeleted status for author with id: {} to {}", id, isDeleted);
            Author author = authorService.findById(Long.valueOf(id));
            if (author == null) {
                logger.warn("Author with id: {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Author not found", HttpStatus.NOT_FOUND));
            }
            if (isDeleted != 0 && isDeleted != 1) {
                logger.warn("Invalid isDeleted status provided: {}", isDeleted);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("isDeleted status is invalid", HttpStatus.BAD_REQUEST));
                
            }
            if (author.getIsDeleted() == isDeleted) {
                logger.info("Author with id: {} already has isDeleted status of {}", id, isDeleted);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Author already has the specified isDeleted status " + isDeleted, HttpStatus.BAD_REQUEST));
                
            }
            // author.setIsDeleted(isDeleted);
            authorService.updateIsDeletedById(id, isDeleted);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            logger.error("Error updating author with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error updating author", HttpStatus.INTERNAL_SERVER_ERROR));
        }
        
    }
}



class PageResponse<T> {
    private final Page<T> data;
    private final String message;

    public PageResponse(Page<T> data, String message) {
        this.data = data;
        this.message = message;
    }

    public Page<T> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
