package com.dnd.backend.domain.mapping;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RecordDetail {

    // 세션 이름 & 부스 이름 & 주제(기타) 일괄처리
    @Column(name = "detail_record_name")
    private String detailRecordName;
    // 해당 항목에 대한 설명(내용) 일괄처리
    @Column(name = "record_description")
    private String recordDescription;

    // 강연/세미나 및 기타의 경우 행사 정보 저장
    @Column(name = "event_description")
    private String eventDescription;
    // 설명에 대한 이미지 저장 경로 (강연/세미나 및 기타는 1개의 튜플만 저장, 나머지는 여러 개의 튜플 저장 가능)
    @Column(name = "description_file_url")
    private String descriptionFileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Record record;
}