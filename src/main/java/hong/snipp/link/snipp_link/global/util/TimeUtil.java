package hong.snipp.link.snipp_link.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * packageName : hong.snipp.link.snipp_link.global.util
 * fileName : TimeUtil
 * author : work
 * date : 2025-04-16
 * description : Date / Time 관련 유틸함수
 * ===========================================================
 * DATE             AUTHOR      NOTE
 * -----------------------------------------------------------
 * 2025-04-16       work        최초 생성
 * 2026-01-17       work        formatToDateTimeHM 입력값 포멧 맞추기
 * 2026-02-02       work        static 변수 제거
 */
public class TimeUtil {

    // 마이크로초(6자리)까지 대응 가능한 포맷터
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd HH:mm:ss")
                    .optionalStart()           // 소수점이 있을 수도 있고
                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                    .optionalEnd()             // 없을 수도 있음
                    .toFormatter();


    /**
     * @method addTimeFormat
     * @author work
     * @date 2025-04-16
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
     * @method formatToDateTimeHM
     * @author work
     * @date 2025-04-16
     * @deacription (형식 변환) "2025-05-01T23:59:59" => "2025-05-01 23:59"
     **/
    public static String formatToDateTimeHM(String isoDateTime) {
        if (isoDateTime == null || isoDateTime.isBlank())
            return "";
        try {
            // H2 등 DB에서 오는 포맷 "yyyy-MM-dd HH:mm:ss.SSSSSS" 등을 처리하기 위해 T로 변환
            String standardIso = isoDateTime.replace(" ", "T");
            LocalDateTime dt = LocalDateTime.parse(standardIso);
            return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            return isoDateTime; // 혹시 잘못된 형식이면 원본 리턴
        }
    }

    /**
     * @method isXYearAfter
     * @author work
     * @date 2025-04-18
     * @deacription {compareDateString}가 현재로부터 {year}년 이내인지 여부 체크
     **/
    public static boolean isXYearAfter(String compareDateString, int year) {
        if (compareDateString == null || compareDateString.isBlank()) return false;

        // 핵심: .132549 같은 나노초가 포함되어 있다면 "yyyy-MM-dd HH:mm:ss" 포맷에 맞춰 자름
        if (compareDateString.contains(".")) {
            compareDateString = compareDateString.split("\\.")[0];
        }

        try {
            LocalDateTime compareDate = LocalDateTime.parse(compareDateString, DATE_TIME_FORMATTER);
            // {TODAY}도 메서드 호출 시점의 시간을 새로 가져오는 것이 더 정확
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneYearAgo = now.minus(year, ChronoUnit.YEARS);
            return !compareDate.isBefore(oneYearAgo);
        } catch (DateTimeParseException e) {
            // 로그 기록 후 기본값 리턴 (방어적 코드)
            return false;
        }
    }

    public static boolean dateCompare(String futureDateString) {
        LocalDateTime futureDate = LocalDateTime.parse(futureDateString, DATE_TIME_FORMATTER);
        LocalDateTime today = LocalDateTime.now();
        return futureDate.isAfter(today);
    }

    public static String daysAfter(int after) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime futureDate = today.plusDays(after);
        return futureDate.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime daysAfter_Date(int after) {
        LocalDateTime today = LocalDateTime.now();
        return today.plusDays(after);
    }

}
