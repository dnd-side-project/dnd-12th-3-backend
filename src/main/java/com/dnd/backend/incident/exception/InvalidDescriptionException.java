package com.dnd.backend.incident.exception;

import com.dnd.backend.support.exception.AppException;

public class InvalidDescriptionException extends AppException {
	public InvalidDescriptionException() {
		super(IncidentErrorCode.INCIDENT_DESCRIPTION_INVALID);
	}
}
