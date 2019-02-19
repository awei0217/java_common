### 1.8

    -Xms6G -Xmx6G -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+CMSScavengeBeforeRemark -XX:+CMSParallelRemarkEnabled -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause -Xloggc:/export/Logs/jvm/gc.log

 

### 1.6

    -Xms6G -Xmx6G -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:+CMSScavengeBeforeRemark -XX:+CMSParallelRemarkEnabled -XX:PermSize=512m -XX:MaxPermSize=512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause -Xloggc:/export/Logs/jvm/gc.log
    
    
    
    
    
    