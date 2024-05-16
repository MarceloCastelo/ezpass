package org.example.ezpass.service;

import org.example.ezpass.dto.SiteDto;


import java.util.List;

public interface SiteService {

    SiteDto createSite(SiteDto siteDto);

    List<SiteDto> getAllSites();

    SiteDto updateSite(Long siteId, SiteDto updatedSite);

    void deleteSite(Long siteId);

    SiteDto getSiteByID(Long siteId);

}
