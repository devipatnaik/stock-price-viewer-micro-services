package com.javadev.stock.dbservice.resource;


import com.javadev.stock.dbservice.model.Quote;
import com.javadev.stock.dbservice.model.Quotes;
import com.javadev.stock.dbservice.repository.QuotesRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
@EnableJpaRepositories(basePackages = "com.javadev.stock.dbservice.repository")
public class DBServiceResource {

    private QuotesRepository quotesRepository;

    public DBServiceResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username){

        return getQuotesByUserName(username);
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){
        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quotesRepository.save(quote));
        return getQuotesByUserName(quotes.getUserName());
    }

   /* @PostMapping("/delete/{userName}")
    public List<String> delete(@PathVariable("username") final String username){

        List<Quote> quotes = quotesRepository.findByUserName(username);
        quotesRepository.delete(quotes);
        return getQuotesByUserName(username);
    }*/


    private List<String> getQuotesByUserName(@PathVariable("username") String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(quote -> {
            return quote.getQuote();
        }).collect(Collectors.toList());
    }
}
