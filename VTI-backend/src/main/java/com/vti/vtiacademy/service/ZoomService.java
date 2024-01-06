package com.vti.vtiacademy.service;

import com.vti.vtiacademy.dto.ZoomCreateRequest;
import com.vti.vtiacademy.dto.ZoomUpdateRequest;
import com.vti.vtiacademy.entity.ClassRoom;
import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.form.ZoomFilterForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ZoomService {

    List<Zoom> getAll();

    Page<Zoom> search(ZoomFilterForm form);

    Zoom findById(Long id);

    void deleteById(Long id);

    Zoom create(ZoomCreateRequest request);

    Zoom update(ZoomUpdateRequest request);


}
