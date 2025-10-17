package com.deng.book_review_publishing.controller.admin.adminAPI;

import java.util.Arrays;
import java.util.Date;

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
import com.deng.book_review_publishing.utils.ValidateUtil;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/admin/api/author")
@Validated
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Get all active authors with pagination and sorting
     * @param pageNum
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @return ResponseEntity<?> with a page of active authors or an error response
     */
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
            // Validate pageNum and pageSize
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            // Validate pageSize
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            // Validate sort field
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "firstName", "countryName"}, "id");
            // Validate sort direction
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);

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

    /**
     * Get all authors with pagination and sorting
     * @param pageNum
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @return ResponseEntity<?> with a page of authors or an error response
     */
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
            // Validate pageNum and pageSize
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            // Validate pageSize
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            // Validate sort field
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "firstName", "countryName"}, "id");
            // Validate sort direction
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);

            Page<Author> authors = authorService.findAllPage(pageNum, pageSize, sortField, sortDirection);

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
    
    /**
     * Get all authors with pagination and sorting by conditions
     * @param pageNum
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @param firstName
     * @param lastName
     * @param countryName
     * @param gender
     * @param isDeleted
     * @param authorStatus
     * @return ResponseEntity<?> with a page of authors or an error response
     */
    @GetMapping("/allByConditions")
    public ResponseEntity<?> getAllAuthorsByConditions(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String countryName,
        @RequestParam(required = false, defaultValue = "0") Byte authorGender,
        @RequestParam(required = false, defaultValue = "0") Byte isDeleted,
        @RequestParam(required = false, defaultValue = "0") Byte authorStatus 
    ){
        try {
            logger.debug("Fetching authors with conditions: pageNum: {}, pageSize: {}, sortField: {}, sortDirection: {}, firstName: {}, lastName: {}, countryName: {}, gender: {}, status: {}",
                pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, authorGender, isDeleted, authorStatus);
            // Validate pageNum and pageSize
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            // Validate pageSize
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            // Validate sort field
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "firstName", "countryName"}, "id");
            // Validate sort direction
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            System.out.println("lastName: " + lastName);

            Page<Author> authors = authorService.findAllByConditions(pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, authorGender, isDeleted, authorStatus); 
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
    
    /**
     * Get author by id
     * @param id the id of the author to get
     * @return ResponseEntity<?> with the author or an error response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            logger.debug("Fetching author with id: {}", id);
            if (id == null ) {
                logger.warn("Invalid author id provided");
                return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid author id", HttpStatus.BAD_REQUEST));    
            }
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

    /**
     * Update the isDeleted status of an author by ID.
     *
     * @param id        The ID of the author to update.
     * @param isDeleted The new isDeleted status (0 or 1).
     * @return ResponseEntity with the updated author or an error message.
     */
    @PutMapping("/{id}/updatedIsDeleted")
    public ResponseEntity<?> updateIsDeletedById(@PathVariable Long id, @RequestParam(defaultValue = "0") Byte isDeleted) {
        try {
            logger.debug("Updating isDeleted status for author with id: {} to {}", id, isDeleted);
            if (id <= 0) {
                logger.warn("Invalid author id provided");
                return ResponseEntity.badRequest()
                  .body(new ErrorResponse("Invalid author id", HttpStatus.BAD_REQUEST));    
            }
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
    
    /**
     * Inactivate multiple authors by their IDs.
     *
     * @param ids The array of author IDs to inactivate.
     * @return ResponseEntity with a success message or an error message.
     */
    @PutMapping("/batch/inactive")
    public ResponseEntity<?> inactiveBatchByIds(@RequestBody Long[] ids) {
        try {
            logger.debug("Inactivating authors with ids: {}", Arrays.toString(ids));
            if (ids == null || ids.length == 0) {
                logger.warn("Invalid author ids provided");
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid author ids", HttpStatus.BAD_REQUEST));
            }

            Boolean result = authorService.inactiveBatch(ids);
            if (result) {
                logger.info("Authors with ids: {} have been inactivated", Arrays.toString(ids));
                return ResponseEntity.ok()
                    .body(new ErrorResponse("Authors have been successfully inactivated", HttpStatus.OK));
            } else {
                logger.warn("Failed to inactivate some or all authors with ids: {}", Arrays.toString(ids));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to inactivate some or all authors", HttpStatus.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e) {
            logger.error("Error inactivating authors with ids: {}", Arrays.toString(ids), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error inactivating authors", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    
    /**
     * Add a new author.
     *
     * @param author The author object to add.
     * @return ResponseEntity with the added author or an error message.
     */
    @PostMapping("/addAuthor")
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        try {
            logger.debug("Adding new author: {}", author);
            if (author == null) {
                logger.warn("Invalid author data provided");
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid author data", HttpStatus.BAD_REQUEST));
            }
            Boolean isSaved = authorService.save(author);
            if (!isSaved) {
                logger.warn("Failed to save author: {}", author);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to save author", HttpStatus.INTERNAL_SERVER_ERROR));
            }
            logger.info("Author added successfully {}", author);
            return ResponseEntity.status(HttpStatus.CREATED).body(author);
        } catch (Exception e) {
            logger.error("Error adding new author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error adding new author", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    
    /**
     * Update an existing author.
     *
     * @param id      The ID of the author to update.
     * @param author  The author object with updated data.
     * @return ResponseEntity with the updated author or an error message.
     */
    @PutMapping("/updateAuthor/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        try {
            logger.debug("Updating author with id: {} with data: {}", id, author);
            if (id == null || author == null) {
                logger.warn("Invalid author id or data provided");
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid author id or data", HttpStatus.BAD_REQUEST));
            }
            System.out.println("author.getUpdatedTime: " + author.getUpdatedTime());
            Author existingAuthor = authorService.findById(id);
            if (existingAuthor == null) {
                logger.warn("Author with id: {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Author not found", HttpStatus.NOT_FOUND));
            }
            // Update the existing author's fields
            existingAuthor.setFirstName(author.getFirstName());
            existingAuthor.setLastName(author.getLastName());
            existingAuthor.setCountryName(author.getCountryName());
            existingAuthor.setAuthorGender(author.getAuthorGender());
            existingAuthor.setAuthorStatus(author.getAuthorStatus());
            existingAuthor.setIsDeleted(author.getIsDeleted());
            existingAuthor.setCreatedByAdminId(author.getCreatedByAdminId());
            existingAuthor.setUpdatedTime(new Date()); // Set to update to current time
                        
            // Save the updated author
            Boolean isUpdated = authorService.save(existingAuthor);
            
            if (!isUpdated) {
                logger.warn("Failed to update author with id: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to update author", HttpStatus.INTERNAL_SERVER_ERROR));
            }
            Author updatedAuthor = authorService.findById(id);
            if (updatedAuthor == null) {
                logger.warn("Updated author with id: {} not found after update", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Updated author not found", HttpStatus.NOT_FOUND));
            }
            logger.info("Author with id: {} updated successfully", id);
            return ResponseEntity.ok(updatedAuthor);
        } catch (Exception e) {
            logger.error("Error updating author with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error updating author", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }    
    
}


/**
 * PageResponse class to wrap paginated responses.
 *
 * @param <T> the type of elements in the page
 */
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
