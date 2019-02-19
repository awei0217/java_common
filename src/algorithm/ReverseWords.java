package algorithm;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/2
 * @描述
 * @联系邮箱
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反转字符串中的每个单词
 */
public class ReverseWords {

    /**
     *
     给定一个字符串，逐个翻转字符串中的每个单词。

     示例:

     输入: "the sky is blue",
     输出: "blue is sky the".
     说明:
     无空格字符构成一个单词。
     输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     进阶: 请选用C语言的用户尝试使用 O(1) 空间复杂度的原地解法。
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new ReverseWords().reverseWords("the   sky is blue"));
    }

    public String reverseWords(String s) {
        if (s ==null || s.length()==0){
            return s;
        }
        List<String> l  = Arrays.stream(s.split(" ")).filter(temp -> !temp.equals("")).collect(Collectors.toList());

        Object [] arrays = l.toArray();
        for (int i=0;i< arrays.length / 2;i++){
            String temp = (String) arrays[arrays.length-i-1];
            arrays[arrays.length-i-1] = arrays[i];
            arrays[i] = temp;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : arrays){
            stringBuilder = stringBuilder.append(o).append(" ");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }
}
