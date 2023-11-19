package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import com.tszalama.shorturlapi.service.UrlIDGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlIdGeneratorServiceImpl implements UrlIDGeneratorService {
    private static final int ID_LENGTH = 8;
    private static final int MAX_ATTEMPTS = 100;
    private static final String USABLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final Random random = new Random();
    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public UrlIdGeneratorServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    //TODO: reimplement this with a more stable solution that does not suffer from increasing number of id collisions (e.g. persisted counter or id ranges)
    @Override
    public String getUniqueUrlId() {
        String urlId;
        int attempts = 0;
        //In the case of an id collision, regenerate the id until the collision is avoided
        do {
            urlId = generateId();
            attempts++;
        } while (shortUrlRepository.existsById(urlId) && attempts < MAX_ATTEMPTS);

        if (attempts == MAX_ATTEMPTS) {
            throw new RuntimeException("Failed to generate unique URL ID after " + MAX_ATTEMPTS + " attempts.");
        }

        return urlId;
    }

    //Select random char from string and append it to the final id until we have an id of length 8 (62^8 combinations)
    private String generateId() {
        StringBuilder result = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(USABLE_CHARS.length());
            result.append(USABLE_CHARS.charAt(index));
        }

        return result.toString();
    }
}
