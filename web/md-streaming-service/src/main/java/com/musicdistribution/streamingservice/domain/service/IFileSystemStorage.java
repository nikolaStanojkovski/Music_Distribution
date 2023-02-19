package com.musicdistribution.streamingservice.domain.service;

import com.musicdistribution.streamingservice.domain.model.enums.FileLocationType;
import org.springframework.web.multipart.MultipartFile;

/**
 * A domain service which is used for file storage manipulation.
 */
public interface IFileSystemStorage {

    /**
     * Method used to save a multipart file.
     *
     * @param file             - the file to be saved.
     * @param fileName         - the name of the file which is to be saved.
     * @param fileLocationType - the type of the location in which the file is to be stored.
     */
    void saveFile(MultipartFile file, String fileName, FileLocationType fileLocationType);

    /**
     * Method used for reading the file size in bytes.
     *
     * @param fileName         - the name of the file whose size is to be read.
     * @param fileLocationType - the type of the location in which the file is stored.
     * @return the size of the specified file in bytes.
     */
    Long loadFileSize(String fileName, FileLocationType fileLocationType);

    /**
     * Method used to fetch the bytes from the file in the specified byte range.
     *
     * @param filename         - the name of the file whose bytes are to be read from.
     * @param start            - the starting index of the range of the required bytes.
     * @param end              - the ending index of the range of the required bytes.
     * @param fileLocationType - the type of the location in which the file is stored.
     * @return the bytes of the file in the specified range.
     */
    byte[] loadFileByteRange(String filename, long start, long end, FileLocationType fileLocationType);
}
