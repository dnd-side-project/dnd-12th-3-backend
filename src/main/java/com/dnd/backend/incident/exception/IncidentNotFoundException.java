package com.dnd.backend.incident.exception;

import com.dnd.backend.support.exception.AppException;

public class IncidentNotFoundException extends AppException {
	public IncidentNotFoundException() {
		super(IncidentErrorCode.INCIDENT_NOT_FOUND);
	}
}
