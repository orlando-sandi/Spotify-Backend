package org.oasumainline.SpringStarter.services;

import org.oasumainline.SpringStarter.config.StorageConfig;
import org.oasumainline.SpringStarter.exceptions.StorageException;
import org.oasumainline.SpringStarter.exceptions.StorageFileNotFoundException;
import org.oasumainline.SpringStarter.storage.StorageMultipartFile;
import org.oasumainline.SpringStarter.storage.StorageService;
import org.oasumainline.SpringStarter.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class LocalStorageService implements StorageService {

    private final Path rootDir;

    private final String[] permittedExtensions;
    @Autowired
    public LocalStorageService(StorageConfig config) {
        if(config.getRootDir().isBlank()){
            throw new StorageException("The root dir configuration cannot be empty");
        }
        this.rootDir = Paths.get(config.getRootDir());
        this.permittedExtensions = config.getPermittedExtensions();
    }
    @Override
    public void init() {
        try {
            if(!Files.isDirectory(this.rootDir)) {
                Files.createDirectory(this.rootDir);
            }
        } catch (IOException e) {
            throw new StorageException("Could not create rootDir", e);
        }

    }

    @Override
    public Path store(MultipartFile file) {
        try{
            if(file.isEmpty()) {
                throw new StorageException("The file is empty. Operation aborted");
            }
            String contentType = file.getContentType();
            if(!isValidContentType(contentType)) {
                throw new StorageException("The file has an invalid extension. Valid extensions are: " + String.join(",", permittedExtensions) + " and " + contentType + " was provided");
            }
            String extension = MediaType.parseMediaType(contentType).getSubtype();
            String filename = RandomUtils.getRandomString(10) + "." + extension;
            Path destinationFile = rootDir.resolve(filename).normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootDir.toAbsolutePath())) {
                throw new StorageException("Attempting to save file outside target dir");
            }
            try(InputStream stream = file.getInputStream()) {
                Files.copy(stream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return destinationFile;
        } catch (IOException e) {
            throw new StorageException("Could not save in destination file", e);
        }
    }

    @Override
    public Path store(String fileURL) {
        try {
            var response = RestClient.create(fileURL.trim()).head().retrieve().toBodilessEntity();
            MediaType contentType  = response.getHeaders().getContentType();
            assert contentType != null;
            if(!isValidContentType(contentType)){
                throw new StorageException("Invalid content type '" + contentType + "' for URL: " + fileURL);
            }
            URL url = new URL(fileURL);
            InputStream stream = url.openStream();
            Path tempFile = Files.createTempFile("tmp",  contentType.getSubtype());
            Files.copy(stream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            MultipartFile fileToStore = new StorageMultipartFile(tempFile, contentType);
            return store(fileToStore);
        } catch (IOException e) {
            throw new StorageException("Error decoding URL of file", e);
        }
    }
    @Override
    public Resource load(String filename) {
        Path filePath = this.rootDir.resolve(filename);
        try {
            Resource fileResource = new UrlResource(filePath.toUri());
            if(fileResource.isFile() && fileResource.isReadable()){
                return fileResource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(this.rootDir);
        } catch (IOException e) {
            throw new StorageException("Could not delete images", e);
        }
    }


    private boolean isValidContentType(MediaType contentType) {
        String extension = contentType.getSubtype();
        return Arrays.asList(permittedExtensions).contains(extension);
    }
    private boolean isValidContentType(String contentType) {
        MediaType parsedContentType = MediaType.parseMediaType(contentType);
        return isValidContentType(parsedContentType);
    }
}
