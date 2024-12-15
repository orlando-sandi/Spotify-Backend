package org.oasumainline.SpringStarter.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class StorageMultipartFile implements MultipartFile {
    private final Path input;
    private final MediaType contentType;
    @Override
    public String getName() {
        return input.getFileName().toString();
    }

    @Override
    public String getOriginalFilename() {
        return getName();
    }

    @Override
    public String getContentType() {
        return this.contentType.toString();
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public long getSize() {
        try {
            return getBytes().length ;
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(input);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(input);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(getInputStream(), dest.toPath());
    }
}
