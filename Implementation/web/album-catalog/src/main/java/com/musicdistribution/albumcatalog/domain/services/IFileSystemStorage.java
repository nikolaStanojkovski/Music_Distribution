package com.musicdistribution.albumcatalog.domain.services;

import com.musicdistribution.albumcatalog.domain.models.enums.FileLocationType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
    String saveFile(MultipartFile file, String fileName, FileLocationType fileLocationType);

    Long loadFileSize(String fileName, FileLocationType fileLocationType);

    byte[] loadFileByteRange(String filename, long start, long end, FileLocationType fileLocationType);
}
