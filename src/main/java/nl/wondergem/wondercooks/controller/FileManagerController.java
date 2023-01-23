package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.exception.UsernameNotFoundException;
import nl.wondergem.wondercooks.service.FileManagerService;
import nl.wondergem.wondercooks.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("${apiPrefix}/files")
public class FileManagerController {

    private final FileManagerService fileManagerService;

    @Autowired
    private Environment env;


    public FileManagerController(FileManagerService fileManagerService) {
        this.fileManagerService = fileManagerService;
    }

    @PostMapping("")
    public ResponseEntity<Object> storeFile(@RequestParam("file") MultipartFile file) throws FileAlreadyExistsException {

        String fileName = fileManagerService.storeFile(file);
        URI uri = Util.uriGenerator(env.getProperty("apiPrefix") + "/files/" + fileName);
        return ResponseEntity.created(uri).body("File stored");
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
        Resource resource = fileManagerService.downloadFile(fileName);

        String mediaType = FileTypeMap.getDefaultFileTypeMap().getContentType(fileName);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mediaType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);


    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Object> deleteFile(@PathVariable String fileName) {

        if (fileManagerService.deleteFile(fileName)) {
            return ResponseEntity.ok( fileName + " is deleted");
        } else {
            throw new UsernameNotFoundException("file does not exist in the system");
        }

    }
}
