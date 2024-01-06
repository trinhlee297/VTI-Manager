package com.vti.vtiacademy.exepction;

public enum ErrorResponseEnum {
    NOT_FOUND_PRODUCT(404, "Không tìm thấy sản phẩm"),
    NOT_FOUND_ACCOUNT(404, "Không tìm thấy người dùng"),
    NOT_FOUND_CLASS(404, "Class don't exist !"),
    USERNAME_EXISTED(400, "Username đã tồn tại!"),
    ZOOM_NOT_EXISTED(404, "Zoom not exists !"),
    USERNAME_NOT_VALID(400,"User Name không đúng định dạng"),
    USERNAME_NOT_FOUND(401,"User Name không tồn tại"),
    PASSWORD_FAILS(401,"Mật khẩu không chính xác"),
    ACC_NOT_ACTIVE(401,"Acc chua duoc kich hoat, kiem tra mail de kich hoat");






    public final int status;
    public final String message;
    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;

    }
}
