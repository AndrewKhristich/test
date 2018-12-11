package com.jira.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Long estimate;
    @Column
    private Date createDate;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private  TaskProfile profile;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "username")
    private User author;
    @Column
    private String description;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "task")
    List<Report> reports = new ArrayList<>();
    @Transient
    @JsonIgnore
    private List<Long> subTasksList = new ArrayList<>();

    @Column
    private String subTasks;

    @JsonProperty("subTasks")
    public String getSubTasks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(subTasksList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping");
        }
    }

    @JsonProperty("subTasks")
    public void setSubTasks(String subTasks) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.subTasksList = mapper.readValue(subTasks, List.class);
        } catch (IOException e) {
            throw new RuntimeException("Error mapping");
        }
    }



}
