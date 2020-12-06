package com.example.demo.webScraper.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Has an implementation to crawl the web page and return images.
 *
 * @author kavita
 */
@Service
public class WebScraperService {

    private final JSoupConnector jSoupConnector;

    @Autowired
    public WebScraperService(JSoupConnector jSoupConnector){
        this.jSoupConnector = jSoupConnector;
    }

    public List<String> fetchImages(String websiteUrl){
        List<String> imageList = new ArrayList<>();
        Document document = jSoupConnector.getDOM(websiteUrl);
        Elements images = document.select("img");
        for(Element image : images){
            if(!image.attr("src").equals("")){
                imageList.add(image.attr("src"));
            }
        }
        return imageList;
    }

}
