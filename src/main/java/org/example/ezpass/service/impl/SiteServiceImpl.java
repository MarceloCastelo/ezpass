package org.example.ezpass.service.impl;


import lombok.AllArgsConstructor;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.entity.Site;
import org.example.ezpass.exception.ResourceNotFoundException;
import org.example.ezpass.mapper.SiteMapper;
import org.example.ezpass.repository.SiteRepository;
import org.example.ezpass.service.SiteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    private SiteRepository siteRepository;

    //Adiciona um novo site
    @Override
    public SiteDto createSite(SiteDto siteDto) {
        Site site = SiteMapper.mapToSite(siteDto);
        Site savedSite = siteRepository.save(site);
        return SiteMapper.mapToSiteDto(savedSite);
    }

    //Mostra todos os sites
    @Override
    public List<SiteDto> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream()
                .map(SiteMapper::mapToSiteDto)
                .collect(Collectors.toList());
    }

    //Atualiza o site cadastrado
    @Override
    public SiteDto updateSite(String siteName, SiteDto updatedSite) {
        Site site = siteRepository.findById(siteName).orElseThrow(
        () -> new ResourceNotFoundException("SIte não existe com o id: " + siteName)
        );

        site.setUrl(updatedSite.getUrl());
        site.setUsername(updatedSite.getUsername());
        site.setPassword(updatedSite.getPassword());

        Site updatedSiteObj = siteRepository.save(site);

        return SiteMapper.mapToSiteDto(updatedSiteObj);
    }

    //Deleta um site cadastrado
    @Override
    public void deleteSite(String siteName) {
        siteRepository.findById(siteName).orElseThrow(
                () -> new ResourceNotFoundException("Site não existe com o id: " + siteName)
        );

        siteRepository.deleteById(siteName);
    }


    @Override
    public SiteDto getSiteByName(String siteName) {
        Site site = siteRepository.findById(siteName)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Site não existe com o id: " + siteName)
                );
        return SiteMapper.mapToSiteDto(site);
    }



}
