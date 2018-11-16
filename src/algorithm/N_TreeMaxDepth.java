package algorithm;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunpengwei
 * @创建时间 2018/11/15
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class N_TreeMaxDepth {
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    /**
     * 广度优先搜索
     */
    static class Solution {
        public int maxDepth(Node root) {
            if (root == null){
                return 0;
            }
            int depth  = 0;
            if (root.children != null) {
                for (Node node:root.children){
                    depth = Integer.max(depth,maxDepth(node));
                }
            }

            return depth+1;
        }
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString().equals(""));
        Solution solution = new Solution();
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node());
        Node node = new Node(10,nodeList);
        System.out.println(solution.maxDepth(node));
    }

}
