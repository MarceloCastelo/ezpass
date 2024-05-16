package org.example.ezpass.service.impl;


import lombok.AllArgsConstructor;
import org.example.ezpass.dto.SiteDto;
import org.example.ezpass.entity.SecureKeyGenerator;
import org.example.ezpass.entity.Site;
import org.example.ezpass.exception.ResourceNotFoundException;
import org.example.ezpass.mapper.SiteMapper;
import org.example.ezpass.repository.SiteRepository;
import org.example.ezpass.service.SiteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    private SiteRepository siteRepository;
    private static final String KEY = SecureKeyGenerator.generateKey();



    // Método para criptografar a senha
    private String encrypt(String strToEncrypt) {
        try {
            assert KEY != null;
            Key secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    private String decrypt(String strToDecrypt) {
        try {
            assert KEY != null;
            Key secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }
        return null;
    }

    //Adiciona um novo site
    @Override
    public SiteDto createSite(SiteDto siteDto) {
        Site site = SiteMapper.mapToSite(siteDto);
        site.setSenha(encrypt(site.getSenha()));
        site.setLogin(encrypt(site.getLogin()));
        Site savedSite = siteRepository.save(site);
        return SiteMapper.mapToSiteDto(savedSite);
    }

    //Mostra todos os sites
    @Override
    public List<SiteDto> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream()
                .map(site -> {
                    site.setSenha(decrypt(site.getSenha()));// Descriptografar a senha antes de retornar
                    site.setLogin(decrypt(site.getLogin()));// Descriptografar o login antes de retornar
                    return SiteMapper.mapToSiteDto(site);
                })
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
    public void deleteSite(Long siteId) {
        siteRepository.findById(siteId).orElseThrow(
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
