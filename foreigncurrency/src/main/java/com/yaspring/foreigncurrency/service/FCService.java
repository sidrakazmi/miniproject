package com.yaspring.foreigncurrency.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class FCService {
    public Double changeCurrency_post(Double cost) {
        var exchange = this.doECBCall();
        var rate = exchange.get("SEK");
        var n = Double.valueOf(rate);
        var newPrice = cost * n;
        System.out.println("converted price to sek  = " + newPrice);
        return newPrice;
    }

    public double changeCostInRequiredCurrency(Double cost, String currency) {
        var exchange = this.doECBCall();
        System.out.println(exchange);
        var rate = exchange.get(currency);
        System.out.println("rate = "+rate);
        var n = Double.valueOf(rate);
        var newPrice = cost * n;
        System.out.println("converted price in desired currency = "+ newPrice);
        return newPrice;
    }

    @EventListener(ApplicationReadyEvent.class)
    public HashMap<String, String> doECBCall() {
        // Get XML from ECB
        WebClient webClient = WebClient.create("https://www.ecb.europa.eu");
        var ecbData = webClient
                .get()
                .uri("/stats/eurofxref/eurofxref-daily.xml")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        HashMap<String, String> exchangeRates = new HashMap<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new ByteArrayInputStream(ecbData.getBytes()), new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equals("Cube")) {
                        var currency = attributes.getValue("currency");
                        var rate = attributes.getValue("rate");
                        if (currency != null && rate != null) {
                            exchangeRates.put(currency, rate);
                        }
                    }
                }
            });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(exchangeRates);
        return exchangeRates;
    }
}
