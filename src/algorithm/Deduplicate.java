package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for(String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            if(!map.containsKey(key)) map.put(key, new ArrayList<>());
            map.get(key).add(s);
        }
        for(String key : map.keySet()) {
            List<String> list = map.get(key);
            Collections.sort(list);
            result.add(list);
        }
        return result;
    }
}
