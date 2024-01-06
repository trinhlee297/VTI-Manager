package com.vti.vtiacademy.repository;

import com.vti.vtiacademy.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long>,
        JpaSpecificationExecutor<ClassRoom> {

//    List<ClassRoom> findAllByNameContains(String name);
//    List<ClassRoom> findAllByNameContainsAndSize(String name, Integer size);
//    List<ClassRoom> findAllBySizeBetween(Integer sizeMin, Integer sizeMax);
//    Page<ClassRoom> findAllByNameContains(String name, Pageable pageable);

}
