package vn.id.nguyenphanhuyhieu.ecommerce.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter(value = AccessLevel.PRIVATE)
@Builder
public class BaseRest {
    private boolean status;
    private int code;
    private String time;
    private Object data;
    private String message;
//    private Pagination pagination;
//
//    @Data
//    private static class Pagination {
//        private Integer prePage;
//        private int currentPage;
//        private Integer nextPage;
//
//        public Pagination(int offset, int maxResults, boolean nextPage) {
//            int current = Math.floorDiv(offset, maxResults) + 1;
//            this.currentPage = current;
//            this.prePage = (current == 1) ? null : (current - 1);
//            this.nextPage = nextPage ? (current + 1) : null;
//        }
//    }

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String empty = "data_empty";

    private String toJson() {
        String json = "{}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // remove null value
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Map<String ,Object> map = new HashMap<>();
        map = mapper.convertValue(this, new TypeReference<Map<String, Object>>(){});
        try {
            json = mapper.writeValueAsString(map);
        } catch (Exception e) {
            log.error("Convert string exception: {}", e.getStackTrace());
        }
        return json;
    }

    public static String response(HttpsUtils http, Object data) {
        BaseRest rest = BaseRest.builder()
                .status(http.getCode() == HttpsUtils.SUCCESS.getCode())
                .code(http.getCode())
                .time(LocalDateTime.now().format(format))
                .data(data)
                .message(http.getMessage())
                .build();
        return rest.toJson();
    }

}
