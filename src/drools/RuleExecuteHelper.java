package drools;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.drools.core.common.DefaultFactHandle;
import tools.ApplicationContextUtils;

import java.util.*;

public class RuleExecuteHelper {


    public static RuleConfiguration ruleConfiguration;

    static {
        ruleConfiguration = ApplicationContextUtils.getBean("ruleConfiguration");
    }


    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称， facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(String serviceName, Object... facts) {

        RuleEngine engine = RuleConfiguration.ruleEngineMap.get(serviceName);

        return engine.execute(RuleCommand.generate().accept(facts));
    }

    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称，globalName 可以理解为jsf服务的Id， globalValue 可以理解为jsf服务  facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(String serviceName,String globalName,
                                                  Object globalValue, Object... facts) {
        if (StringUtils.isBlank(globalName) || !Optional.ofNullable(globalValue).isPresent()){
            throw new RuntimeException("globalName is not null and globalValue is not null");
        }
        RuleEngine engine = RuleConfiguration.ruleEngineMap.get(serviceName);

        return engine.execute(RuleCommand.generate().global(globalName,globalValue).accept(facts));
    }
    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称，globalMap 传入多个global key 为global的名称，value 为global的值  facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(String serviceName,Map<String,Object> globalMap, Object... facts) {

        RuleEngine engine = RuleConfiguration.ruleEngineMap.get(serviceName);
        RuleCommand ruleCommand =RuleCommand.generate();
        if (globalMap != null  && globalMap.size() > 0 ) {
            for (Map.Entry entry : globalMap.entrySet()){
                ruleCommand.global(entry.getKey().toString(),entry.getValue());
            }
        }
        ruleCommand.accept(facts);
        return  engine.execute(ruleCommand);
    }

    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称，globalMap 传入多个global key 为global的名称，value 为global的值  facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(String serviceName,Map<String,Object> globalMap,List<Object> facts) {

        return execute(serviceName,globalMap,facts.toArray());
    }

    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称， facts 需要执行规则的对象
     *@返回值  Object
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(String serviceName,
                                                   String globalName,
                                                   Object globalValue,
                                                   List<Object> facts) {

        return execute(serviceName,globalName,globalValue,facts.toArray());
    }
    /**
     *@描述 真正的执行规则（执行单个规则文件）
     *@参数 serviceName 规则文件package的名称， facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static  Object execute(String serviceName, List<Object> facts) {

        return execute(serviceName,facts.toArray());
    }


    /**
     *@描述 执行多个规则文件 （按list的顺序执行）
     *@参数 serviceNameList 规则文件package的名称(多个)， facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static  Object execute(List<String> serviceNameList, List<Object> facts) {
        if(CollectionUtils.isEmpty(facts)){
            throw new RuntimeException("facts is not null");
        }
        return execute(serviceNameList,facts.toArray());
    }

    /**
     *@描述 执行多个规则文件 （按list的顺序执行）
     *@参数 serviceNameList 规则文件package的名称(多个)， facts 需要执行规则的对象
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱
     */
    public static Object execute(List<String> serviceNameList,Object ... facts) {
        if(CollectionUtils.isEmpty(serviceNameList)){
            throw new RuntimeException("serviceNameList is not null");
        }
        List<DefaultFactHandle> factHandles = null;
        for (String serviceName : serviceNameList) {
            RuleEngine engine = RuleConfiguration.ruleEngineMap.get(serviceName);
            if(factHandles == null){
                factHandles = engine.execute(RuleCommand.generate().accept(facts));
            }else{
                List<Object> objects = new ArrayList<>();
                for (DefaultFactHandle defaultFactHandle : factHandles){
                    if(defaultFactHandle.getObject() instanceof Collection){
                        objects.addAll((Collection<?>) defaultFactHandle.getObject());
                    }else{
                        objects.add(defaultFactHandle.getObject());
                    }
                }
                factHandles = engine.execute(RuleCommand.generate().accept(objects));
            }
        }
        return factHandles;
    }
}
