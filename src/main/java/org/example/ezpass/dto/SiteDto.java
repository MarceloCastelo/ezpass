package org.example.ezpass.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteDto {
    private Long id;
    private String url;
    private String login;
    private String senha;
}
