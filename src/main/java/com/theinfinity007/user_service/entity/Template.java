package com.theinfinity007.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "templates", indexes = {
        @Index(name = "idx_type_code_lang_unique", columnList = "type,code,lang", unique = true)
})
@Entity
public class Template extends BaseEntity {

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "lang", nullable = false)
    private String lang;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Lob
    @Column(name = "text", nullable = false, columnDefinition = "LONGTEXT")
    private String text;
}
