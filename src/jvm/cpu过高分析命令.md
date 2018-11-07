###
    top  命令查看那个进程最好时cpu，从高到底
    ps aux | grep pid   根基进程ID(pid)查看这个进程是什么
    ps -mp pid -o  THREAD,tid,time  查看这个进程下那个线程耗时cpu最长
    printf "%x\n" tid  把这个线程id转换为16进制
    jstack pid | grep tid -A 30 打印这个线程的堆栈信息，定位具体原因
    
 
    
### ps命令使用
    ps -ax (这个命令打印结果会很长，可以结合less使用)   
    ps -ax | less
    使用 -a 参数。-a 代表 all。同时加上x参数会显示没有控制终端的进程
    
    ps -u root (根据用户过滤进程)
    
    ps -aux | less (通过cpu和内存使用来过滤进程,未排序)
    
    ps -aux --sort -pcpu | less (根据cpu来排序)
    ps -aux --sort -pmem | less (根据内存来排序)
    
    ps -aux --sort -pcpu,+pmem | head -n 10 (根据cpu，内存排序，只显示前10行)
    
    
    
    
    
    