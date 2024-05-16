package org.example.ezpass;

import org.example.ezpass.controller.SiteController;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.service.SiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SiteControllerTest {

    @Mock
    private SiteService siteService;

    @InjectMocks
    private SiteController siteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateSite() {
        SiteDto siteDto = new SiteDto();
        when(siteService.createSite(any(SiteDto.class))).thenReturn(siteDto);

        ResponseEntity<SiteDto> responseEntity = siteController.createSite(siteDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(siteDto, responseEntity.getBody());
    }

    @Test
    void testGetAllSites() {
        List<SiteDto> siteDtoList = new ArrayList<>();
        when(siteService.getAllSites()).thenReturn(siteDtoList);

        ResponseEntity<List<SiteDto>> responseEntity = siteController.getAllSites();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(siteDtoList, responseEntity.getBody());
    }

    @Test
    void testUpdateSite() {
        long siteId = 1L;
        SiteDto updatedSite = new SiteDto();
        when(siteService.updateSite(eq(siteId), any(SiteDto.class))).thenReturn(updatedSite);

        ResponseEntity<SiteDto> responseEntity = siteController.updateSite(siteId, updatedSite);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedSite, responseEntity.getBody());
    }

    @Test
    void testDeleteSite() {
        long siteId = 1L;
        ResponseEntity<String> responseEntity = siteController.deleteSite(siteId);

        verify(siteService, times(1)).deleSite(siteId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("O site foi removido", responseEntity.getBody());
    }

    @Test
    void testGetSiteById() {
        long siteId = 1L;
        SiteDto siteDto = new SiteDto();
        when(siteService.getSiteByID(siteId)).thenReturn(siteDto);

        ResponseEntity<SiteDto> responseEntity = siteController.getSiteById(siteId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(siteDto, responseEntity.getBody());
    }
}
