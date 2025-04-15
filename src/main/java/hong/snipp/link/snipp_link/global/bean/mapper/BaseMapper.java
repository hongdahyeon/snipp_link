package hong.snipp.link.snipp_link.global.bean.mapper;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.mapper
 * fileName       : BaseMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 기본 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
public interface BaseMapper <T> {

    /**
     * @method      page
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <B>
     *              (출력) List<R>
    **/
    <B, R> List<R> page(B param);

    /**
     * @method      list
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <B>
     *              (출력) List<R>
    **/
    <B, R> List<R> list(B param);

    /**
     * @method      list
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) Long uid
     *              (출력) List<R>
    **/
    <R> List<R> list(Long uid);

    /**
     * @method      count
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <B>
     *              (출력) int
    **/
    <B> int count(B param);

    /**
     * @method      insert
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <T> bean
     *              (출력) int
    **/
    int insert(T bean);

    /**
     * @method      update
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <T> bean
     *              (출력) int
    **/
    int update(T bean);

    /**
     * @method      delete
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) Long uid
     *              (출력) int
    **/
    int delete(Long uid);

    /**
     * @method      delete
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <T> bean
     *              (출력) int
    **/
    int delete(T bean);

    /**
     * @method      view
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) Long uid
     *              (출력) <T> bean
    **/
    T view(Long uid);

    /**
     * @method      view
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) String
     *              (출력) <T> bean
    **/
    T view(String key);

    /**
     * @method      view
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) <T> bean
     *              (출력) <T> bean
    **/
    T view(T bean);

    /**
     * @method      getDetail
     * @author      work
     * @date        2025-04-15
     * @deacription (입력) Long uid
     *              (출력) <R>
    **/
    <R> R getDetail(Long uid);

}
