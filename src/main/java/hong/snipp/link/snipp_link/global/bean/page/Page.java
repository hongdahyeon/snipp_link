package hong.snipp.link.snipp_link.global.bean.page;

import lombok.Getter;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.page
 * fileName       : Page
 * author         : work
 * date           : 2025-04-15
 * description    : Page 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter
public class Page<B> {

    private int prev;          // 이전 페이지
    private int next;          // 다음 페이지
    private int size;          // 페이지 당 아이템 수
    private int pageNumber;    // 현재 페이지
    private int totalPages;    // 총 페이지 수
    private int countPage;     // 한 화면에 표시할 페이지 수
    private int totalElements; // 총 아이템 수
    private int startPage;     // 시작 페이지
    private int endPage;       // 끝 페이지
    private List<B> list;      // 현재 페이지의 데이터 리스트

    public Page(List<B> list, int totalElements, Pageable pageable) {
        this.list = list;
        this.size = pageable.getSize();
        this.totalElements = totalElements;
        this.pageNumber = pageable.getPageNumber();
        this.totalPages = (this.totalElements / this.size) + (this.totalElements % this.size == 0 ? 0 : 1);
        this.countPage = pageable.getCountPage();

        // startPage와 endPage 계산
        this.startPage = (pageable.getPageNumber() - 1) / countPage * countPage + 1;
        this.endPage = Math.min(this.startPage + countPage - 1, this.totalPages);

        // prev, next 계산
        this.prev = this.startPage - 1;
        if (this.prev < 1) {
            this.prev = 1;
        }

        this.next = this.startPage + countPage;
        if (this.next > this.totalPages) {
            this.next = this.totalPages;
        }
    }

}
