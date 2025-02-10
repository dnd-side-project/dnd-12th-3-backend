package com.dnd.backend.domain.mapping;


import com.dnd.backend.domain.User;
import com.dnd.backend.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscribe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // default EAGER
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    public Subscribe(User toUser, User fromUser){

        this.toUser = toUser;
        this.fromUser = fromUser;
    }
    public Long getFromUser() {
        return fromUser.getId();
    }

    public Long getToUser() {
        return toUser.getId();
    }

}