package algorithm;

import java.util.*;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/20
 * @描述
 * @联系邮箱 ********@jd.com
 */
public class TreeTest {

    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();
        Node root = new Node();
        root.data = "a";
        Node left = new Node();
        left.data = "b";
        root.left  = left;
        Node right = new Node();
        right.data ="c";
        root.right = right;

        List<String> list = treeTest.treeLayerErgodic(root);
        list.forEach( s -> System.out.print(s));
    }

    public List<String> treeLayerErgodic(Node node){
        List<String> list  = new ArrayList<>();
        if (!Optional.ofNullable(node).isPresent()){
            return list;
        }
        Queue<Node> queue = new ArrayDeque<Node>();
        queue.add(node);
        while (!queue.isEmpty()){
            Node temp = queue.poll();
            list.add(temp.data);
            if (temp.left != null){
                queue.add(temp.left);
            }
            if (temp.right != null){
                queue.add(temp.right);
            }
        }
        return list;
    }

    static class Node {
        public String data;
        public Node left;
        public Node right;
    }
}
