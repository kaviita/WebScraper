package com.example.demo.service;

import com.example.demo.webScraper.service.HtmlParsing;
import com.example.demo.webScraper.service.JSoupConnector;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import java.io.File;
import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for JSoupConnector.
 *
 * @author kavita
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class JSoupConnectorTest {

    @Test
    public void testConnectorSuccess() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("webPage.html")).getFile());
        HtmlParsing parsing = new HtmlParsing();
        Document expectedDoc = parsing.helper(file); //wise html doc
        Connection connection = PowerMockito.mock(Connection.class);
        PowerMockito.mockStatic(Jsoup.class);
        String websiteUrl = "https://wiseappcompany.com";
        PowerMockito.when(Jsoup.connect(websiteUrl)).thenReturn(connection);
        Mockito.when(connection.get()).thenReturn(expectedDoc);
        JSoupConnector jSoupConnector = new JSoupConnector();
        Document actualDoc = jSoupConnector.getDOM(websiteUrl);
        assertEquals(expectedDoc, actualDoc);
        verify(connection).get();
    }

    @Test(expected = IllegalStateException.class)
    public void testConnectorFailure() throws Exception {
        Connection connection = PowerMockito.mock(Connection.class);
        PowerMockito.mockStatic(Jsoup.class);
        String websiteUrl = "https://wiseappcompany.c";
        PowerMockito.when(Jsoup.connect(websiteUrl)).thenReturn(connection);
        PowerMockito.when(connection.get())
                .thenThrow(new IllegalStateException(String.format("Cannot retrieve web document from URL: %s",
                        websiteUrl)));
        JSoupConnector jSoupConnector = new JSoupConnector();
        jSoupConnector.getDOM(websiteUrl);
        verify(connection).get();
    }

}
