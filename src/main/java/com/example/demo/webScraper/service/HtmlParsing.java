package com.example.demo.webScraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

/**
 * Acts as a helper class to parse a .html file and return a Document.
 * Used for JSoupConnectorTest class.
 * @author kavita
 */
public class HtmlParsing {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public Document helper(File file) {
        Document document;
        try {
            System.out.println("file : " + file);
            document = Jsoup.parse(file, "utf-8");
        } catch (IOException e) {
            final String error = "Cannot parse the document";
            LOGGER.error(error);
            throw new IllegalStateException(error);
        }
        return document;
    }

}
