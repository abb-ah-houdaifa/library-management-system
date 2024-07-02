package com.library.library_management_system.service;

import com.library.library_management_system.entity.Editor;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.repository.EditorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EditorService{
    
    private final EditorRepository editorRepository;
    private final Validator validator;

    public List<Editor> findAllEditors() {
        return editorRepository.findAll();
    }

    @Transactional
    public Editor saveEditor(Editor editor) {

        validateEditorAttributes(editor);

        return editorRepository.save(editor);
    }

    private void validateEditorAttributes(Editor editor) {
        Set<ConstraintViolation<Editor>> violations = validator.validate(editor);

        if(!violations.isEmpty()){
            StringBuilder violationMessage = new StringBuilder();
            for (ConstraintViolation<Editor> violation: violations){
                violationMessage.append(violation.getMessage()).append(", ");
            }

            throw new ConstraintViolationException("Error occurred : " + violationMessage, violations);
        }
    }

    public Editor findEditorById(Long editorId) {
        Optional<Editor> editor = editorRepository.findById(editorId);
        if(editor.isEmpty()) throw new NotFoundException("Editor with ID : " + editorId + " Not Found");

        return editor.get();
    }

    @Transactional
    public Editor updateEditor(Long editorId, Editor newEditorDetails) {
        Editor editor = findEditorById(editorId);

        String editorName = Optional.ofNullable(newEditorDetails.getEditorName())
                .orElse(editor.getEditorName());
        editor.setEditorName(editorName);

        String editorAddress = Optional.ofNullable(newEditorDetails.getEditorAddress())
                .orElse(editor.getEditorAddress());
        editor.setEditorAddress(editorAddress);

        String editorEmail = Optional.ofNullable(newEditorDetails.getEditorEmail())
                .orElse(editor.getEditorEmail());
        editor.setEditorEmail(editorEmail);

        return editorRepository.save(editor);
    }
}
