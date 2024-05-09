package org.example.ezpass.mapper;

import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.entity.Site;

public class SiteMapper {

    public static SiteDto mapToSiteDto(Site site){
        return new SiteDto(
                site.getId(),
                site.getUrl(),
                site.getLogin(),
                site.getSenha()
        );
    }

    public static Site mapToSite(SiteDto sitedto){
        return new Site(
                sitedto.getId(),
                sitedto.getUrl(),
                sitedto.getLogin(),
                sitedto.getSenha()
        );
    }
}
