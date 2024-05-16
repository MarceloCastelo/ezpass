package org.example.ezpass.controller;


import lombok.AllArgsConstructor;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.service.SiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/sites")
public class SiteController {

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
    @PutMapping("{id}")
    public ResponseEntity<SiteDto> updateSite(@PathVariable("id") Long siteId,
                                              @RequestBody SiteDto updatedSite) {
        SiteDto siteDto = siteService.updateSite(siteId, updatedSite);
        return ResponseEntity.ok(siteDto);
    }

    //REST API - DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSite(@PathVariable("id")Long siteId) {
        siteService.deleteSite(siteId);
        return ResponseEntity.ok("O site foi removido");
    }

    //REST API - GET BY ID
    @GetMapping("{id}")
    public ResponseEntity<SiteDto> getSiteById(@PathVariable("id") long siteId){
        SiteDto siteDto = siteService.getSiteByID(siteId);
        return ResponseEntity.ok(siteDto);
    }
}
