package com.example.demo.webScraper.controller;

import com.example.demo.base.Urls;
import com.example.demo.webScraper.dto.WebPageUrlDto;
import com.example.demo.webScraper.service.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Controls the data flow by accepting in the url as a request body.
 * Returns a hashmap.
 * @author kavita
 */
@RestController
@RequestMapping(Urls.BASE_PATH)
public class WebScraperController {

    @Autowired
    WebScraperService scraperService;

    @PostMapping(Urls.FETCH_IMAGES)
    public HashMap<String, Object> fetchImages(@Valid @RequestBody WebPageUrlDto dto) {
        List<String> images = scraperService.fetchImages(dto.getUrl());
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.OK.value());
        map.put("message", "Images fetched successfully");
        map.put("list_count", images.size());
        map.put("images", images);
        return map;
    }

}
