package com.depromeet.todo.infrastructure.thumbnail;

import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class ThumbnailService {
    public void createThumbnail(InputStream inputStream, OutputStream outputStream) throws IOException {
        Thumbnailator.createThumbnail(inputStream, outputStream, 160, 160);
    }
}

