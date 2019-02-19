package drools;


import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.springframework.beans.factory.InitializingBean;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/6/19
 * @描述 规则引擎配置类，引入lbs-common报的需要把RuleConfiguration对象交给spring容器管理
 * @联系邮箱
 */

/** 使用方法
 <bean id="ruleConfiguration" class="com.jd.lbs.common.rule.engine.RuleConfiguration">
     <property name="ruleMap">
         <map>
             <entry key="serviceName" value-ref="ruleFileName"/>
         </map>
     </property>
 </bean>
 * @author sunpengwei
 */
public class RuleConfiguration implements InitializingBean {



    /**
     * 规则文件配置 key 为规则文件的 package 名称(也就是serviceName)，value 为规则文件的名称,带后缀
     */
    public Map<String,String> ruleMap;

    /**
     * 规则引擎缓存map
     */
    public static Map<String,RuleEngine> ruleEngineMap = new HashMap<String, RuleEngine>();

    public static ReleaseId releaseId;

    public static RuleDynamic ruleDynamic;



    /**
     *@描述 根据规则文件的package名称初始化规则引擎
     *@参数
     *@返回值  void
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(ruleMap == null){
            throw new RuntimeException("ruleMap is not null");
        }
        releaseId = KieServices.Factory.get().getRepository().getDefaultReleaseId();
        ruleDynamic = RuleDynamic.Facotry.get();
        for (Map.Entry entry : ruleMap.entrySet()){
            try {
                String serviceName = entry.getKey().toString();
                String fileName = entry.getValue().toString();
                String ruleName = entry.getValue().toString().split("\\.")[0];
                Rules rules = new Rules(RuleEntity.generate(serviceName,ruleName,fileName));
                getRuleDynamic().refresh(getReleaseId(),rules.getResources(), rules.getModuleContent());
                RuleEngine engine = RuleEngine.generate(serviceName);
                ruleEngineMap.put(serviceName,engine);
            }catch (Exception e){
                throw new RuntimeException("加载规则文件异常",e);
            }
        }
    }

    public static Map<String, RuleEngine> getRuleEngineMap() {
        return ruleEngineMap;
    }

    public static void setRuleEngineMap(Map<String, RuleEngine> ruleEngineMap) {
        RuleConfiguration.ruleEngineMap = ruleEngineMap;
    }

    public static ReleaseId getReleaseId() {
        return releaseId;
    }

    public static void setReleaseId(ReleaseId releaseId) {
        RuleConfiguration.releaseId = releaseId;
    }

    public static RuleDynamic getRuleDynamic() {
        return ruleDynamic;
    }

    public static void setRuleDynamic(RuleDynamic ruleDynamic) {
        RuleConfiguration.ruleDynamic = ruleDynamic;
    }

    public Map<String, String> getRuleMap() {
        return ruleMap;
    }

    public void setRuleMap(Map<String, String> ruleMap) {
        this.ruleMap = ruleMap;
    }
}
