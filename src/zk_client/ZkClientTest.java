package zk_client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * curator-framework：对zookeeper的底层api的一些封装
 * curator-client：提供一些客户端的操作，例如重试策略等
 * curator-recipes：封装了一些高级特性，如：Cache事件监听、选举、分布式锁、分布式计数器、分布式Barrier等
 */
public class ZkClientTest {

    public static void main(String[] args) throws Exception {


        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.newClient(
                        "127.0.0.1:2181",
                        5000,
                        3000,
                        retryPolicy);

        client.start();
        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
        zooKeeper.create("/bs",null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> list = zooKeeper.getChildren("/bs",false);

        /**
         * leader
         * servers
         * instances
         * config
         * sharding
         */
        for (String s : list){
            System.out.println(s);
        }
        byte[] bytes = zooKeeper.getData("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob",false,null);
        System.out.println("config:"+new String(bytes, "utf-8"));

        List<String>  insList= zooKeeper.getChildren("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader",false,null);
        for (String s : insList){
            System.out.println(s);
        }

        List<String> leader = zooKeeper.getChildren("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader",false,null);
        /**
         * election
         * sharding
         */
        for (String s : leader){
            System.out.println(s);
        }

        byte[] bytes3 = zooKeeper.getData("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader/sharding",false,null);
        System.out.println("leader/sharding:"+new String(bytes3, "utf-8"));


        List<String> leaderElection = zooKeeper.getChildren("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader/election",false);
        /**
         * latch
         * instance
         */
        for (String s : leaderElection){
            System.out.println(s);
        }
        zooKeeper.delete("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/sharding/2/disabled",0);
        byte[] bytes1 = zooKeeper.getData("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader/election/latch",false,null);
        System.out.println("leader/election/latch:"+new String(bytes1, "utf-8"));
        byte[] bytes2 = zooKeeper.getData("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader/election/instance",false,null);
        System.out.println("leader/election/instance:"+new String(bytes2, "utf-8"));





        List<String> leaderSharding = zooKeeper.getChildren("/bs/cn.com.gome.cs.web.jobs.ThreeCReplenishmentDataFlowJob/leader/sharding",false);
        /**
         * latch
         * instance
         */
        for (String s : leaderSharding){
            System.out.println(s);
        }

        List<String>  shardingList= zooKeeper.getChildren("/bs/com.dangdang.ddframe.job.example.job.simple.SpringSimpleJob/sharding",false,null);
        for (String s : shardingList){
            System.out.println(s);
        }
        List<String>  sharding0List= zooKeeper.getChildren("/bs/com.dangdang.ddframe.job.example.job.simple.SpringSimpleJob/sharding/0",false,null);
        for (String s : sharding0List){
            System.out.println(s);
        }




    }
}
