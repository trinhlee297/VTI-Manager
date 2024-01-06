package com.vti.vtiacademy.specification;

import com.vti.vtiacademy.entity.ClassEntity;
import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.form.ClassEntityFilterForm;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ClassEntitySpecification {
    public static Specification<ClassEntity> buildCondition(ClassEntityFilterForm form) {
        return Specification.where(buildConditionClassName(form))
                .and(buildConditionStatus(form))
                .and(buildConditionStartDate(form))
                .and(buildConditionEndDate(form))
                .and(findByZoomId(form));
    }

    public static Specification<ClassEntity> buildConditionClassName(ClassEntityFilterForm form) {
        if (!Utils.isBlank(form.getClassName())) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("className"), "%" + form.getClassName() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<ClassEntity> buildConditionStartDate(ClassEntityFilterForm form) {
        if (form.getStartDateMin() != null) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.greaterThanOrEqualTo(root.get("startDate"), form.getStartDateMin());
            };
        }
        return null;
    }

    public static Specification<ClassEntity> buildConditionEndDate(ClassEntityFilterForm form) {
        if (form.getClassStatus() != null) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.equal(root.get("status"), form.getClassStatus());
            };
        }
        return null;
    }

    public static Specification<ClassEntity> buildConditionStatus(ClassEntityFilterForm form) {
        if (form.getEndDateMin() != null) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.greaterThanOrEqualTo(root.get("endDate"), form.getEndDateMin());
            };
        }
        return null;
    }

    public static Specification<ClassEntity> findByZoomId(ClassEntityFilterForm form) {
        if (form.getZoomId() != null) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                Join<ClassEntity, Zoom> joiner = root.join("zoom");
                return cri.equal(joiner.get("id"), form.getZoomId());
            };
        }
        return null;
    }


}
