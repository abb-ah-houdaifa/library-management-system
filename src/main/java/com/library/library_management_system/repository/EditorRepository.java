package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor,Long> {
}
