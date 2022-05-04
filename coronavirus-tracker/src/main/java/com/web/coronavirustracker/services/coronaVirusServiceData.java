package com.web.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.web.coronavirustracker.models.locationStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class coronaVirusServiceData {

    private String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<locationStats> allStats = new ArrayList<>();

    public List<locationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getVirusData() throws IOException, InterruptedException {
        List<locationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println("Total" + httpResponse.body());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvBodyReader);
        int c=0;
        for (CSVRecord record : records) {
            if(c==0)
            {
                c=1;
                continue;
            }
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            int delta =  latestCases - prevDayCases;
            locationStats locationStat = new locationStats();
            locationStat.setState(record.get(0));
            locationStat.setCountry(record.get(1));
            locationStat.setLatestTotalCases(Integer.toString(latestCases));
            locationStat.setDelta(delta);
            // String customerNo = record.get("CustomerNo");
            // String name = record.get("Name");
            // System.out.println(locationStat);
            newStats.add(locationStat);
        }
        this.allStats=newStats;
    }
}
