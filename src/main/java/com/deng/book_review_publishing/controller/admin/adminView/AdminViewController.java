package com.deng.book_review_publishing.controller.admin.adminView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deng.book_review_publishing.service.AdminService;
import com.deng.book_review_publishing.service.AuthorService;

@Controller
@RequestMapping("/admin/view")
public class AdminViewController {
    private static final Logger logger = LoggerFactory.getLogger(AdminViewController.class);
    private final AdminService adminService;
    private final AuthorService authorService;

    public AdminViewController(AdminService adminService, AuthorService authorService) {
        this.adminService = adminService;
        this.authorService = authorService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        logger.debug("Rendering admin index page");
        Long authorCount = authorService.countByStatus((byte) 0);
        model.addAttribute("authorCount", authorCount);
        return "admin/dashboard";
    }
    
}
