package algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/11/29
 * @描述
 * @联系邮箱
 */
public class Deduplicate {

    public static void main(String[] args) {
        System.out.println(deduplicate(new char[]{'a', 'a','a','a','y','u','m','t'}));
    }


    /**
     * 去除重复的字符串，原地操作，不申请额外的数组空间
     * @param input
     * @return
     */
    public static char[] deduplicate(char[] input) {
        if (input == null || input.length == 0) {
            return null;
        }
        Set<Character> list = new HashSet<Character>();
        int current = 0;
        for (int next =0;next <input.length;){
            if(list.contains(input[next])){
                next++;
                continue;
            }
            input[current] = input[next];
            list.add(input[next]);
            next++;
            current++;
        }
        return String.valueOf(input).substring(0,current).toCharArray();
    }
}
