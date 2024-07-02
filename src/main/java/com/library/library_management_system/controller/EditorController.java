package com.library.library_management_system.controller;

import com.library.library_management_system.entity.Editor;
import com.library.library_management_system.service.EditorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping("/editors")
    public ResponseEntity<List<Editor>> findAllEditors(){
        List<Editor> editors = editorService.findAllEditors();
        return new ResponseEntity<>(editors, HttpStatus.FOUND);
    }

    @PostMapping("/editors/add")
    public ResponseEntity<Editor> addEditor(
            @RequestBody @Valid Editor editor
    ){
        Editor savedEditor = editorService.saveEditor(editor);

        return new ResponseEntity<>(savedEditor,HttpStatus.CREATED);
    }

    @PutMapping("/editors/edit/{id}")
    public ResponseEntity<Editor> updateEditor(
            @PathVariable("id") Long editorId,
            @RequestBody Editor newEditorDetails
    ){
        Editor updatedEditor = editorService.updateEditor(editorId,newEditorDetails);

        return new ResponseEntity<>(updatedEditor,HttpStatus.ACCEPTED);
    }
}
