package com.example.demo.service;

import com.example.demo.webScraper.service.JSoupConnector;
import com.example.demo.webScraper.service.WebScraperService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for WebScraperService.
 *
 * @author kavita
 */
public class WebScraperServiceTest {

    @Mock
    private JSoupConnector jSoupConnector;

    @InjectMocks
    private WebScraperService webScraperService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testServiceSuccess() throws Exception {
        String websiteUrl = "https://wiseappcompany.com";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("webPage.html")).getFile());
        Document htmlPage = Jsoup.parse(file, "utf-8");
        Mockito.when(jSoupConnector.getDOM(websiteUrl)).thenReturn(htmlPage);
        List<String> expectedOutput = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/responseFile");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            expectedOutput.add(line);
        }
        List<String> actualOutput = webScraperService.fetchImages(websiteUrl);
        assertEquals(expectedOutput.size(), actualOutput.size());
        verify(jSoupConnector).getDOM(websiteUrl);
    }

    @Test(expected = IllegalStateException.class)
    public void testServiceFailure() {
        String websiteUrl = "https://wiseappcompany.c";
        Mockito.when(jSoupConnector.getDOM(websiteUrl))
                .thenThrow(new IllegalStateException(String.format("Cannot retrieve web document from URL: %s",
                        websiteUrl)));
        webScraperService.fetchImages(websiteUrl);
        verify(jSoupConnector).getDOM(websiteUrl);
    }

}
