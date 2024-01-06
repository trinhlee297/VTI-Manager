package com.vti.vtiacademy.service.impl;

import com.vti.vtiacademy.dto.ClassRoomCreateRequest;
import com.vti.vtiacademy.dto.ClassRoomUpdateRequest;
import com.vti.vtiacademy.dto.SearchClassRoom;
import com.vti.vtiacademy.entity.ClassRoom;
import com.vti.vtiacademy.form.ClassRoomFilterForm;
import com.vti.vtiacademy.repository.ClassRoomRepository;
import com.vti.vtiacademy.service.ClassRoomService;
import com.vti.vtiacademy.specification.ClassRoomSpecification;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public List<ClassRoom> getAll() {
        return classRoomRepository.findAll();
    }

    @Override
    public Page<ClassRoom> search(ClassRoomFilterForm form) {
        PageRequest pageRequest = Utils.buildPageRequest(form);
        var spec = ClassRoomSpecification.buildCondition(form);
        return classRoomRepository.findAll(spec, pageRequest);
    }

//    @Override
//    public List<ClassRoom> getAllByNameAndSize(SearchClassRoom request) {
////        if (request.getSize() == null) {
////            return classRoomRepository.findAllByNameContains(request.getName());
////        } else {
////            return classRoomRepository.findAllByNameContainsAndSize(request.getName(), request.getSize());
//        return classRoomRepository.findAllBySizeBetween(request.getMinSize(), request.getMaxSize());
//        }
//
//    @Override
//    public Page<ClassRoom> searchV1(SearchClassRoom request) {
//        Pageable pageable = Pageable.ofSize(request.getPageSize())
//                .withPage(request.getPageNumber());
//        return classRoomRepository.findAllByNameContains(request.getName(),pageable);
//    }
////    }


    @Override
    public ClassRoom findById(Long id) {
        Optional<ClassRoom> optionalClassRoom = classRoomRepository.findById(id);
        if (optionalClassRoom.isPresent()) {
            return optionalClassRoom.get();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        classRoomRepository.deleteById(id);

    }

    @Override
    public ClassRoom create(ClassRoomCreateRequest request) {
        ClassRoom classRoom = new ClassRoom();
        BeanUtils.copyProperties(request, classRoom);
        return classRoomRepository.save(classRoom);
    }

    @Override
    public ClassRoom update(ClassRoomUpdateRequest request) {
        ClassRoom classRoom = findById(request.getId());
        if (classRoom != null) {
            BeanUtils.copyProperties(request, classRoom);
            return classRoomRepository.save(classRoom);
        }
        return null;
    }
}
