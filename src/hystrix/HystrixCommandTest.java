package hystrix;

import com.netflix.hystrix.*;

import java.util.Random;

/**
 * @author sunpengwei
 * @description Hystrix测试
 * @date 2019/12/30 10:28
 */
public class HystrixCommandTest extends HystrixCommand<Integer> {

    private final String name;

    /**
     * 业务逻辑处理
     * @return
     * @throws Exception
     */
    @Override
    protected Integer run() throws Exception {
        Random rand = new Random();
        //模拟错误百分比
        if (1 == rand.nextInt(2)) {
            throw new Exception("exception");
        }
        return 1;
    }

    /**
     * 降级处理
     * @return
     */
    @Override
    protected Integer getFallback() {
        return -1;
    }

    public HystrixCommandTest (String name){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties
                                 .Setter()
                                //至少有10个请求，熔断器才进行错误率的计算
                                .withCircuitBreakerRequestVolumeThreshold(10)
                                //熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                                //错误率达到1%开启熔断保护
                                .withCircuitBreakerErrorThresholdPercentage(1)
                                .withExecutionTimeoutEnabled(true)));
        this.name = name;
    }



    public static void main(String[] args) {

        for (int i = 0;i<200;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            HystrixCommandTest hystrixCommandTest = new HystrixCommandTest("helloWorld");
            Integer result  = hystrixCommandTest.execute();
            System.out.println(result+".."+hystrixCommandTest.isCircuitBreakerOpen());

        }
    }
}
