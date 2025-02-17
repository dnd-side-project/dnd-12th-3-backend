package com.dnd.backend.mediaFile.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dnd.backend.mediaFile.dto.MediaFileInfo;
import com.dnd.backend.mediaFile.entity.JpaMediaFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaFileReadService {
	private final JpaMediaFileRepository mediaFileRepository;

	public List<MediaFileInfo> getMediaFilesByIncidentIds(List<Long> incidentIds) {
		var mediaFileEntities = mediaFileRepository.findByIncidentIdIn(incidentIds);

		return mediaFileEntities.stream()
			.map(media -> new MediaFileInfo(media.getIncidentId(), media.getFileType().name(), media.getFileUrl()))
			.collect(Collectors.toList());
	}
}
