package cache;

import org.mapdb.*;
import java.io.File;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/4/20
 * @描述
 * @联系邮箱 sunpengwei@jd.com
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

    /**
     *@描述 BTreeMap 采用b+树的数据结构缓存
     *@参数  [DBDisk]
     *@返回值  org.mapdb.BTreeMap<java.lang.String,java.lang.String>
     *@创建人  sunpengwei
     *@创建时间  com.jd.lbs.derive.config.cache
     *@邮箱  sunpengwei@jd.com
     */
    public BTreeMap<String,String> getBTreeMap (DB DBDisk){

        return (BTreeMap<String, String>) DBDisk.treeMap("btreemap")
                .valueSerializer(Serializer.STRING)
                .counterEnable()
                .createOrOpen();
    }


    // 测试，btreemap  在get 上比 htreemap 性能要好多。快40倍，在put 上稍微慢一点
    public static void main(String[] args) {
        MapDbConfig mapDbConfig = new MapDbConfig();
        DB db =mapDbConfig.initDB();
        BTreeMap<String,String> bTreeMap = mapDbConfig.getBTreeMap(db);

       /* long Trrstart = System.currentTimeMillis();
        for(int i = 0;i<1000000;i++){
            bTreeMap.put("EBUTESTB"+i,"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        }
        long Treeend = System.currentTimeMillis();
        System.out.println("mapdb put 100万数据耗时："+(Treeend-Trrstart)/1000 +" 秒");*/

        long getBStart = System.currentTimeMillis();
        System.out.println("get EBUTESTB105001 value"+bTreeMap.get("EBUTESTB105001"));
        long getBEnd= System.currentTimeMillis();
        System.out.println("get 中间一条数据耗时："+ (getBEnd-getBStart)+" 豪秒");

        // 还需要测试 并发性能
       /* for (int i = 0;i< 20;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for(int i = 0;i<200;i++){
                            bTreeMap.put("EBUTESTB"+i,String.valueOf(RandomUtils.nextLong()));
                        }
                    }
                }
            }).start();
        }

        while (true){
            System.out.println("get EBUTESTB100 value: "+bTreeMap.get("EBUTESTB100"));
        }*/

    }
}
