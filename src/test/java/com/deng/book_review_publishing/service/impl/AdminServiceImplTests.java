package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deng.book_review_publishing.entity.Admin;
import com.deng.book_review_publishing.repository.AdminRepository;
import com.deng.book_review_publishing.service.impl.AdminServiceImpl;

@SpringBootTest
public class AdminServiceImplTests {
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminServiceImpl;

    @Test
    public void testFindById() {
        // Arrange
        Long adminId = 1L;
        Admin expectedAdmin = new Admin();
        expectedAdmin.setLoginName("Admin Login Name");
        expectedAdmin.setAdminNickname("Admin Nickname");
        
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(expectedAdmin));
        
        // Act
        Admin actualAdmin = adminServiceImpl.findById(adminId);
        
        // Assert
        assertNotNull(actualAdmin);
        assertEquals(expectedAdmin.getLoginName(), actualAdmin.getLoginName());
        assertEquals(expectedAdmin.getAdminNickname(), actualAdmin.getAdminNickname());
        verify(adminRepository, times(1)).findById(adminId);
    }

    @Test
    public void testUpdate(){
        // Arrange
        Long adminId = 1L;
        Admin expectedAdmin = new Admin();
        expectedAdmin.setLoginName("John Doe");
        expectedAdmin.setAdminNickname("JD");
        expectedAdmin.setLocked((byte) 0);
        when(adminRepository.save(expectedAdmin)).thenReturn(expectedAdmin);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(expectedAdmin));
        
        Admin updateAdmin = new Admin();
        updateAdmin.setLoginName("Jane Doe");
        updateAdmin.setAdminNickname("JD");
        updateAdmin.setLocked((byte) 1);
        when(adminServiceImpl.update(adminId, updateAdmin)).thenReturn(true);

        // Act
        boolean actualBoolean = adminServiceImpl.update(adminId, expectedAdmin);
        
        // assert
        assertTrue(actualBoolean);
        verify(adminRepository, times(1)).save(expectedAdmin);

    }

    @Test
    public void testUpdateAdminLockStatus(){
        // Arrange
        Long adminId = 1L;
        Admin expectedAdmin = new Admin();
        expectedAdmin.setLoginName("Admin Login Name");
        expectedAdmin.setAdminNickname("Admin Nickname");
        expectedAdmin.setLocked((byte) 0);
            
        when(adminRepository.updateAdminLockStatus(adminId, (byte) 1)).thenReturn(1);

        // Act
        Boolean actualBoolean = adminServiceImpl.updateAdminLockStatus(adminId, (byte) 1);
        
        // Assert
        assertTrue(actualBoolean);
        assertEquals(expectedAdmin.getLocked(), (byte) 0);
        verify(adminRepository, times(1)).updateAdminLockStatus(adminId, (byte) 1);
    }

    @Test
    public void testCount(){
        // Arrange
        Long expectedCount = 5L;
        when(adminRepository.count()).thenReturn(expectedCount);

        // Act
        Long actualCount = adminServiceImpl.count();

        // Assert
        assertNotNull(actualCount);
        assertEquals(expectedCount, actualCount);
        verify(adminRepository, times(1)).count();
    }

    @Test
    public void testExistsById() {
        // Arrange
        Long adminId = 1L;
        when(adminRepository.existsById(adminId)).thenReturn(true);

        // Act
        boolean exists = adminServiceImpl.existsById(adminId);

        // Assert
        assertTrue(exists);
        verify(adminRepository, times(1)).existsById(adminId);
    }
}
