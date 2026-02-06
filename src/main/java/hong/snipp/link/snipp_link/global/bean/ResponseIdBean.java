package hong.snipp.link.snipp_link.global.bean;

import lombok.Getter;

import java.util.UUID;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean
 * fileName       : ResponseIdBean
 * author         : work
 * date           : 2025-04-25
 * description    : 응답 {mainId} 필드 담기용 빈
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 * 2026-02-06        work       {mainId} 동적 생성
 *                               - 기존에는 쿼리에서 받아왔으나, 해당 bean을 상속받으면 동적 생성됨
 */

@Getter
public class ResponseIdBean {

    private String mainId = UUID.randomUUID().toString();
}
