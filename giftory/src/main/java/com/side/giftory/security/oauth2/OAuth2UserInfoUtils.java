package com.side.giftory.security.oauth2;

import java.util.Collections;
import java.util.Map;

public class OAuth2UserInfoUtils {

    /**
     * Map에서 안전하게 Map 타입 값을 꺼냄, 없으면 빈 맵 리턴
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> safeGetMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        return Collections.emptyMap();
    }

    /**
     * String 값을 꺼내고 null이면 빈 문자열 반환 (선택적)
     */
    public static String safeGetString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return "";
    }
}
