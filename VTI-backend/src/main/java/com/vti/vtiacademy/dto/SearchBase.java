package com.vti.vtiacademy.dto;

import lombok.Data;

@Data
public class SearchBase {
    protected String sortType; // DESC, ASC
    protected String sortFiled; // Cot duoc sap xep
    protected int pageSize;
    protected int pageNumber;

}
