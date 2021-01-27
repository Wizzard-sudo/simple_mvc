package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repository.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final UserFileRepository userRepo;
    private final Logger logger = Logger.getLogger(UserFileRepository.class);

    @Autowired
    public FileService(UserFileRepository userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<Object> downloadFile(String fileName) throws FileNotFoundException {

        return userRepo.downloadFile(fileName);
    }

    public ArrayList<String> getFilesName(){
                return userRepo.getFilesName();
    }
}
