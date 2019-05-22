/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package algorithm;

import java.util.Optional;

/**
 *
 * @author pengwei.sun
 * @version $Id: StringUtils.java, v 0.1 2019年04月23日 9:02 PM pengwei.sun Exp $
 */
public class StringUtils {

    public static void main(String[] args) {
        /*l1 := len(str1)
        l2 := len(str2)
        max := 0
        end := 0

        var twoArray [][]int
        for i := 0; i < l1+1; i++ {
            tmp := make([]int, l2+1)
            twoArray = append(twoArray, tmp)
        }
        for i := 1; i <= l1; i++ {
            for j := 1; j <= l2; j++ {
                if str1[i-1] == str2[j-1] {
                    twoArray[i][j] = twoArray[i-1][j-1] + 1
                    if twoArray[i][j] > max {
                        max = twoArray[i][j]
                        end = j
                    }
                } else {
                    twoArray[i][j] = 0
                }
            }
        }
        bytes := make([]byte, 0)
        for m := end - max; m < end; m++ {
            bytes = append(bytes, str2[m])
        }
        return string(bytes)*/

        StringUtils stringUtils = new StringUtils();

        System.out.println(stringUtils.findMaxLenCommonSubStr("abcderm","cdert"));
    }

    /**
     * 求最长公共子川
     * 利用二维数组(动态规划)
     * @param s1
     * @param s2
     * @return
     */
    public String findMaxLenCommonSubStr(String s1,String s2) {
        if (!Optional.ofNullable(s1).isPresent() || !Optional.ofNullable(s2).isPresent()){
            return null;
        }
        if(s1.length() ==0 || s2.length() ==0){
            return null;
        }

        int max = 0;
        int end = 0;
        int [][] twoArray = new int[s1.length()+1][s2.length()+1];

        for (int i =1;i<s1.length();i++){
            for (int j=1;j<s2.length();j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    twoArray[i][j] = twoArray[i-1][j-1]+1;
                    if(twoArray[i][j] > max){
                        max = twoArray[i][j];
                        end = j;
                    }
                }else{
                    twoArray[i][j] = 0;
                }
            }
        }

       return s2.substring(end-max,end+1);
        //   a   b   c   d   e   r   t
        //c  0   0   1   0   0   0   0

        //d  0   0   0   1   0   0   0

        //e  0   0   0   0   1   0   0

        //r  0   0   0   0   0   1   0

        //t  0   0   0   0   0   0   0

        //值为1的就是最长公共字串了
    }
}