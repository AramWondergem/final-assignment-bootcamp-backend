package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

@Service
public class FileManagerService {

    @Value("${MY_UPLOAD_LOCATION}")
    private Path fileStoragePath;

    private final String fileStorageLocation;

    public FileManagerService(@Value("${MY_UPLOAD_LOCATION}") String fileStorageLocation) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        this.fileStorageLocation = fileStorageLocation;

        if(!Files.exists(fileStoragePath) && !Files.isDirectory(fileStoragePath)) {

            try {
                Files.createDirectories(fileStoragePath);
            } catch (IOException exception) {
                throw new RuntimeException("Issue in creating file directory");
            }
        }

    }

    public String storeFile(MultipartFile file) throws FileAlreadyExistsException {



        String fileNameAddition = StringUtils.cleanPath(Objects.requireNonNull(String.valueOf(Date.from(Instant.now()).getTime()))); // to prevent that files can have the same name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + File.separator + fileNameAddition +  fileName);

        try{
            Files.copy(file.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            if( e instanceof FileAlreadyExistsException) {
                throw new FileAlreadyExistsException("Adapt the file name of the file to save it");
            } else {
                throw new RuntimeException("Issue in storing the file",e);
            }

        }

        return fileNameAddition + fileName;
    }


    public Resource downloadFile(String fileName){

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try{
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file",e);
        }

        if(resource.exists()&& resource.isReadable()){
            return resource;
        } else {
            throw new UsernameNotFoundException("the file doesn't exist or is not readable");
        }


    }

    public boolean deleteFile(String fileName) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        try{
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("A problem occurred with deleting: " + fileName);
        }

    }


}
