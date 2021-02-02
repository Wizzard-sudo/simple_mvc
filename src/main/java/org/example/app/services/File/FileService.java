package org.example.app.services.File;

import org.example.app.repository.File.FileRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {

    private final FileRepositoryImpl fileRepo;

    @Autowired
    public FileService(FileRepositoryImpl userRepo) {
        this.fileRepo = userRepo;
    }

    public ResponseEntity downloadFile(String fileName) throws FileNotFoundException {

        File file = fileRepo.downloadFile(fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(
        file.length()).contentType(MediaType.parseMediaType("application/txt")).<Object>body(resource);
    }

    public void uploadFile(MultipartFile file) throws IOException {
        fileRepo.uploadFile(file);
    }

    public ArrayList<String> getFilesName(){
                return fileRepo.getFilesName();
    }
}
