package com.vti.vtiacademy.controller;

import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.ClassDTO;
import com.vti.vtiacademy.dto.ClassEntityCreateRequest;
import com.vti.vtiacademy.dto.ClassEntityUpdateRequest;
import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.form.ClassEntityFilterForm;
import com.vti.vtiacademy.service.AccountService;
import com.vti.vtiacademy.service.ClassEntityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classEntity")
@CrossOrigin("*")
public class ClassEntityController {

    @Autowired
    private ClassEntityService classEntityService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<ClassDTO> findAll()
    {
        List<ClassEntity> classEntityList = classEntityService.getAll();
        List<ClassDTO> classDTOList = new ArrayList<ClassDTO>();
        for (ClassEntity classEntity: classEntityList
        ) {
            ClassDTO classDTO = new ClassDTO();
            BeanUtils.copyProperties(classEntity, classDTO);
            classDTOList.add(classDTO);
        }

        return classDTOList;
    }

//    @GetMapping("/getAll-className")
//    public List<ClassEntity> getClassByName() {
//        return classEntityService.getCLassByName();
//    }


    @PostMapping("/search")
    public Page<ClassDTO> findAll(@RequestBody ClassEntityFilterForm form) {
        Page<ClassEntity> classPage = classEntityService.search(form);
        Page<ClassDTO> classDTOPage = classPage.map(entity -> {
            ClassDTO dto = classEntityService.classEntityToDto(entity);
            return dto;
        });
        return classDTOPage;
    }

//    @PostMapping("/search")
//    public Page<ClassEntity> findAll(@RequestBody ClassEntityFilterForm form) {
//        return classEntityService.search(form);
//    }


    @GetMapping("/{id}")
    public ClassEntity findById(@PathVariable Long id) {
        return classEntityService.findById(id);

    }

    @PostMapping("/create")
    public ClassEntity create(@RequestBody ClassEntityCreateRequest request) {
        return classEntityService.create(request);
    }

    @PutMapping("/update")
    public ClassDTO update(@RequestBody ClassEntityUpdateRequest request) {
        ClassDTO classDTO = new ClassDTO();
        ClassEntity classEntity = classEntityService.update(request);
        BeanUtils.copyProperties(classEntity,classDTO);
        return classDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        classEntityService.deleteById(id);
    }
}
