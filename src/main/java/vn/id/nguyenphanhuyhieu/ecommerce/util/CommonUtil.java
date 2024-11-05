package vn.id.nguyenphanhuyhieu.ecommerce.util;

public class CommonUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank() || "null".equals(str);
    }

}
