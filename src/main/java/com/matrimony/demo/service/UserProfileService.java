package com.matrimony.demo.service;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import com.matrimony.demo.bucket.BucketName;
import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.filestore.FileStore;
import com.matrimony.demo.model.User;
import com.matrimony.demo.profile.UserProfile;
import com.matrimony.demo.repository.UserRepository;

import static org.apache.http.entity.ContentType.*;

@Repository
@CrossOrigin("*")
public class UserProfileService {

	private final UserProfileDataAccessService userProfileDataAccessService;
	@Autowired 
	private FileStore fileStore;
	 @Autowired
	private UserRepository userRepository;

	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService) {
		super();
		this.userProfileDataAccessService = userProfileDataAccessService;
	}
	
	public List<UserProfile> getUserProfiles(){
		return userProfileDataAccessService.getUserProfiles();
	}

	public void uploadUserProfileImage(Long userProfileId, MultipartFile file) {
		// 1. Check if image is not empty
        isFileEmpty(file);
        // 2. If file is an image
        isImage(file);

        // 3. The user exists in our database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        // 4. Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5. Store the image in s3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
            User u = userRepository.findById(user.getUserProfileId()).orElseThrow(() -> new ResourceNotFoundException("UserProfile not found ","id", user.getUserProfileId()));;
            u.setImageUrl(filename);
            userRepository.save(u);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
		
	}

	public byte[] downloadUserProfileImage(Long userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());

        return (byte[]) user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);

    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfileOrThrow(Long userProfileId) {
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }
	
}
