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
        return  SiteMapper.mapToSiteDto(savedSite);
    }

    //Mostra todos os sites
    @Override
    public List<SiteDto> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream().map((site) -> SiteMapper.mapToSiteDto(site))
                .collect(Collectors.toList());
    }

    //Atualiza o site cadastrado
    @Override
    public SiteDto updateSite(Long siteId, SiteDto updatedSite) {
        Site site = siteRepository.findById(siteId).orElseThrow(
        () -> new ResourceNotFoundException("SIte não existe com o id: " + siteId)
        );

        site.setUrl(updatedSite.getUrl());
        site.setLogin(updatedSite.getLogin());
        site.setSenha(updatedSite.getSenha());

        Site updatedSiteObj = siteRepository.save(site);

        return SiteMapper.mapToSiteDto(updatedSiteObj);
    }

    //Deleta um site cadastrado
    @Override
    public void deleSite(Long siteId) {
        Site site = siteRepository.findById(siteId).orElseThrow(
                () -> new ResourceNotFoundException("Site não existe com o id: " + siteId)
        );

        siteRepository.deleteById(siteId);
    }

    @Override
    public SiteDto getSiteByID(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Site não existe com o id: " + siteId)
                );
        return SiteMapper.mapToSiteDto(site);
    }
}
