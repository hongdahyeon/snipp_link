package hong.snipp.link.snipp_link.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * packageName    : hong.snipp.link.snipp_link.global.util
 * fileName       : TimeUtil
 * author         : work
 * date           : 2025-04-16
 * description    : Date / Time 관련 유틸함수
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 */
public class TimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final LocalDateTime TODAY = LocalDateTime.now();

    public static String nowDate(){
        return TODAY.format(DATE_TIME_FORMATTER);
    }

    public static String nowDateYYMMDD() {
        return TODAY.format(DATE_FORMATTER);
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * @method      addTimeFormat
     * @author      work
     * @date        2025-04-16
     * @deacription (입력) YYYY-MM-DD
     *              (출력) YYYY-MM-DD HH:mm
    **/
    public static String addTimeFormat(String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDate date = LocalDate.parse(dateString, inputFormatter);
        LocalDateTime dateTime = date.atStartOfDay(); // 00:00:00 붙이기

        return dateTime.format(outputFormatter); // "2025-04-01 00:00"
    }

    /**
     * @method      formatToDateTimeHM
     * @author      work
     * @date        2025-04-16
     * @deacription (형식 변환) "2025-05-01T23:59:59" => "2025-05-01 23:59"
    **/
    public static String formatToDateTimeHM(String isoDateTime) {
        if (isoDateTime == null || isoDateTime.isBlank()) return "";
        try {
            LocalDateTime dt = LocalDateTime.parse(isoDateTime);
            return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            return isoDateTime; // 혹시 잘못된 형식이면 원본 리턴
        }
    }

    /**
     * @method      isXYearAfter
     * @author      work
     * @date        2025-04-18
     * @deacription {compareDateString}가 현재로부터 {year}년 이내인지 여부 체크
    **/
    public static boolean isXYearAfter(String compareDateString, int year) {
        LocalDateTime compareDate = LocalDateTime.parse(compareDateString, DATE_TIME_FORMATTER);
        LocalDateTime oneYearAgo = TODAY.minus(year, ChronoUnit.YEARS);
        return !compareDate.isBefore(oneYearAgo);
    }

    public static boolean dateCompare(String futureDateString) {
        LocalDateTime futureDate = LocalDateTime.parse(futureDateString, DATE_TIME_FORMATTER);
        return futureDate.isAfter(TODAY);
    }

    public static String daysAfter(int after){
        LocalDateTime futureDate = TODAY.plusDays(after);
        return futureDate.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime daysAfter_Date(int after) {
        return TODAY.plusDays(after);
    }

}
