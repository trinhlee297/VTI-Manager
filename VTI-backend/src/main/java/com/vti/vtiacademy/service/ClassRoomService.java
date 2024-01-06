package com.vti.vtiacademy.service;

import com.vti.vtiacademy.dto.ClassRoomCreateRequest;
import com.vti.vtiacademy.dto.ClassRoomUpdateRequest;
import com.vti.vtiacademy.dto.SearchClassRoom;
import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.entity.ClassRoom;
import com.vti.vtiacademy.form.ClassRoomFilterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassRoomService {

    List<ClassRoom> getAll();

//    Page<ClassRoom> findAll(ClassRoomFilterForm form);

//    List<ClassRoom> getAllByNameAndSize(SearchClassRoom request);
    Page<ClassRoom> search(ClassRoomFilterForm form);

    ClassRoom findById(Long id);

    void deleteById(Long id);

    ClassRoom create(ClassRoomCreateRequest request);

    ClassRoom update(ClassRoomUpdateRequest request);



}
