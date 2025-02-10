package com.dnd.backend.domain.mapping;


import com.dnd.backend.domain.Event;
import com.dnd.backend.domain.User;
import com.dnd.backend.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "city")
    private String city;

    @Column(nullable = false, name = "address_line")
    private String addressLine;

    @OneToOne(mappedBy = "region", cascade = CascadeType.ALL)
    private Event event;

    @OneToOne(mappedBy = "region", cascade = CascadeType.ALL)
    private User user;
}