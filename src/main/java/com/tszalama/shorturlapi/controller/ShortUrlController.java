package com.tszalama.shorturlapi.controller;

import com.tszalama.shorturlapi.dto.ShortUrlCreationRequestDTO;
import com.tszalama.shorturlapi.dto.ShortUrlCreationResponseDTO;
import com.tszalama.shorturlapi.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<Object> getUrl(@PathVariable("urlId") String urlId) {
        try {
            final URI uri = new URI(shortUrlService.getUrl(urlId));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //TODO: replace with global exception handler
        }
    }

    @PostMapping
    public ResponseEntity<ShortUrlCreationResponseDTO> shortenUrl(@RequestBody ShortUrlCreationRequestDTO shortUrlCreationRequestDTO) {
        return new ResponseEntity<>(shortUrlService.shortenUrl(shortUrlCreationRequestDTO), HttpStatus.CREATED);
    }
}
