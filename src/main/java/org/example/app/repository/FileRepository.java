package org.example.app.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface FileRepository{

    File downloadFile(String filename);

    void uploadFile(MultipartFile file) throws IOException;

    ArrayList<String> getFilesName();
}
