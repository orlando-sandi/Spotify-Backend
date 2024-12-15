package org.oasumainline.SpringStarter.storage;

import org.oasumainline.SpringStarter.exceptions.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Path;

public interface StorageService {
    void init();

    Path store(MultipartFile file) throws StorageException;
    Path store(String fileURL) throws  StorageException;
    Resource load(String filename);


    void deleteAll();
}
