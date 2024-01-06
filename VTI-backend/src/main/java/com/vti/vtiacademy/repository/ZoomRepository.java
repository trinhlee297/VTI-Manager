package com.vti.vtiacademy.repository;

import com.vti.vtiacademy.entity.Zoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ZoomRepository extends JpaRepository<Zoom, Long>,
        JpaSpecificationExecutor<Zoom> {

}
