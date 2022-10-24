package com.musicdistribution.albumcatalog.domain.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileSystemStorage {
    String saveFile(MultipartFile file, String fileName);
    byte[] loadFile(String fileName);
}
