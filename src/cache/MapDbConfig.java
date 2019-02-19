package cache;

import org.mapdb.*;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/4/20
 * @描述
 * @联系邮箱
 */
public class MapDbConfig {

    /**
     * mapdb  磁盘持久化路径
     */
    private static final String MAPDB_PERSISTENT_PATH = "/export/mapdb/lbs-master/lbs-master.db";

    /**
     *   初始化mapDB 磁盘缓存数据库
     */
    public DB initDB(){
        File file = new File(MAPDB_PERSISTENT_PATH);
        // 初始化文件夹路径
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
       return DBMaker.fileDB(file)
               .closeOnJvmShutdown()
               .fileMmapEnableIfSupported()
               .fileMmapPreclearDisable()
               .cleanerHackEnable()
               .checksumHeaderBypass()
               .concurrencyScale(16)
               .closeOnJvmShutdown()
               .make();
    }

    public DB initOffHeapDB(){
        return DBMaker.memoryDirectDB()
              .make();
    }

    /**
     *@描述 BTreeMap 采用b+树的数据结构缓存
     *@参数  [DBDisk]
     *@返回值  org.mapdb.BTreeMap<java.lang.String,java.lang.String>
     *@创建人  sunpengwei
     *@创建时间  com.jd.lbs.derive.config.cache
     *@邮箱
     */
    public BTreeMap<String,String> getBTreeMap (DB DBDisk){

        return (BTreeMap<String, String>) DBDisk.treeMap("btreemap")
                .valueSerializer(Serializer.STRING)
                .counterEnable()
                .createOrOpen();
    }

    public HTreeMap<String,String> getHTreeMap (DB DBDisk){

        return (HTreeMap<String, String>) DBDisk.hashMap("btreemap")
                .valueSerializer(Serializer.STRING)
                .counterEnable()
                .createOrOpen();
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MapDbConfig mapDbConfig = new MapDbConfig();
        DB db =mapDbConfig.initOffHeapDB(); //611538
        BTreeMap<String,String> bTreeMap = mapDbConfig.getBTreeMap(db);

        for (int i=1;i< 50000;i++){
            bTreeMap.put("EMG0123456789"+i,"EMG0123456789"+i);
        }
        long start = System.nanoTime(); //230060354
                                        //1379608180
        for (int i=1;i< 50000;i++){
            System.out.println(bTreeMap.get("EMG0123456789"+i));
        }
        System.out.println(System.nanoTime()-start);
        countDownLatch.await();



    }
}
