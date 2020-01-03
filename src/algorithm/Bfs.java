package algorithm;

import java.util.LinkedList;

/**
 * @创建人 sunpengwei
 * @创建时间 2019/1/4
 * @描述
 * @联系邮箱
 */
public class Bfs {

    public static void main(String[] args) {

        isHappy(19);
        Vertex[] vertices = new Vertex[9];
        for(int i=0;i<vertices.length;i++){
            vertices[i]=  new Vertex(i);
        }
        vertices[0].addNeighbors(vertices[1],vertices[3]);
        vertices[1].addNeighbors(vertices[8],vertices[2]);
        vertices[2].addNeighbors(vertices[3],vertices[4]);
        vertices[3].addNeighbors(vertices[1],vertices[4]);
        vertices[4].addNeighbors(vertices[4],vertices[5]);
        vertices[5].addNeighbors(vertices[1],vertices[3],vertices[4],vertices[5]);
        vertices[6].addNeighbors(vertices[5],vertices[6],vertices[2]);
        vertices[7].addNeighbors(vertices[1],vertices[7],vertices[8]);
        vertices[8].addNeighbors(vertices[6],vertices[4]);
        Vertex start = vertices[0];
        Vertex end = vertices[8];
        Bfs bfs = new Bfs();
        boolean  b = bfs.bfs(start,end);
        if (b){
            Vertex p = end;
            do {
                System.out.println(p.getId());
                p = p.getPre();
            }while (p != null);
        }else{
            System.out.println("不存在A到B的路京");
        }
    }

    /**
     * 是否存在从定点A到定点B的路径
     * @param start
     * @param end
     * @return
     */
    public boolean bfs(Vertex start,Vertex end){
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()){
            //从队首取出一个元素
            Vertex v = queue.pollFirst();
            if (v.isSearch()){
                continue;
            }
            if (v == end){
                return true;
            }
            for (Vertex temp : v.getNeighbors()){
                if (temp.getPre() == null && temp != start){
                    temp.setPre(v);
                }
                queue.addLast(temp);
            }
            v.setSearch(true);
        }
        return false;
    }

    public static boolean isHappy(int n) {
        if (n==1){
            return true;
        }

        String s = String.valueOf(n);
        int result = 0;
        for (char c : s.toCharArray()){
            int num = Integer.parseInt(String.valueOf(c));
            result = result+num * num;
        }
       return isHappy(result);
    }
}

class Vertex{

    /**
     * 这个顶点的标识
     */
    private int id;

    /**
     * 这个定点的邻居定点
     */
    private LinkedList<Vertex> neighbors;
    /**
     * 这个定点是否被搜索过
     */
    private boolean isSearch;

    /**
     * 这个定点的前驱
     */
    private Vertex pre;

    public Vertex(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<Vertex> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public Vertex getPre() {
        return pre;
    }

    public void setPre(Vertex pre) {
        this.pre = pre;
    }

    public void addNeighbors(Vertex ...vertexs){
        LinkedList<Vertex> vertexLinkedList = new LinkedList<>();
        for (Vertex vertex : vertexs){
            vertexLinkedList.add(vertex);
        }
        this.neighbors = vertexLinkedList;
    }
}


