package co.jammingRules.Servizi._FileUpload.services;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileRepositoryService {

    @Value("{fileRepositoryFolder}")
    private String fileRepositoryFolder;

    /**
     *
     * @param file File from upload controller
     * @return String of new file name with extension
     */
    @SneakyThrows
    public String upload(MultipartFile file){
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "."+ extension;
        File finalFolder = new File(fileRepositoryFolder);
        if (!finalFolder.exists()) throw new IOException("'File repository folder' does not exist");
        if(!finalFolder.isDirectory()) throw new IOException("'File repository folder' is not a directory");
        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");
        file.transferTo(finalDestination);
        return completeFileName;
    }
    public void remove(String fileName) throws Exception {
        File fileFromRepository = new File(fileRepositoryFolder + File.separator + fileName);
        if (!fileFromRepository.exists()) return;
        boolean deleteResult = fileFromRepository.delete();
        if(!deleteResult) throw new Exception("Impossible to delete file");
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + File.separator + fileName);
        if (!fileFromRepository.exists()) throw new IOException("File not found");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));

    }
}
