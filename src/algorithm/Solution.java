package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/13
 * @描述
 * @联系邮箱
 */
public class Solution {

    /**
     * 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。

     例如，给定一个 3叉树 :
        1
     3  2   4
   5  6

     返回其层序遍历:

     [
     [1],
     [3,2,4],
     [5,6]
     ]


     说明:

     树的深度不会超过 1000。
     树的节点总数不会超过 5000。
     * @param args
     */
    public static void main(String[] args) {

    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        order(root,list,0);
        return list;
    }

    public void order(Node root,List<List<Integer>> list,int level){
        if (root  == null){
            return;
        }
        if (list.size() <= level){
            list.add(new ArrayList<Integer>());
        }
        list.get(level).add(root.val);
        if (root.children != null){
            for (Node node : root.children){
                order(node,list,level+1);
            }
        }
    }

    /**
     * 求最大高度
     * @param root
     * @return
     */
    public int maxDepth(Node root) {
        if(root == null){
            return 0;
        }
        int depth = 0;
        for (Node node:root.children){
            depth = Integer.max(maxDepth(node),depth);
        }
        return depth+1;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
}
