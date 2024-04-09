package ru.sberbank.elasticsearchplayground.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sberbank.elasticsearchplayground.services.ProductSearchService;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class SearchController {

    private final ProductSearchService productSearchService;

    @Autowired
    public SearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/search")
    public String search() {
        return "search.html";
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        log.info("fetch suggests {}", query);
        List<String> suggests = productSearchService.fetchSuggestions(query);
        log.info("suggests {}", suggests);
        return suggests;
    }
}
