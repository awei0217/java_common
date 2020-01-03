
## 事务

### mysql事务特性
    
    A: atomicity.   原子性    一次事务操作中要么全部成功，要么全部失败,不可能停留在中间状态
    C: consistency. 一致性    事务的一致性定义基本可以理解为是事务对数据完整性约束的遵循，事务执行的前后都是合法的数据状态，不会违背任何的数据完整性
    I:: isolation.  隔离性    不同事务操作相互隔离，互不影响 
    D: durability.  持久性    事务完成后,对数据的更改是永久性的 
    
### mysql事务级别    

    从最高的一致性和到最低，InnoDB支持的隔离级别为： SERIALIZABLE， REPEATABLE READ， READ COMMITTED和 READ UNCOMMITTED
    
    SERIALIZABLE 完全串行，并发能力最差
    
    REPEATABLE READ 可重复读(RR) 默认隔离级别，这种级别下，防止任何的查询会被其它事务更改，这种隔离级别会出现幻读，意思是A事务第一次查询结果是A，B事务添加了一条数据并提交，A事务第二次查询结果还是A(解决了不可重复读，靠MVCC解决)，但是A事务第三次更新时会发现B添加的数据。
         MySQL官网描述
         he default isolation level for InnoDB. It prevents any rows that are queried from being changed by other transactions, 
         thus blocking non-repeatable reads but not phantom reads. 
         It uses a moderately strict locking strategy so that all queries within a transaction see data from the same snapshot, 
         that is, the data as it was at the time the transaction started
         
         When a transaction with this isolation level performs UPDATE ... WHERE, DELETE ... WHERE, SELECT ... FOR UPDATE, and LOCK IN SHARE MODE operations,
         other transactions might have to wait.
         SELECT ... FOR SHARE replaces SELECT ... LOCK IN SHARE MODE in MySQL 8.0.1, but LOCK IN SHARE MODE remains available for backward compatibility.
         
    READ COMMITTED 不可重复读(RC) 这种级别在重复读时会出现不同结果，比如事务A第一次读出A，此时事务B提交，事务A再次读时会读出AB，在一个事务A中，两次读看到的结果不一致
         MySQL官网描述
         An isolation level that uses a locking strategy that relaxes some of the protection between transactions, 
         in the interest of performance. Transactions cannot see uncommitted data from other transactions, 
         but they can see data that is committed by another transaction after the current transaction started. 
         Thus, a transaction never sees any bad data, but the data that it does see may depend to some extent on the timing of other transactions
         
         When a transaction with this isolation level performs UPDATE ... WHERE or DELETE ... WHERE operations, other transactions might have to wait. 
         The transaction can perform SELECT ... FOR UPDATE, and LOCK IN SHARE MODE operations without making other transactions wait.
         SELECT ... FOR SHARE replaces SELECT ... LOCK IN SHARE MODE in MySQL 8.0.1, but LOCK IN SHARE MODE remains available for backward compatibility
         
    READ UNCOMMITTED 读未提交(RU) 这种级别会读出脏数据，就是一个事务读到另一个事物未提交的数据
    

## 锁

### 一些常见操作命令
    查看当前mysql使用的引擎   show variables like '%storage_engine%';
    查看mysql的版本 select version();
    查看事务隔离级别 SELECT @@tx_isolation
    设置事务隔离级别 set session transaction isolation level rencad ucommitted;
    SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
   
    禁止自动提交 set autocommit=0;
     
    #设置事务特性,必须在所有事务开始前设置
    #set transaction read only;  #设置事务只读
    set transaction read write;  #设置事务可读、写
     
    #开始一次事务
    开启事务 start transaction;
    insert into student values('spw');
    回滚事务 rollback
    提交事务 commit;
   
    

### 表语句
    CREATE TABLE `student` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    CASE1: 在主键自增情况下，并且 innodb_autoinc_lock_mode ==0
        事务1 
        start TRANSACTION;
        insert into student (name) VALUE ('spw1');
       
        事务2
        start TRANSACTION;
        insert into student (name) VALUE ('spw1');
        COMMIT;
        
       这种情况下事务1会进行表锁，事务2会阻塞    