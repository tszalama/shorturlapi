package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.dto.ShortUrlCreationRequestDTO;
import com.tszalama.shorturlapi.dto.ShortUrlCreationResponseDTO;
import com.tszalama.shorturlapi.model.ShortUrl;
import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import com.tszalama.shorturlapi.service.ShortUrlService;
import com.tszalama.shorturlapi.service.UrlIDGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final UrlIDGeneratorService urlIdGeneratorService;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository, UrlIDGeneratorService urlIDGeneratorService) {
        this.shortUrlRepository = shortUrlRepository;
        this.urlIdGeneratorService = urlIDGeneratorService;
    }

    //Find url based on given url id
    @Override
    public String getUrl(String urlId) {
        final ShortUrl existingShortUrl = shortUrlRepository.findById(urlId).orElseThrow(
                () -> new NoSuchElementException("Url not found") //TODO: replace with custom exception + global exception handler
        );
        return existingShortUrl.getUrl();
    }

    //Return a unique url id that can later be used to access the given url
    @Override
    public ShortUrlCreationResponseDTO shortenUrl(ShortUrlCreationRequestDTO shortUrlCreationRequestDTO) {
        if(shortUrlCreationRequestDTO == null || shortUrlCreationRequestDTO.getUrl().isBlank()) {
            throw new IllegalArgumentException("Request must contain URL"); //TODO: replace with custom exception + global exception handler
        }
        final String urlId = urlIdGeneratorService.getUniqueUrlId();
        if (urlId.isBlank()) {
            throw new IllegalArgumentException("Url id must not be blank"); //TODO: replace with custom exception + global exception handler
        }
        final ShortUrl newShortUrl = ShortUrl.builder()
                .url(shortUrlCreationRequestDTO.getUrl())
                .urlId(urlId)
                .build();

        shortUrlRepository.save(newShortUrl);

        return ShortUrlCreationResponseDTO.builder()
                .urlId(urlId)
                .build();

    }
}
