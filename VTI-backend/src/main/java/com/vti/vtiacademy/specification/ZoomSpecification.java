package com.vti.vtiacademy.specification;

import com.vti.vtiacademy.entity.Zoom;
import com.vti.vtiacademy.form.ZoomFilterForm;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class ZoomSpecification {
    public static Specification<Zoom> buildCondition(ZoomFilterForm form) {
        return Specification.where(buildConditionName(form))
                .and(buildConditionLink(form));
    }

    public static Specification<Zoom> buildConditionName(ZoomFilterForm form) {
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

    public static Specification<Zoom> buildConditionLink(ZoomFilterForm form) {
        if (!Utils.isBlank(form.getLink())) {
            return (root, query, cri) -> {
                return cri.like(root.get("link"), "%" + form.getLink() + "%");
            };
        } else {
            return null;
        }
    }
}
