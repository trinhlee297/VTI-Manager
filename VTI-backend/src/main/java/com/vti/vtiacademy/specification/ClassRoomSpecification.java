package com.vti.vtiacademy.specification;

import com.vti.vtiacademy.entity.ClassRoom;
import com.vti.vtiacademy.form.ClassRoomFilterForm;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class ClassRoomSpecification {

    public static Specification<ClassRoom> buildCondition(ClassRoomFilterForm form) {
        return Specification.where(buildConditionName(form))
                .and(buildConditionAddress(form))
                .and(buildConditionSize(form));
    }

    public static Specification<ClassRoom> buildConditionName(ClassRoomFilterForm form) {
        if (!Utils.isBlank(form.getName())) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("name"), "%" + form.getName() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<ClassRoom> buildConditionAddress(ClassRoomFilterForm form) {
        if (!Utils.isBlank(form.getAddress())) {
            return (root, query, cri) -> {
                return cri.like(root.get("address"), "%" + form.getAddress() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<ClassRoom> buildConditionSize(ClassRoomFilterForm form) {
        if (form.getMinSize() != null && form.getMaxSize() != null) {
            return (root, query, cri) -> {
                return cri.between(root.get("size"),+ form.getMinSize(), form.getMaxSize());
            };
        } else {
            return null;
        }
    }
}

