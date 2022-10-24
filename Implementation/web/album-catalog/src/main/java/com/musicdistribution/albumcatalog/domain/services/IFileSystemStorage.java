package com.musicdistribution.albumcatalog.domain.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
    String saveFile(MultipartFile file, String fileName);
    Resource loadFile(String fileName);
}
