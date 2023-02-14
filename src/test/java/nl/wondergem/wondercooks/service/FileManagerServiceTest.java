package nl.wondergem.wondercooks.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.spi.DirectoryManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
class FileManagerServiceTest {

    private FileManagerService fileManagerService;

    Path basePath;
    MockMultipartFile file;

    @BeforeEach
    public void setUp() throws IOException {

        basePath = Paths.get("src/test/resources/uploads").toAbsolutePath().normalize();

        fileManagerService = new FileManagerService(basePath.toString());

        if (!Files.exists(basePath) && !Files.isDirectory(basePath)) {

            try {
                Files.createDirectory(basePath);
            } catch (IOException e) {
                throw new IOException("Issue with creating directory for test");
            }
        }

        byte[] fileContent = "file content".getBytes();
        String fileName = "file.txt";
        file = new MockMultipartFile("file", fileName, "text/plain", fileContent);

    }

    @AfterEach
    public void cleanUp() throws IOException {

        try {
            File directory = new File(basePath.toUri());
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new IOException("Issue with deleting directory for test");
        }
    }


    @Test
    @DisplayName("WhenFileIsStoredTHenTheFileNameIsReturned")
    void storeFile() throws FileAlreadyExistsException {
        //arrange
        byte[] fileContent = "file content".getBytes();
        String fileName = "file.txt";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileContent);

        //Act
        String result = fileManagerService.storeFile(file);
        System.out.println(result);

        //Assert
        assertTrue(result.contains(fileName));
        assertTrue(Files.exists(basePath.resolve(result)));

    }

    @Test
    @DisplayName("WhenFileIsDownloadedTHenAResourceIsReturnedWithTheCorrectFile")
    void downloadFile() throws IOException {
        //arrange
        String fileName = fileManagerService.storeFile(file);

        //act
        Resource fileResult = fileManagerService.downloadFile(fileName);

        //assert

        assertTrue(fileResult.isFile());
        assertTrue(fileResult.getFilename().contains("file.txt"));
        assertTrue(fileResult.isReadable());

    }

    @Test
    @DisplayName("WhenFileIsDeletedThenIsNotInTheDirectoryAnymore")
    void deleteFile() throws FileAlreadyExistsException {

        //arrange
        String fileName = fileManagerService.storeFile(file);

        //act
        boolean result = fileManagerService.deleteFile(fileName);

        //assert

        assertTrue(result);
        assertFalse(Files.exists(basePath.resolve(fileName)));


    }
}