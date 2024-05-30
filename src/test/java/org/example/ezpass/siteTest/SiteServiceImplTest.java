package org.example.ezpass.siteTest;

import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.entity.Site;
import org.example.ezpass.exception.ResourceNotFoundException;
import org.example.ezpass.repository.SiteRepository;
import org.example.ezpass.service.impl.SiteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SiteServiceImplTest {

    @Mock
    private SiteRepository siteRepository;

    @InjectMocks
    private SiteServiceImpl siteService;

    private Site site;
    private SiteDto siteDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        site = new Site();
        siteDto = new SiteDto();
    }

    @Test
    public void testCreateSite() {
        when(siteRepository.save(any(Site.class))).thenReturn(site);

        SiteDto createdSite = siteService.createSite(siteDto);

        assertEquals(siteDto.getName(), createdSite.getName());
        verify(siteRepository, times(1)).save(any(Site.class));
    }

    @Test
    public void testGetAllSites() {
        when(siteRepository.findAll()).thenReturn(Arrays.asList(site));

        List<SiteDto> sites = siteService.getAllSites();

        assertEquals(1, sites.size());
        assertEquals(siteDto.getName(), sites.get(0).getName());
        verify(siteRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateSite() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.of(site));
        when(siteRepository.save(any(Site.class))).thenReturn(site);

        SiteDto updatedSiteDto = new SiteDto();
        SiteDto updatedSite = siteService.updateSite("example", updatedSiteDto);

        assertEquals(updatedSiteDto.getUrl(), updatedSite.getUrl());
        assertEquals(updatedSiteDto.getUsername(), updatedSite.getUsername());
        assertEquals(updatedSiteDto.getPassword(), updatedSite.getPassword());
        verify(siteRepository, times(1)).findById(anyString());
        verify(siteRepository, times(1)).save(any(Site.class));
    }

    @Test
    public void testDeleteSite() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.of(site));
        doNothing().when(siteRepository).deleteById(anyString());

        siteService.deleteSite("example");

        verify(siteRepository, times(1)).findById(anyString());
        verify(siteRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void testGetSiteByName() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.of(site));

        SiteDto foundSite = siteService.getSiteByName("example");

        assertEquals(siteDto.getName(), foundSite.getName());
        verify(siteRepository, times(1)).findById(anyString());
    }

    @Test
    public void testGetSiteByNameNotFound() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> siteService.getSiteByName("nonexistent"));

        verify(siteRepository, times(1)).findById(anyString());
    }

    @Test
    public void testUpdateSiteNotFound() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.empty());

        SiteDto updatedSiteDto = new SiteDto();

        assertThrows(ResourceNotFoundException.class, () -> siteService.updateSite("nonexistent", updatedSiteDto));

        verify(siteRepository, times(1)).findById(anyString());
    }

    @Test
    public void testDeleteSiteNotFound() {
        when(siteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> siteService.deleteSite("nonexistent"));

        verify(siteRepository, times(1)).findById(anyString());
    }
}
