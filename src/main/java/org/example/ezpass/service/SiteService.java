package org.example.ezpass.service;

import org.example.ezpass.dto.SiteDto;


import java.util.List;

public interface SiteService {

    SiteDto createSite(SiteDto siteDto);

    List<SiteDto> getAllSites();


    //Atualiza o site cadastrado
    SiteDto updateSite(String siteName, SiteDto updatedSite);

    //Deleta um site cadastrado
    void deleteSite(String siteName);

    SiteDto getSiteByName(String siteName);
}
