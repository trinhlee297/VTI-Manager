package com.vti.vtiacademy.specification;

import com.vti.vtiacademy.entity.Account;
import com.vti.vtiacademy.form.AccountFilterForm;
import com.vti.vtiacademy.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
    public static Specification<Account> buildCondition(AccountFilterForm form) {
        return Specification.where(buildConditionAccountName(form))
                .or(buildConditionAccountEmail(form))
                .or(buildConditionAccountFullName(form));
//                .and(buildConditionEndDate(form))
//                .and(findByZoomId(form));
    }

    public static Specification<Account> buildConditionAccountName(AccountFilterForm form) {
        if (!Utils.isBlank(form.getUsername())) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("username"), "%" + form.getUsername() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<Account> buildConditionAccountEmail(AccountFilterForm form) {
        if (!Utils.isBlank(form.getUsername())) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("email"), "%" + form.getUsername() + "%");
            };
        } else {
            return null;
        }
    }

    public static Specification<Account> buildConditionAccountFullName(AccountFilterForm form) {
        if (!Utils.isBlank(form.getUsername())) {
            // Tạo điều kiện tìm kiếm với name
            return (root, query, cri) -> {
                // root: Chọn cột, field, để tìm kiếm (giá trị là thuộc tính trong java)
                // cri: CriteriaBuilder Khai báo loại so sánh dữ liệu. ( lớn hơn, nhỏ hơn, equal, like,.... )
                return cri.like(root.get("fullName"), "%" + form.getUsername() + "%");
            };
        } else {
            return null;
        }
    }
}
