package com.vti.vtiacademy.form;

import com.vti.vtiacademy.dto.SearchBase;
import lombok.Data;

@Data
public class ZoomFilterForm extends SearchBase {
    private String name;
    private String link;
}
