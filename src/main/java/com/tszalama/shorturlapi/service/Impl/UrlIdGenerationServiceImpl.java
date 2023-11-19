package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import com.tszalama.shorturlapi.service.UrlIDGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlIdGenerationServiceImpl implements UrlIDGeneratorService {
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    public UrlIdGenerationServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public String getUniqueUrlId() {
        String urlId = generateId();
        while (shortUrlRepository.existsById(urlId)) {
            urlId = generateId();
        }
        return urlId;
    }

    private final Random random = new Random();
    private String generateId() {
        final int max = 2147483647;
        final int min = 1;
        final int randomId = random.nextInt(max - min) + min;
        return String.valueOf(randomId);
    }
}
