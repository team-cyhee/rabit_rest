package com.cyhee.rabit.service.file;

import com.cyhee.rabit.exception.file.InvalidFileException;
import com.cyhee.rabit.model.file.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    Page<FileInfo> getFiles(Pageable pageable);

    void addFile(MultipartFile file, String directory) throws IOException, InvalidFileException;

    void addFile(MultipartFile file) throws IOException, InvalidFileException;

    FileInfo getFile(long id);

    void updateFile(long id, FileInfo file);

    void deleteFile(long id);
}
