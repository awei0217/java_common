package algorithm;

import java.util.Collections;
import java.util.Random;

/**
 * @author sunpengwei
 * @创建时间 2018/10/30
 * @描述
 * @联系邮箱
 */
public class SkipList {

    private Node head = new Node();
    /**
     * 跳跃列表最大层
     */
    private static final int LEVEL = 32;
    /**
     * 一个随机数用来获取插入元素的层
     */
    private Random random = new Random();

    private int maxLevel;

    /**
     * 跳跃列表的元素的个数
     */
    private int count;

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        for (int i =1;i<100;i++){
            skipList.insert(i);
            if (i % 5 ==0){
                skipList.delete(i);
            }
        }
        System.out.println(skipList.find(4));
        System.out.println(skipList.find(5));
    }

    public Node find(int value){
        Node p = head;
        for (int i= LEVEL-1;i>=0;i--){
            while (p.forward[i] != null && p.forward[i].getValue() < value){
                p = p.forward[i];
            }
        }
        if (p.forward[0] != null && p.forward[0].getValue() == value){
            return p.forward[0];
        }
        return null;
    }

    public void insert(int value){
        //获取当前元素应该所在的层
        int level = getLevel();
        Node current = new Node(value);
        //设置当前节点的最大层
        current.setCurrentLevel(level);
        Node [] update = new Node[level];
        for(int i =0;i<update.length;i++){
            update[i] = head;
        }
        Node p = head;
        for (int i = level-1;i>=0;i--){
            while(p.forward[i] != null && p.forward[i].getValue() < value){
                p = p.forward[i];
            }
            update[i] = p;
        }
        for (int i =0;i<level;i++){
            current.forward[i] = update[i].forward[i];
            update[i].forward[i] = current;
        }
        if (level > maxLevel){
            maxLevel = level;
        }
        count++;
    }

    public void delete(int value){
        Node del = find(value);
        if (del == null){
            return;
        }
        int level = del.getCurrentLevel();
        Node [] update = new Node[level];
        Node p = head;
        for  (int i=level-1;i>=0;i--){
            while (p.forward[i] != del){
                p = p.forward[i];
            }
            update[i] = p;
        }
        for (int i=0;i<level;i++){
            update[i].forward[i] =del.forward[i];
        }
        count--;
    }


    private int getLevel(){
        int level = 1;
        for (int i=0;i<LEVEL;i++){
            /**
             * 百分之50的概率上升一层
             */
            if (random.nextInt() % 2 ==0){
                level++;
            }
        }
        return level;
    }

    public int getCount() {
        return count;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    class Node{
        private int value;

        private Node [] forward = new Node[LEVEL];

        private int currentLevel;

        public Node(){

        }
        public Node(int value){
            this.value =value;
        }
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getCurrentLevel() {
            return currentLevel;
        }

        public void setCurrentLevel(int currentLevel) {
            this.currentLevel = currentLevel;
        }
    }
}
