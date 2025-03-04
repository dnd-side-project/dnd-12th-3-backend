package com.dnd.backend.incident.dto;

import java.time.LocalDate;

public record GetResult(
	Long id,
	Long writerId,
	String title,
	String description,
	LocalDate createdAt
) {
}
