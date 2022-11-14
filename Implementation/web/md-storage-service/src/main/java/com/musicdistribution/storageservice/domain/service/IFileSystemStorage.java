package com.musicdistribution.storageservice.domain.service;

import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
    String saveFile(MultipartFile file, String fileName, FileLocationType fileLocationType);

    Long loadFileSize(String fileName, FileLocationType fileLocationType);

    byte[] loadFileByteRange(String filename, long start, long end, FileLocationType fileLocationType);
}
