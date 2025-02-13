package com.dnd.backend.infrastructure.repository.incident.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnd.backend.domain.incident.entity.IncidentEntity;

public interface IncidentJpaRepository extends JpaRepository<IncidentEntity, Long> {

	List<IncidentEntity> findAllByWriterId(Long writerId, Pageable pageable);

	List<IncidentEntity> findAllByWriterIdAndIdLessThan(Long writerId, Long id, Pageable pageable);

	@Modifying
	@Query("DELETE FROM IncidentEntity i WHERE i.id = :id")
	int deleteByIdAndReturnCount(@Param("id") Long id);

}
