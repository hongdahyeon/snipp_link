package hong.snipp.link.snipp_link.global.util;

import java.util.Random;
import java.util.UUID;

/**
 * packageName    : hong.snipp.link.snipp_link.global.util
 * fileName       : StringUtil
 * author         : work
 * date           : 2025-04-15
 * description    : 문자열 관련 유틸 함수
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
public class StringUtil {

    public static boolean startswith(String str, String prefix) {
        if(str == null || str.length() == 0) return false;
        else {
            return str.startsWith(prefix);
        }
    }

    public static boolean equal(String str1, String str2) {
        if(str1 == null || str2 == null) return false;
        else {
            return str1.equals(str2) && str2.equals(str1);
        }
    }

    public static String fixLength(String src, int limit) {
        if (src.length() > limit) {
            return src.substring(0, limit) + "...";
        }
        return src;
    }

    public static String random(int length){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            password.append(randomChar);
        }

        return password.toString();
    }

    public static <T> T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }

    public static String numberFormat(long amount) {
        return String.format("%,d", amount);
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

}
