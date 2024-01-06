package com.vti.vtiacademy.controller;

import com.vti.vtiacademy.dto.ZoomCreateRequest;
import com.vti.vtiacademy.dto.ZoomUpdateRequest;
import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.form.ZoomFilterForm;
import com.vti.vtiacademy.service.ZoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/zoom")
@CrossOrigin("*")
@Validated

public class ZoomController {

    @Autowired
    private ZoomService zoomService;

    @GetMapping("/class/{id}")
    public Zoom getListClassById(@PathVariable Long id) {
        return zoomService.findById(id);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<Zoom> findAll() {
        return zoomService.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/search")
    public Page<Zoom> findAll(@RequestBody ZoomFilterForm form) {
        return zoomService.search(form);
    }

    @GetMapping("/{id}")
    public Zoom findById(@PathVariable Long id) {
        return zoomService.findById(id);
    }

    @PostMapping("/create")
    public Zoom create(@RequestBody @Valid ZoomCreateRequest request) {
        return zoomService.create(request);
    }

    @PutMapping("/update")
    public Zoom update(@RequestBody ZoomUpdateRequest request) {
        return zoomService.update(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        zoomService.deleteById(id);
    }

}
