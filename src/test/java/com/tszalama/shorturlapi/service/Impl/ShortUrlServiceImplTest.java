package com.tszalama.shorturlapi.service.Impl;

import com.tszalama.shorturlapi.dto.ShortUrlCreationRequestDTO;
import com.tszalama.shorturlapi.dto.ShortUrlCreationResponseDTO;
import com.tszalama.shorturlapi.model.ShortUrl;
import com.tszalama.shorturlapi.repository.ShortUrlRepository;
import com.tszalama.shorturlapi.service.ShortUrlService;
import com.tszalama.shorturlapi.service.UrlIDGeneratorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceImplTest {

    @Mock
    ShortUrlRepository shortUrlRepository;
    @Mock
    UrlIDGeneratorService urlIdGeneratorService;
    @InjectMocks
    ShortUrlServiceImpl underTest;

    private ShortUrl shortUrlSample;
    private ShortUrlCreationResponseDTO shortUrlCreationResponseDTOSample;
    private ShortUrlCreationRequestDTO shortUrlCreationRequestDTOSample;

    @BeforeEach
    void setUp() {
        shortUrlSample = ShortUrl.builder()
                .url("https://www.test.com/superlongtestpath")
                .urlId("a3O9fgHL")
                .build();
        shortUrlCreationRequestDTOSample = ShortUrlCreationRequestDTO.builder()
                .url("https://www.test.com/superlongtestpath")
                .build();
        shortUrlCreationResponseDTOSample = ShortUrlCreationResponseDTO.builder()
                .urlId("a3O9fgHL")
                .build();
    }

    @Test
    void getUrlThatExists() {
        when(shortUrlRepository.findById(anyString())).thenReturn(Optional.ofNullable(shortUrlSample));
        String response = underTest.getUrl(shortUrlSample.getUrlId());
        Assertions.assertThat(response).isEqualTo(shortUrlSample.getUrl());
        verify(shortUrlRepository).findById(anyString());
    }

    @Test
    void exceptionWhenGettingNonExistantUrl() {
        when(shortUrlRepository.findById(anyString())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> underTest.getUrl(shortUrlSample.getUrlId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Url not found");
    }

    @Test
    void shortenUrl() {
        when(urlIdGeneratorService.getUniqueUrlId()).thenReturn(shortUrlSample.getUrlId());
        final ShortUrlCreationResponseDTO response = underTest.shortenUrl(shortUrlCreationRequestDTOSample);
        verify(shortUrlRepository, times(1)).save(shortUrlSample);
        Assertions.assertThat(response).isEqualTo(shortUrlCreationResponseDTOSample);
    }

    //TODO: Implement tests that trigger exceptions
}