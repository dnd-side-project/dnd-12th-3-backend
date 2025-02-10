package com.dnd.backend.domain;

import com.dnd.backend.domain.mapping.*;
import com.dnd.backend.domain.shared.BaseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 모집 시작일 & 모집 종료일
    @Column(nullable = false, name = "recruitment_start")
    private LocalDateTime recruitmentStart;
    @Column(nullable = false, name = "recruitment_end")
    private LocalDateTime recruitmentEnd;

    // 행사명, 간단소개,  대표이미지
    @Column(nullable = false, length = 20, name = "event_name")
    private String eventName;
    @Column(nullable = false, length = 200, name = "description")
    private String description;
    @Column(name = "event_main_file_url")
    private String eventMainFileUrl;

    // 행사 시작일, 행사 종료일, 행사 외부 사이트, 행사 정보, 행사 정보 이미지
    @Column(nullable = false, name = "event_start")
    private LocalDateTime eventStart;
    @Column(nullable = false, name = "event_end")
    private LocalDateTime eventEnd;
    @Column(length = 300, name = "link")
    private String link;
    @Column(nullable = false, length = 200, name = "event_content")
    private String eventContent;
    @Column(name = "event_inform_file_url")
    private String eventInformFileUrl;

    @Column(nullable = false, length = 40, name = "event_cost")
    private String eventCost;

    //행사 주소
    @Column(nullable = false, length = 40, name = "address")
    private String address;
    @Column(nullable = false, length = 40, name = "spec_address")
    private String specAddress;

    @Column(nullable = false, length = 20, name = "manager_name")
    private String managerName;
    @Column(length = 20, name = "manager_email")
    private String managerEmail;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int hits;

//    @Enumerated(EnumType.STRING)
//    private Category category;

//    @Enumerated(EnumType.STRING)
//    private KeywordName keywordName;

    // 커리어 키워드가 기타인 경우에만 입력 받음
    @Column(length = 300, name = "event_etc_detail")
    private String eventEtcDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Organizer organizer;

//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Wish> wish = new ArrayList<>();

//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
//    private List<Participate> participate = new ArrayList<>();
}
