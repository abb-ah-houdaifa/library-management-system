package com.library.library_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "editor_seq_gen")
    @SequenceGenerator(name = "editor_seq_gen", sequenceName = "editor_seq", allocationSize = 1)
    private Long editorId;
    @NotBlank(message = "Editor name must not be blank")
    private String editorName;
    private String editorAddress;
    @Email(message = "Editor email is invalid")
    private String editorEmail;
}
