package algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sunpengwei
 * @创建时间 2018/11/29
 * @描述
 * @联系邮箱
 *
 *  求最长不重复子串
 *
 */
public class MaxLenNoRepareSubStr {

    public static void main(String[] args) {
        System.out.println(new MaxLenNoRepareSubStr().lengthOfLongestSubstring(""));
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++){
            for (int j = i + 1; j <= n; j++){
                if (allUnique(s, i, j)){
                    ans = Math.max(ans, j - i);
                }else{
                    break;
                }
            }
        }
        return ans;
    }

    public boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if (set.contains(ch)){
                return false;
            }
            set.add(ch);
        }
        return true;
    }
}
