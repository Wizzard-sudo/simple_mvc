package org.example.app.repository.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private final Logger logger = Logger.getLogger(FileRepositoryImpl.class);

    @Override
    public File downloadFile(String fileName){
        logger.info("download File: " + fileName);

        fileName = "D:\\Java Project\\apache-tomcat-9.0.41\\external_uploads\\" + fileName;
        File file = new File(fileName);

        return file;
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

    public void uploadFile(MultipartFile file) throws IOException {
        logger.info("start upload :");
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists())
            dir.mkdirs();

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at:" + serverFile.getAbsolutePath());
    }
}
