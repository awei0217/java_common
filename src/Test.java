/**
 * @author sunpengwei
 * @description TODO
 * @date 2020/1/9 15:15
 */
public class Test {

    public static void main(String[] args) {
        int num = 100000000;
        long start = System.nanoTime();
        for (int i = 0; i < num; i++) {
            int[] temp = {1, 2, i-1, 4, 5, 6, 7, 8, 9};
            bubbleSort(temp); //3308123800
        }
        System.out.println((System.nanoTime() - start));
    }
    //3379482300
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
