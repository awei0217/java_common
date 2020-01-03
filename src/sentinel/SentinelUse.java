package sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunpengwei
 * @description sentinel 使用
 * @date 2019/12/20 15:17
 */
public class SentinelUse {


    //资源
    private static String resource = "resource";

    public static void main(String[] args) {

        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(resource);
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        for (int i=0;i<100;i++){
            Entry entry = null;
            // 务必保证finally会被执行
            try {
                // 资源名可使用任意有业务语义的字符串
                entry = SphU.entry(resource, EntryType.IN);
                // 被保护的业务逻辑
                // do something...
            } catch (BlockException e1) {
                System.out.println("e1");
                // 资源访问阻止，被限流或被降级
                // 进行相应的处理操作
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }


    }



    public int lengthOfLastWord(String s) {
        if (s == null || s.length()==0){
            return 0;
        }
        String temp = s.trim();
        char[] chars = temp.toCharArray();
        int t = 0;
        for (int i=chars.length;i>=0;i--){
            if (chars[i] == ' '){
                t = i-1;
                break;
            }
        }
        return chars.length-t ;

    }


}
