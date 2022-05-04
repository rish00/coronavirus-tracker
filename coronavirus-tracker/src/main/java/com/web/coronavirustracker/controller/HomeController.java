package com.web.coronavirustracker.controller;

import java.util.List;

import com.web.coronavirustracker.models.locationStats;
import com.web.coronavirustracker.services.coronaVirusServiceData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    coronaVirusServiceData data;

    @GetMapping("/")
    public String home(Model model)
    {
        List<locationStats> allStats = data.getAllStats();
        int totalCases = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getLatestTotalCases())).sum();
        int totalDelta = allStats.stream().mapToInt(stat -> stat.getDelta()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalDelta", totalDelta);
        return "home";
    }
}
