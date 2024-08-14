package com.teamD.RevTaskManagement.ProfileTest;
import com.teamD.RevTaskManagement.dao.ProfileDAO;
import com.teamD.RevTaskManagement.exceptions.ImageNotFoundException;
import com.teamD.RevTaskManagement.model.Profile;
import com.teamD.RevTaskManagement.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {
    @Mock
    private ProfileDAO profileDAO;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;
    private MultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        profile = new Profile();
        profile.setEmail("employee@example.com");
        profile.setImage(new byte[]{1, 2, 3, 4});

        file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("profileImage.jpg");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3, 4});
    }


    @Test
    void testSetProfile_IOException() throws IOException {
        when(file.getBytes()).thenThrow(new IOException("IO Error"));

        ImageNotFoundException thrown = assertThrows(
                ImageNotFoundException.class,
                () -> profileService.setProfile("employee@example.com", file),
                "Expected setProfile() to throw ImageNotFoundException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Can't save Image"));
    }

    @Test
    void testDeleteProfile_Success() {
        when(profileDAO.findByEmail(anyString())).thenReturn(profile);
        doNothing().when(profileDAO).delete(any(Profile.class));

        Profile deletedProfile = profileService.deleteProfile("employee@example.com");

        assertNotNull(deletedProfile);
        assertEquals("employee@example.com", deletedProfile.getEmail());
        verify(profileDAO).delete(any(Profile.class));
    }

    @Test
    void testDeleteProfile_NotFound() {
        when(profileDAO.findByEmail(anyString())).thenReturn(null);

        ImageNotFoundException thrown = assertThrows(
                ImageNotFoundException.class,
                () -> profileService.deleteProfile("employee@example.com"),
                "Expected deleteProfile() to throw ImageNotFoundException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Can't find image with email: employee@example.com"));
    }

    @Test
    void testFetchProfileByEmail_Success() {
        when(profileDAO.findByEmail(anyString())).thenReturn(profile);

        Profile fetchedProfile = profileService.fetchProfileByEmail("employee@example.com");

        assertNotNull(fetchedProfile);
        assertEquals("employee@example.com", fetchedProfile.getEmail());
        assertArrayEquals(new byte[]{1, 2, 3, 4}, fetchedProfile.getImage());
    }

    @Test
    void testFetchProfileByEmail_NotFound() {
        when(profileDAO.findByEmail(anyString())).thenReturn(null);

        ImageNotFoundException thrown = assertThrows(
                ImageNotFoundException.class,
                () -> profileService.fetchProfileByEmail("employee@example.com"),
                "Expected fetchProfileByEmail() to throw ImageNotFoundException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Can't find image with that email"));
    }
}
