package com.vti.vtiacademy.repository;

import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long>,
        JpaSpecificationExecutor<ClassEntity> {
    List<ClassEntity> findAllByZoom_Id(Long zoomId);

//    List<ClassEntity> findAllByClassName(ClassEntity name);
}
