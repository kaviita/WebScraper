package com.example.demo.webScraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Has an implementation to connect to the given url using jsoup.
 * Returns a html document.
 * @author kavita
 */
@Component
public class JSoupConnector {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Document getDOM(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            final String error = String.format("Cannot retrieve web document from URL: %s", url);
            LOGGER.error(error);
            throw new IllegalStateException(error);
        }
        return document;
    }

}
