package drools;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/6/19
 * @描述 规则引擎对外服务接口
 * @联系邮箱 sunpengwei@jd.com
 */
public interface RuleEngineJsfService {

    /**
     *@描述 执行规则，没有返回结果
     *@参数
     *@返回值  void
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    void noResultExecuteRuleByParam(String serviceName, Object... facts);

    /**
     *@描述 执行规则，没有返回结果
     *@参数
     *@返回值  void
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    void noResultExecuteRuleByParamAndGlobal(String serviceName, String globalName,
                                             Object globalValue, Object... facts);


    /**
     *@描述 执行规则，有返回结果
     *@参数
     *@返回值  List<DefaultFactHandle>
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    Object hasResultExecuteRuleByParam(String serviceName, Object... facts);

    /**
     *@描述 执行规则，有返回结果
     *@参数
     *@返回值  Object
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    Object hasResultExecuteRuleByParamAndGlobal(String serviceName, String globalName,
                                                Object globalValue,
                                                Object... facts);
    /**
     *@描述 执行规则，没有返回结果，调用 noResultExecuteRuleByParam ，在子类实现时实现这个方法
     *@参数
     *@返回值  void
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    void noResultExecute();
    /**
     *@描述 执行规则，有返回结果，调用 hasResultExecute，在子类实现时实现这个方法
     *@参数
     *@返回值  void
     *@创建人  sunpengwei
     *@创建时间  2018/6/19
     *@邮箱  sunpengwei@jd.com
     */
    Object hasResultExecute();
}
