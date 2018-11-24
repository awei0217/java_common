package cache;

import org.ehcache.*;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.MemoryUnit;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/4/9
 * @描述 ehcahce 配置类
 * @联系邮箱 sunpengwei@jd.com
 */
public class EhCacheConfig {
    /**
     *     ehcache 持久化的路径
     */
    private static final String EHCARCHE_PERSISTENT_PATH = "/export/ehcache/lbs-master/";

    private PersistentCacheManager persistentCacheManager;


    public PersistentCacheManager createPersistentCacheManager() {
        // 磁盘缓存暂不支持配置淘汰策略，满足1024M 时，默认按照LFU（最不常用）驱除规则将对应的元素进行删除
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(EHCARCHE_PERSISTENT_PATH))
                //lbs-master 代表存储的是 lbs-master主数据的缓存
                .withCache("lbs-master", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB, true)))
                .build(true); // 这个true 代表直接init，不用再显示的调用persistentCacheManager.init();
        this.persistentCacheManager = persistentCacheManager;

        return persistentCacheManager;
    }

    public org.ehcache.Cache createCache(PersistentCacheManager persistentCacheManager) throws CachePersistenceException {
        // 根据别名获取对应的缓存对象
        org.ehcache.Cache cache = persistentCacheManager.getCache("lbs-master",String.class, String.class);
        return cache;
    }

    public PersistentCacheManager getPersistentCacheManager() {
        return persistentCacheManager;
    }

    public void setPersistentCacheManager(PersistentCacheManager persistentCacheManager) {
        this.persistentCacheManager = persistentCacheManager;
    }

    public static void main(String[] args) throws CachePersistenceException {
        EhCacheConfig ehCacheConfig =  new EhCacheConfig();
        PersistentCacheManager persistentCacheManager = ehCacheConfig.createPersistentCacheManager();

        org.ehcache.Cache cache = ehCacheConfig.createCache(persistentCacheManager);
        long putStart = System.currentTimeMillis();
        for (int i = 0;i<2000000;i++) {
            cache.put("EBUTEST"+i,"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
        long putEnd = System.currentTimeMillis();
        System.out.println("put 100万 耗时:"+ (putEnd-putStart)/10000+" 秒");
        long getStart = System.currentTimeMillis();
        System.out.println("get EBUTEST300000:"+ cache.get("EBUTEST300000"));
        long getEnd = System.currentTimeMillis();
        System.out.println("get EBUTEST300000:"+ (getEnd-getStart)+" 毫秒");
    }
}
