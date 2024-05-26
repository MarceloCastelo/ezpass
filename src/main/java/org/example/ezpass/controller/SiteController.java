package org.example.ezpass.controller;


import lombok.AllArgsConstructor;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    //REST API - POST (CREATE)
    @PostMapping
    public ResponseEntity<SiteDto> createSite(@RequestBody SiteDto siteDto) {
        SiteDto savedSite = siteService.createSite(siteDto);
        return new ResponseEntity<>(savedSite, HttpStatus.CREATED);
    }
    //REST API - GET ALL
    @GetMapping
    public ResponseEntity<List<SiteDto>> getAllSites() {
        List<SiteDto> sites = siteService.getAllSites();
        return ResponseEntity.ok(sites);
    }

    //REST API - UPDATE
    @PutMapping("{name}")
    public ResponseEntity<SiteDto> updateSite(@PathVariable("name") String siteName,
                                              @RequestBody SiteDto updatedSite) {
        SiteDto siteDto = siteService.updateSite(siteName, updatedSite);
        return ResponseEntity.ok(siteDto);
    }

    //REST API - DELETE
    @DeleteMapping("{name}")
    public ResponseEntity<String> deleteSite(@PathVariable("name")String siteName) {
        siteService.deleteSite(siteName);
        return ResponseEntity.ok("O site foi removido");
    }

    //REST API - GET BY ID
    @GetMapping("{name}")
    public ResponseEntity<SiteDto> getSiteById(@PathVariable("name") String siteName){
        SiteDto siteDto = siteService.getSiteByName(siteName);
        return ResponseEntity.ok(siteDto);
    }


}
