package com.dnd.backend.mediaFile.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageService {
	String uploadFile(MultipartFile file);

	byte[] downloadFile(String key);

}

