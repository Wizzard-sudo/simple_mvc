package org.example.app.repository.File;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//TODO опять же дженерик тут ни к чему, наверное лучше убрать пакеты Book и File, т.к. это все таки проектные сервисы
public interface FileRepository<T> {

   File downloadFile(String filename);

   void uploadFile(MultipartFile file) throws IOException;

   ArrayList<T> getFilesName();
}
