//package nl.wondergem.wondercooks.service;
//
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.nio.file.FileAlreadyExistsException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@TestPropertySource(locations = "classpath:application-test.properties")
//class FileManagerServiceTest {
//
//    @Autowired
//    private FileManagerService fileManagerService;
//
//    Path basePath;
//
//    @BeforeEach
//    public void setUp() {
//
//        Path basePath = Paths.get("${MY_UPLOAD_LOCATION}").toAbsolutePath().normalize();
//
//    }
//
//
//    @Test
//    void storeFile() throws FileAlreadyExistsException {
//        //arrange
//        byte[] fileContent = "file content".getBytes();
//        String fileName = "file.txt";
//        MockMultipartFile file = new MockMultipartFile("file", fileName, "text/plain", fileContent);
//
//        //Act
//        String result = fileManagerService.storeFile(file);
//        System.out.println(result);
//
//        //Assert
//        assertTrue(result.contains(fileName));
//        assertTrue(Files.exists(basePath.resolve(result)));
//
//    }
//
//    @Test
//    @Disabled
//    void downloadFile() {
//    }
//}