package com.nhs.orgskills.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @SequenceGenerator(name = "skill_seq", sequenceName = "skill_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String level;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "skills")
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();
}
