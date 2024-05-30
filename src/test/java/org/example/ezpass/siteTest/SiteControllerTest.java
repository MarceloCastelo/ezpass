package org.example.ezpass.siteTest;

import org.example.ezpass.controller.SiteController;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.service.SiteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SiteControllerTest {

    @Mock
    private SiteService siteService;

    @InjectMocks
    private SiteController siteController;

    @Test
    public void testCreateSite() {
        SiteDto siteDto = new SiteDto();
        SiteDto savedSiteDto = new SiteDto();
        when(siteService.createSite(any(SiteDto.class))).thenReturn(savedSiteDto);

        ResponseEntity<SiteDto> response = siteController.createSite(siteDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(savedSiteDto);
    }

    @Test
    public void testGetAllSites() {
        SiteDto siteDto1 = new SiteDto();
        SiteDto siteDto2 = new SiteDto();
        List<SiteDto> siteList = Arrays.asList(siteDto1, siteDto2);
        when(siteService.getAllSites()).thenReturn(siteList);

        ResponseEntity<List<SiteDto>> response = siteController.getAllSites();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(siteList);
    }

    @Test
    public void testUpdateSite() {
        SiteDto updatedSiteDto = new SiteDto();
        when(siteService.updateSite(anyString(), any(SiteDto.class))).thenReturn(updatedSiteDto);

        ResponseEntity<SiteDto> response = siteController.updateSite("siteName", updatedSiteDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedSiteDto);
    }

    @Test
    public void testDeleteSite() {
        ResponseEntity<String> response = siteController.deleteSite("siteName");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("O site foi removido");
    }

    @Test
    public void testGetSiteById() {
        SiteDto siteDto = new SiteDto();
        when(siteService.getSiteByName(anyString())).thenReturn(siteDto);

        ResponseEntity<SiteDto> response = siteController.getSiteById("siteName");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(siteDto);
    }
}
