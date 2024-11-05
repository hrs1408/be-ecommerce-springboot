package vn.id.nguyenphanhuyhieu.ecommerce.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpsUtils {
    SUCCESS(200, "Thành công")
    , OBJECT_NOT_EXIST(404, "Đối tượng không tồn tại")

    ;

    private final int code;
    private final String message;
}
