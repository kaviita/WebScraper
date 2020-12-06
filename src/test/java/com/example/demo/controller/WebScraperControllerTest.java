package com.example.demo.controller;

import com.example.demo.webScraper.controller.WebScraperController;
import static org.junit.Assert.assertEquals;
import com.example.demo.webScraper.dto.WebPageUrlDto;
import com.example.demo.webScraper.service.WebScraperService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit test for WebScraperController.
 *
 * @author kavita
 */
public class WebScraperControllerTest {

    @Mock
    WebScraperService webScraperService;

    @InjectMocks
    WebScraperController webScraperController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testControllerSuccess() throws Exception{
        String websiteUrl = "https://wiseappcompany.com";
        WebPageUrlDto dto = new WebPageUrlDto();
        dto.setUrl(websiteUrl);
        List<String> expectedOutput = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/responseFile");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            expectedOutput.add(line);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.OK.value());
        map.put("message", "Images fetched successfully");
        map.put("list_count", expectedOutput.size());
        map.put("images", expectedOutput);
        Mockito.when(webScraperService.fetchImages(websiteUrl))
                .thenReturn(expectedOutput);
        HashMap<String, Object> actualOutput = webScraperController.fetchImages(dto);
        assertEquals(map, actualOutput);
        verify(webScraperService).fetchImages(websiteUrl);
    }

    @After
    public void verifyAfter(){
        verifyNoMoreInteractions(webScraperService);
    }

}
