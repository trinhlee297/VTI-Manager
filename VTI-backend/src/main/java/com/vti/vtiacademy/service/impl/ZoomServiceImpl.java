package com.vti.vtiacademy.service.impl;

import com.vti.vtiacademy.dto.ZoomCreateRequest;
import com.vti.vtiacademy.dto.ZoomUpdateRequest;
import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.exepction.CustomException;
import com.vti.vtiacademy.exepction.ErrorResponseEnum;
import com.vti.vtiacademy.form.ZoomFilterForm;
import com.vti.vtiacademy.repository.ZoomRepository;
import com.vti.vtiacademy.service.ZoomService;
import com.vti.vtiacademy.specification.ZoomSpecification;
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
public class ZoomServiceImpl implements ZoomService {
    @Autowired
    private ZoomRepository zoomRepository;

    @Override
    public List<Zoom> getAll() {
        return zoomRepository.findAll();
    }

    @Override
    public Page<Zoom> search(ZoomFilterForm form) {
        PageRequest pageRequest = Utils.buildPageRequest(form);
        var spec = ZoomSpecification.buildCondition(form);
        return zoomRepository.findAll(spec, pageRequest);
    }

    @Override
    public Zoom findById(Long id) {
        Optional<Zoom> optionalZoom = zoomRepository.findById(id);
        if (optionalZoom.isPresent()) {
            return optionalZoom.get();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (!zoomRepository.existsById(id)){
            throw new CustomException(ErrorResponseEnum.ZOOM_NOT_EXISTED);
        }
        zoomRepository.deleteById(id);

    }

    @Override
    public Zoom create(ZoomCreateRequest request) {
        Zoom zoom = new Zoom();
        BeanUtils.copyProperties(request, zoom);
        return zoomRepository.save(zoom);
    }

    @Override
    public Zoom update(ZoomUpdateRequest request) {
        Zoom zoom = findById(request.getId());
        if (zoom != null) {
            BeanUtils.copyProperties(request, zoom);
            return zoomRepository.save(zoom);
        }
        return null;
    }
}
