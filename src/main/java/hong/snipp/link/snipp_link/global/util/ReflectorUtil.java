package hong.snipp.link.snipp_link.global.util;

import jodd.util.StringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.global.util
 * fileName       : ReflectorUtil
 * author         : work
 * date           : 2025-04-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
public class ReflectorUtil {

    public static Map<String, Object> getGetterMap(Object o) {
        Map<String, Object> map = new HashMap<>();
        return getGetterMap(o, o.getClass(), map);
    }


    /**
     * @method      getGetterMap
     * @author      work
     * @date        2025-04-15
     * @deacription 클래스의 모든 상속 관계에 걸쳐서 getter 메서드 값을 찾아 맵에 저장
     *              -> getter 메서드를 통해 필드 값을 맵으로 반환
    **/
    private static Map<String, Object> getGetterMap(Object o, Class<?> clazz, Map<String, Object> map) {
        Method[] methods = clazz.getDeclaredMethods();
        for( Method m : methods ) {
            // {get}으로 시작하는 메서드 찾기
            // => {getClass}의 경우 객체의 클래스 정보를 반환하는 메서드이기에 제외
            if( m.getName().startsWith("get") && !m.getName().equals("getClass") ) {
                String key = fieldName(m.getName());
                map.put(key, getValue(o, m));
            }
        }
        /* 현재 클래스의 부모 클래스를 가져오는데, 없거나 Object 클래스라면 종료 */
        Class<?> s = clazz.getSuperclass();
        if( s == null || s.equals(Object.class) ) {
            return map;
        }
        return getGetterMap(o, s, map);
    }

    /**
     * @method      fieldName
     * @author      work
     * @date        2025-04-15
     * @deacription 앞부분 get 값을 제거하고, 남은 문자열의 첫 글자를 소문자로 바꾸어 반환
    **/
    private static String fieldName(String getterMethodName) {
        return StringUtil.uncapitalize(getterMethodName.replace("get", ""));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object o, Method m) {
        Object res = null;
        try {
            m.setAccessible(true);                  // private 메서드라도 접근 가능하도록 설정
            res = m.invoke(o, new Object[] {});     // 메서드 호출
        } catch (Exception e) {
            return null;
        }
        return (T) res;  // 결과값을 제네릭 타입으로 반환
    }
}
