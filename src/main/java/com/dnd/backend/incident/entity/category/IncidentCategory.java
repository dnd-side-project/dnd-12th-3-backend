package com.dnd.backend.incident.entity.category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dnd.backend.incident.entity.category.type.CollapseType;
import com.dnd.backend.incident.entity.category.type.ExplosionType;
import com.dnd.backend.incident.entity.category.type.FineDustType;
import com.dnd.backend.incident.entity.category.type.FireType;
import com.dnd.backend.incident.entity.category.type.NaturalIncidentType;
import com.dnd.backend.incident.entity.category.type.TerrorType;
import com.dnd.backend.incident.entity.category.type.TrafficType;
import com.dnd.backend.incident.exception.InvalidDisasterCategoryException;

public enum IncidentCategory {
	교통("교통", List.of(TrafficType.values())),
	화재("화재", List.of(FireType.values())),
	붕괴("붕괴", List.of(CollapseType.values())),
	폭발("폭발", List.of(ExplosionType.values())),
	자연재난("자연재난", List.of(NaturalIncidentType.values())),
	미세먼지("미세먼지", List.of(FineDustType.values())),
	테러("테러", List.of(TerrorType.values())),
	EMPTY("없음", Collections.EMPTY_LIST);

	private final String displayName;
	private final Map<String, IncidentType> subTypeMap;

	IncidentCategory(String name, List<IncidentType> subTypes) {
		this.displayName = name;
		this.subTypeMap = subTypes.stream()
			.collect(Collectors.toMap(IncidentType::getName, Function.identity()));
	}

	public static IncidentCategory mapToDisasterGroup(String groupName) {
		return Arrays.stream(IncidentCategory.values())
			.filter(group -> group.getName().equals(groupName))
			.findFirst()
			.orElseThrow(InvalidDisasterCategoryException::new);
	}

	public Optional<IncidentType> getDisasterSubType(String name) {
		return Optional.ofNullable(subTypeMap.get(name));
	}

	public boolean hasDisasterSubType(IncidentType incidentType) {
		return subTypeMap.containsValue(incidentType);
	}

	public String getName() {
		return displayName;
	}

	public List<IncidentType> getSubTypes() {
		return new ArrayList<>(subTypeMap.values());
	}
}
