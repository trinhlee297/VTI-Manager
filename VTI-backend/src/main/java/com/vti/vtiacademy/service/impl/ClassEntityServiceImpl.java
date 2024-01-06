package com.vti.vtiacademy.service.impl;

import com.vti.vtiacademy.dto.AccountDTO;
import com.vti.vtiacademy.dto.ClassDTO;
import com.vti.vtiacademy.dto.ClassEntityCreateRequest;
import com.vti.vtiacademy.dto.ClassEntityUpdateRequest;
import com.vti.vtiacademy.entity.*;
import com.vti.vtiacademy.form.ClassEntityFilterForm;
import com.vti.vtiacademy.repository.AccountRepository;
import com.vti.vtiacademy.repository.ClassEntityRepository;
import com.vti.vtiacademy.repository.ClassRoomRepository;
import com.vti.vtiacademy.repository.ZoomRepository;
import com.vti.vtiacademy.service.ClassEntityService;
import com.vti.vtiacademy.specification.ClassEntitySpecification;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassEntityServiceImpl implements ClassEntityService {
    @Autowired
    private ClassEntityRepository classEntityRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ZoomRepository zoomRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public List<ClassEntity> getAll() {
        return classEntityRepository.findAll();
    }

    @Override
    public Page<ClassEntity> search(ClassEntityFilterForm form) {
        PageRequest pageRequest = Utils.buildPageRequest(form);
        Specification<ClassEntity> specification = ClassEntitySpecification.buildCondition(form);
        return classEntityRepository.findAll(specification,pageRequest);
    }

    @Override
    public ClassEntity findById(Long id) {
        return classEntityRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        classEntityRepository.deleteById(id);

    }

    @Override
    public ClassEntity create(ClassEntityCreateRequest request) {
        ClassEntity classEntity = new ClassEntity();
        BeanUtils.copyProperties(request, classEntity);
        // goi sang ben account -> lay gia tri
        Account account = accountRepository.findById(request.getAccountId()).get();
        Zoom zoom = zoomRepository.findById(request.getZoomId()).get();
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId()).get();

        // set cac doi tuong vao khoa ngoai
        classEntity.setMentor(account);
        classEntity.setZoom(zoom);
        classEntity.setClassRoom(classRoom);
        return classEntityRepository.save(classEntity);
    }

    @Override
    public ClassEntity update(ClassEntityUpdateRequest request) {
        ClassEntity classEntity = classEntityRepository.findById(request.getId()).get();
        BeanUtils.copyProperties(request, classEntity);
        // goi sang ben account -> lay gia tri
        Account account = accountRepository.findById(request.getAccountId()).get();
        Zoom zoom = zoomRepository.findById(request.getZoomId()).get();
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId()).get();

        // set cac doi tuong vao khoa ngoai
        classEntity.setMentor(account);
        classEntity.setZoom(zoom);
        classEntity.setClassRoom(classRoom);
        return classEntityRepository.save(classEntity);
    }

    @Override
    public ClassDTO classEntityToDto(ClassEntity entity) {
        ClassDTO classDTO =  new ClassDTO();
        classDTO.setId(entity.getId());
        classDTO.setClassName(entity.getClassName());
        classDTO.setClassRoom(entity.getClassRoom());
        classDTO.setStatus(entity.getStatus());
        classDTO.setDescription(entity.getDescription());
        classDTO.setZoom(entity.getZoom());
        classDTO.setEndDate(entity.getEndDate());
        classDTO.setStartDate(entity.getStartDate());
        classDTO.setMentor(entity.getMentor().getFullName());
        classDTO.setTeachingFrom(entity.getTeachingFrom());

        return classDTO;
    }

//    @Override
//    public List<ClassEntity> getCLassByName() {
//        ClassEntity classEntity ;
//        return classEntityRepository.findAllByClassName(classEntity.getClassName());
//    }
}
