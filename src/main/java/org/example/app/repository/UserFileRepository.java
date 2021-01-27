package org.example.app.repository;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserFileRepository implements UserFileInterface{

    private final Logger logger = Logger.getLogger(UserFileRepository.class);

    @Override
    public ResponseEntity downloadFile(String fileName) throws FileNotFoundException {
        logger.info("download File: " + fileName);

        fileName = "D:\\Java Project\\apache-tomcat-9.0.41\\external_uploads\\" + fileName;
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(
                file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);


        return responseEntity;
    }

    @Override
    public ArrayList<String> getFilesName() {

        File packagePath = new File("D:\\Java Project\\apache-tomcat-9.0.41\\external_uploads");
        File[] packageFile = packagePath.listFiles();
        ArrayList<String> listFileName = new ArrayList<>();
        for(File file : Arrays.asList(packageFile))
            listFileName.add(file.getName());

        return listFileName;
    }
}
