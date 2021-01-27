package org.example.app.repository;

import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface UserFileInterface<T> {

    ResponseEntity<Object> downloadFile(String filename) throws FileNotFoundException;

    ArrayList<T> getFilesName();
}
