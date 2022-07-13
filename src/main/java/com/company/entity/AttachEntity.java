package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column
    private String extension;
    @Column(name = "original_name")
    private String originalName;
    @Column
    private Long size;
    @Column
    private String path;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    public AttachEntity() {
    }

    public AttachEntity(String id) {
        this.id = id;
    }
}
