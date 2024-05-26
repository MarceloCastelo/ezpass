package org.example.ezpass.mapper;

import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.entity.Site;

public class SiteMapper {

    public static SiteDto mapToSiteDto(Site site){
        return new SiteDto(
                site.getName(),
                site.getUrl(),
                site.getUsername(),
                site.getPassword(),
                site.getNote()
        );
    }

    public static Site mapToSite(SiteDto sitedto){
        return new Site(
                sitedto.getName(),
                sitedto.getUrl(),
                sitedto.getUsername(),
                sitedto.getPassword(),
                sitedto.getNote()
        );
    }
}
