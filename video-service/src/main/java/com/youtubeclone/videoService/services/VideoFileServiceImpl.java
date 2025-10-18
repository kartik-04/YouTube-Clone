package com.youtubeclone.videoService.services;

import com.youtubeclone.Repositories.video.FileRepository;
import com.youtubeclone.Repositories.video.MetadataRepository;
import com.youtubeclone.exceptions.StorageException;
import com.youtubeclone.services.Interfaces.video.VideoFileService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

/** We are working on the actual binary date for the video.
 * we are injecting the object of FileRepository to perform the CRUD action on the data
 * Also injecting the metadata object cause there is requirement of videoId
 */
public class VideoFileServiceImpl implements VideoFileService {
    private final FileRepository fileRepository;
    private final MetadataRepository metadataRepository;


    public VideoFileServiceImpl(FileRepository repository,  MetadataRepository metadataRepository) {
        this.fileRepository = repository;
        this.metadataRepository = metadataRepository;
    }

    /** Upload the actual video file over the in memory repository
     *
     * @param videoId is making sure to track the video we are talking about
     * @param fileData  acual binary object real video
     */
    @Override
    public void uploadVideo(UUID videoId, byte[] fileData) {
        ensureExists(videoId);
        if(fileData == null || fileData.length == 0) {
            throw new StorageException("File data cannot be null or empty");
        }
        fileRepository.save(videoId,fileData);
    }

    /** This check if hte videoId is null or not if yes then throws an exception using
     * ensureExists() method.
     *
     * @param videoId is an object which check for matching Id in repository
     * @return return the actual binary stream of data
     */
    @Override
    public byte[] downloadVideo(UUID videoId) {
        ensureExists(videoId);
        return fileRepository.findById(videoId);
    }

    /**
     * @param videoId is Unique Identifier for video
     * @return sends the continuous stream of Byte value
     */
    @Override
    public InputStream streamVideo(UUID videoId) {
        ensureExists(videoId);
        byte[] fileData = fileRepository.findById(videoId);
        return new ByteArrayInputStream(fileData);
    }

    /** same here check for the null value if found throws an exception
     * @param videoId is of UUID type
     */
    @Override
    public void deleteVideoFile(UUID videoId) {
        ensureExists(videoId);
        fileRepository.delete(videoId);
    }


    public void ensureExists(UUID videoId) {
        fileRepository.findById(videoId);
    }
}
