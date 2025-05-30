package hong.snipp.link.snipp_link.domain.bbscl.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.dto.response
 * fileName       : SnippBbsClTree
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsClTree {

    private Long no;
    private Long upperNo;
    private String text;
    private Long sortNo;
    private Long bbsUid;
    private String bbsNm;
    private String path;
    private String depth;
    private List<SnippBbsClTree> children;


    public static List<SnippBbsClTree> buildTree(List<SnippBbsClList> flatList) {
        Map<Long, SnippBbsClTree> nodeMap = new HashMap<>();
        List<SnippBbsClTree> roots = new ArrayList<>();

        // 1. flat list -> map 구조로 변환
        for (SnippBbsClList item : flatList) {
            SnippBbsClTree node = new SnippBbsClTree();
            node.no = item.getNo();
            node.upperNo = item.getUpperNo();
            node.text = item.getText();
            node.sortNo = item.getSortNo();
            node.bbsUid = item.getBbsUid();
            node.bbsNm = item.getBbsNm();
            node.path = item.getPath();
            node.depth = item.getDepth();
            node.children = new ArrayList<>();
            nodeMap.put(node.no, node);
        }

        // 2. 트리 구조 생성
        for (SnippBbsClTree node : nodeMap.values()) {
            if (node.upperNo == null) {
                roots.add(node); // 루트 노드
            } else {
                SnippBbsClTree parent = nodeMap.get(node.upperNo);
                if (parent != null) {
                    parent.children.add(node); // 자식 노드 추가
                }
            }
        }

        return roots;
    }

}
