package com.cyhee.rabit.service.file;

import com.cyhee.rabit.dao.file.FileRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.exception.file.InvalidFileException;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.file.FileInfo;
import com.cyhee.rabit.model.file.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("fileService")
public class FileService {

    private static final List<String> validExtension = Arrays.asList(
        "bmp", "rle", "dib", "jpg", "gif", "png", "tif", "tiff", "raw"
    );

    private static final String directory = System.getProperty("user.home")
        + File.separator + "rabit" + File.separator + "files";

    @Autowired
    private FileRepository repository;

    public Page<FileInfo> getFiles(Pageable pageable) {
        return repository.findByStatusIn(Arrays.asList(FileStatus.ACTIVE), pageable);
    }

    public FileInfo addFile(MultipartFile file) throws IOException, InvalidFileException {
        return addFile(file, FileService.directory);
    }

    public FileInfo addFile(MultipartFile file, String directory) throws IOException, InvalidFileException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String uuid = getUUID();
        Path path = Paths.get(directory, uuid);
        Files.copy(file.getInputStream(), path);

        String extension = getFileExtension(file.getOriginalFilename());
        if (!isValidExtension(extension)) {
            throw new InvalidFileException("Unsupported extension");
        }
        FileInfo createdFile = new FileInfo(directory, file.getSize(), uuid, file.getOriginalFilename(), extension, FileStatus.ACTIVE); 
        repository.save(createdFile);
        return createdFile;
    }

    public FileInfo getFile(long id) {
        Optional<FileInfo> file = repository.findById(id);
        if (!file.isPresent()) {
            throw new NoSuchContentException(ContentType.FILE, id);
        }
        return file.get();
    }

    public void updateFile(long id, FileInfo source) {
        FileInfo file = getFile(id);
        setFileProps(file, source);
        repository.save(file);
    }

    public void deleteFile(long id) {
        FileInfo file = getFile(id);
        file.setStatus(FileStatus.DELETED);
        repository.save(file);
    }

    private void setFileProps(FileInfo target, FileInfo source) {
        target.setSize(source.getSize());
        target.setOrgName(source.getOrgName());
        target.setExtension(source.getExtension());
    }

    private String getUUID() {
        String uuid;
        do uuid = UUID.randomUUID().toString();
        while (repository.existsName(uuid));
        return uuid;
    }

    private boolean isValidExtension(String extension) {
        return validExtension.contains(extension);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex+1);
    }
}
