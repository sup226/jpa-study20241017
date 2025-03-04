package com.study.jpa.chap05.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"idols"})
@EqualsAndHashCode(of = "id")
@Table(name = "tbl_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idol> idols = new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addIdol(Idol idol) {
        idols.add(idol);
        idol.setGroup(this);
    }

    public void removeIdol(Idol idol) {
        idols.remove(idol);
        idol.setGroup(null);
    }

}
