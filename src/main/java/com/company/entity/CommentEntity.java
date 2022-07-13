package com.company.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @JoinColumn(nullable = false, name = "profile_id")
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @JoinColumn(nullable = false, name = "article_id")
    @ManyToOne(targetEntity = VideoEntity.class, fetch = FetchType.LAZY)
    private VideoEntity video;

    @JoinColumn(name = "comment_id")
    @ManyToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    private CommentEntity comment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public CommentEntity(Integer id) {
        this.id = id;
    }
}
