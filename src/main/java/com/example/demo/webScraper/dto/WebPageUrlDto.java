package com.example.demo.webScraper.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

/**
 * Basic Dto class to contain the dynamic url.
 *
 * @author kavita
 */
@Getter
@Setter
public class WebPageUrlDto {

    @NotBlank
    private String url;

}
