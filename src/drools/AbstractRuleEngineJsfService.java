package drools;


/**
 * @创建人 sunpengwei
 * @创建时间 2018/6/19
 * @描述 规则引擎对外服务接口抽象类，对外发布jsf服务时，自定义的规则服务类可以继承这个抽象类调用里面的方法
 * @联系邮箱
 */
public abstract class AbstractRuleEngineJsfService implements RuleEngineJsfService {

    @Override
    public void noResultExecuteRuleByParam(String serviceName,Object... facts) {
        RuleExecuteHelper.execute(serviceName,facts);
    }

    @Override
    public void noResultExecuteRuleByParamAndGlobal(String serviceName,
                                                    String globalName,Object globalValue,
                                                    Object... facts) {

        RuleExecuteHelper.execute(serviceName,globalName,globalValue,facts);
    }

    @Override
    public Object hasResultExecuteRuleByParam(String serviceName,Object... facts) {
        return RuleExecuteHelper.execute(serviceName,facts);
    }

    @Override
    public Object hasResultExecuteRuleByParamAndGlobal(String serviceName,
                                                                        String globalName,Object globalValue,
                                                                        Object... facts) {
        return RuleExecuteHelper.execute(serviceName,globalName,globalValue,facts);
    }


}
