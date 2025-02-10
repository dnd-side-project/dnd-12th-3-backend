package com.dnd.backend.domain.mapping;

import com.dnd.backend.domain.Event;
import com.dnd.backend.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 300, name = "organizer_name")
    private String organizerName;

    @Column(name = "organizer_profile_file_url")
    private String organizerProfileFileUrl;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<Event> event = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}