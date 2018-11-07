package algorithm;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/9/29
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class AVLTree<T extends Comparable> {

    private Node<T> root;

    /**
     * 按照顺序打印树，即中序遍历
     */
    public void printByOrder(){
        printByOrder(root);
    }

    /**
     * 按顺序打印树（中序遍历）的实现例程
     * @param root
     */
    private void printByOrder(Node<T> root){
        if(root == null) {
            return;
        }
        System.out.print(root.value+",");
        printByOrder(root.left);
        printByOrder(root.right);
    }

    public void delete(T value){
        this.root = delete(this.root,value);
    }

    private Node<T> delete(Node<T> parent,T value){
        if(parent == null){
            return null;
        }

        System.out.println("删除进入到："+parent.value);

        //找到了插入的节点
        if(parent.value.compareTo(value) == 0){
            //左子树为空
            if(parent.left == null){
                return parent.right;
            }

            //右子树为空
            if(parent.right == null){
                return parent.left;
            }

            //现在左右子树都不为空，找到中序遍历最后的节点，然后删除该节点
            Node<T> maxLeftNode = parent.left;
            Node<T> newDeletedNode = maxLeftNode;
            while(maxLeftNode != null){
                maxLeftNode = maxLeftNode.right;
                if(maxLeftNode != null){
                    newDeletedNode = maxLeftNode;
                }
            }

            //找到了最新要删除的节点newDeletedNode，先将该节点的值赋给parent，然后删除最新要删除的节点
            parent.value = newDeletedNode.value;

            //递归删除最新的节点
            parent.left = delete(parent.left,newDeletedNode.value);

            return parent;
        }

        //如果父节点大于要删除的节点，进入到父节点的左子树
        if(parent.value.compareTo(value) > 0){

            parent.left = delete(parent.left,value);

        }else{//如果父节点小于要删除的节点，进入到父节点的右子树

            parent.right = delete(parent.right,value);

        }

        parent = rotate(parent);

        return parent;
    }

    public void insert(T value){
        //如果没有根节点
        if(root == null){
            root = new Node(value);
            return;
        }

        root = insertNode(root,value);
    }

    /**
     * 插入节点的实现例程
     * @param parent 父节点
     * @param value 需要插入的值
     * @return 返回插入完成的树
     */
    private Node<T> insertNode(Node<T> parent,T value){
        //如果父节点为空，说明已经到达底部，value新节点为叶子节点
        if(parent == null){
            Node<T> newNode = new Node<T>(value);
            return newNode;
        }

        if(parent.value.compareTo(value) == 0) {
            return parent;
        }
        if(parent.value.compareTo(value) > 0){
            parent.left = insertNode(parent.left,value);
        }else{
            parent.right = insertNode(parent.right,value);
        }

        //试着对树进行旋转
        parent = rotate(parent);

        return parent;
    }

    /**
     * 旋转树，根据情况进行左旋或者右旋
     * @param parent
     * @return 返回旋转后达到平衡的新的树
     */
    private Node<T> rotate(Node<T> parent){

        int leftHeight = parent.leftHeight();
        int rightHeight = parent.rightHeight();

        //左子树的高度比右子树高2
        if((leftHeight >= rightHeight && (leftHeight - rightHeight) >= 2)){
            Node<T> leftChild = parent.left;

            //左节点的左子树比右子树高，左左型，需要一次左旋
            if((leftChild.leftHeight() > leftChild.rightHeight())){

                parent = leftRotate(parent,leftChild);

            }else{//左右型，需要一次右旋和一次左旋

                parent.left = rightRotate(parent.left,parent.left.right);
                parent = leftRotate(parent,parent.left);
            }
            //右子树的高度比左子树高2
        }else if((rightHeight > leftHeight && (rightHeight - leftHeight) >=2 )){
            Node<T> rightChild = parent.right;

            //右节点的右子树比左子树高，右右型，需要一次右旋
            if(rightChild.rightHeight() > rightChild.leftHeight()){

                parent = rightRotate(parent,rightChild);

            }else{

                parent.right = leftRotate(parent.right,parent.right.left);
                parent = rightRotate(parent,parent.right);
            }
        }

        return parent;
    }

    private Node<T> leftRotate(Node<T> p1,Node<T> p2){
        Node<T> p2Right = p2.right;

        p2.right = p1;
        p1.left = p2Right;

        return p2;
    }

    private Node<T> rightRotate(Node<T> p1,Node<T> p2){
        Node<T> p2Left = p2.left;

        p2.left = p1;
        p1.right = p2Left;

        return p2;
    }

    private class Node<T extends Comparable>{
        //左子树
        private Node<T> left;

        //右子树
        private Node<T> right;

        //父节点
        private Node<T> parent;

        //数据
        private T value;

        public Node(T value){
            this.value = value;
        }

        public int leftHeight(){
            return calculateHeight(left);
        }

        public int rightHeight(){
            return calculateHeight(right);
        }

        /**
         * 计算该树的高度
         * @return
         */
        public int calculateHeight(){
            return calculateHeight(this);
        }

        /**
         * 计算树的高度
         * @param tree
         * @return
         */
        private int calculateHeight(Node<T> tree){
            if(tree == null){
                return -1;
            }

            int leftHeight = calculateHeight(tree.left);
            int rightHeight = calculateHeight(tree.right);

            return 1+(leftHeight > rightHeight?leftHeight:rightHeight);
        }
    }

    public static void main(String[] args){


        Integer[] testValues = {5,3,7,2,1,0,8,9,10,11,12,-2,-3};

        AVLTree<Integer> tree = new AVLTree<Integer>();
        for(Integer value:testValues){
            tree.insert(value);
        }
        tree.printByOrder();

    }
}
