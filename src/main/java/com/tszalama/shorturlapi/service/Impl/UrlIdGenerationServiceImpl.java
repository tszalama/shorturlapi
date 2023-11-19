package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import com.tszalama.shorturlapi.service.UrlIDGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlIdGenerationServiceImpl implements UrlIDGeneratorService {
    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public UrlIdGenerationServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public String getUniqueUrlId() {
        String urlId = generateId();
        //In the case of an id collision, regenerate the id until the collision is avoided
        while (shortUrlRepository.existsById(urlId)) {
            urlId = generateId();
        }
        return urlId;
    }

    private final Random random = new Random();
    private final String USABLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private String generateId() {
        StringBuilder result = new StringBuilder();
        //Select random char from string and append it to the final id until we have an id of length 8 (62^8 combinations)
        int limit = 8;
        for (int i = 0; i < limit; i++) {
            int index = random.nextInt(USABLE_CHARS.length());
            result.append(USABLE_CHARS.charAt(index));
        }

        return result.toString();
    }
}
