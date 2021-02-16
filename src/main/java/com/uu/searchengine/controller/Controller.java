package com.uu.searchengine.controller;

import com.uu.searchengine.Spider;
import com.uu.searchengine.spellcorrection.KgramIndexing;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    KgramIndexing kgramIndexing = new KgramIndexing();
    List<String> correctedWords = new ArrayList<>();

    @GetMapping("/")
    public String main(Model model){
        return "spellCorrection";
    }

    @PostMapping("/main")
    public String processMain(HttpServletRequest request, Model model){
        correctedWords.clear();
        String phrase = request.getParameter("phrase");
        correctedWords = kgramIndexing.correctAndReturnSuggestions(phrase, 10);
        model.addAttribute("correctedWords", correctedWords);
        return "spellCorrection";
    }

    @RequestMapping(value = "/result/{phrase}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> result(@PathVariable String phrase)
    {
        correctedWords.clear();
        correctedWords = kgramIndexing.correctAndReturnSuggestions(phrase, 10);
        JSONArray jsonArray = new JSONArray();
        for (String str : correctedWords){
            jsonArray.put(str);
        }

        return ResponseEntity.ok(jsonArray.toString());
    }

    @GetMapping("/kgramprocess")
    void kgramProcess() {
        new KgramIndexing().initialize();
    }

    @GetMapping("/crawl")
    void crawl() {
        Spider.search("https://www.hamshahrionline.ir");
    }

}
