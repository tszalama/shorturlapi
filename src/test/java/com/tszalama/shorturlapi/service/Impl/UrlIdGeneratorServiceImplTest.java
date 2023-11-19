package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.model.ShortUrl;
import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlIdGeneratorServiceImplTest {
    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private UrlIdGeneratorServiceImpl underTest;

    @Test
    void regenerateUrlDueToIdCollisions() {
        when(shortUrlRepository.existsById(anyString()))
                .thenReturn(true)
                .thenReturn(false);
        final String result = underTest.getUniqueUrlId();
        Assertions.assertThat(result.length()).isEqualTo(8);
        verify(shortUrlRepository, times(2)).existsById(anyString());
    }
}