package hong.snipp.link.snipp_link.global.bean.page;

import hong.snipp.link.snipp_link.global.util.ReflectorUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.page
 * fileName       : Pageable
 * author         : work
 * date           : 2025-04-15
 * description    : Page 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {

    private Integer countPage = 10;
    private Integer pageNumber = 1;
    private Integer size = 10;

    public Pageable(Integer size) {
        this.size = size;
    }

    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSize(Integer size){
        this.size = size;
    }

    public final Map<String, Object> generateMap(Object bean) {
        Map<String, Object> map = ReflectorUtil.getGetterMap(bean);
        map.put("startNumber", (this.pageNumber - 1) * this.size);      // 시작 번호
        map.put("endNumber", this.size);                                // 끝 번호
        return map;
    }

}
