package com.vti.vtiacademy.controller;

import com.vti.vtiacademy.dto.ClassRoomCreateRequest;
import com.vti.vtiacademy.dto.ClassRoomUpdateRequest;
import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.entity.ClassRoom;
import com.vti.vtiacademy.form.ClassRoomFilterForm;
import com.vti.vtiacademy.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classRoom")
@CrossOrigin("*")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<ClassRoom> findAll() {
        return classRoomService.getAll();
    }

//    @GetMapping("/search")
//    public Page<ClassRoom> search(ClassRoomFilterForm form) {
//        return classRoomService.findAll(form);
//    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/search")
    public Page<ClassRoom> search(@RequestBody ClassRoomFilterForm form) {
        return classRoomService.search(form);
    }
//
//    @PostMapping("/search-name")
//    public List<ClassRoom> searchName(@RequestBody SearchClassRoom request) {
//        return classRoomService.getAllByNameAndSize(request);
//    }
//
//    @PostMapping("/search-v1")
//    public Page<ClassRoom> search(@RequestBody SearchClassRoom request) {
//        return classRoomService.searchV1(request);
//    }

    @GetMapping("/{id}")
    public ClassRoom findById(@PathVariable Long id) {
        return classRoomService.findById(id);
    }

    @PostMapping("/create")
    public ClassRoom create(@RequestBody ClassRoomCreateRequest request) {
        return classRoomService.create(request);
    }

    @PutMapping("/update")
    public ClassRoom update(@RequestBody ClassRoomUpdateRequest request) {
        return classRoomService.update(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        classRoomService.deleteById(id);
    }


}
