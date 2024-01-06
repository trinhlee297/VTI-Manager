package com.vti.vtiacademy.service;

import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.ClassDTO;
import com.vti.vtiacademy.dto.ClassEntityCreateRequest;
import com.vti.vtiacademy.dto.ClassEntityUpdateRequest;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.form.ClassEntityFilterForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClassEntityService {
    List<ClassEntity> getAll();
    Page<ClassEntity> search(ClassEntityFilterForm form);

    ClassEntity findById(Long id);

    void deleteById(Long id);

    ClassEntity create(ClassEntityCreateRequest request);

    ClassEntity update(ClassEntityUpdateRequest request);

    ClassDTO classEntityToDto(ClassEntity entity);

//    List<ClassEntity> getCLassByName();

}
