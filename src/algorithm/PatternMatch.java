package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/12/20
 * @联系邮箱 ********
 */
public class PatternMatch {

    public static void main(String[] args) {

        PatternMatch p = new PatternMatch();
        System.out.println(p.patternMatch("aabb","a b b a"));
    }

    /**
     *
     * @param pattern 模式串
     * @param str 需要匹配的串
     * @return
     */
    public boolean patternMatch(String pattern, String str){
        if(!Optional.ofNullable(pattern).isPresent() || !Optional.ofNullable(pattern).isPresent()){
            return false;
        }
        if(str.length() ==0 && pattern.length() ==0){
            return true;
        }
        if(str.length()==0){
            return false;
        }
        if(pattern.length()==0){
            return false;
        }
        List<String> stringList = Arrays.stream(str.split(" ")).distinct().collect(Collectors.toList());
        List<String> patternList  = new ArrayList<>();
        for (char c :pattern.toCharArray()){
            if (patternList.contains(String.valueOf(c))){
                continue;
            }
            patternList.add(String.valueOf(c));
        }
        if (patternList.size() != stringList.size()){
            return false;
        }
        for (int i = 0; i < patternList.size(); i++) {
            str = str.replace(stringList.get(i),patternList.get(i));
        }
        if(!pattern.equals(str.replace(" ",""))){
            return false;
        }
        return true;
    }
}
