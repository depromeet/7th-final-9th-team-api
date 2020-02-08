package com.depromeet.todo.domain.storage;

import org.springframework.http.MediaType;

import java.io.InputStream;

public interface StorageService {
    String save(MediaType mediaType, InputStream inputStream) throws StorageException;
}
