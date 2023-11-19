package com.tszalama.shorturlapi.service;

import com.tszalama.shorturlapi.dto.ShortUrlCreationRequestDTO;
import com.tszalama.shorturlapi.dto.ShortUrlCreationResponseDTO;

public interface ShortUrlService {
    String getUrl(String urlId);
    ShortUrlCreationResponseDTO shortenUrl(ShortUrlCreationRequestDTO shortUrlCreationRequestDTO);
}
