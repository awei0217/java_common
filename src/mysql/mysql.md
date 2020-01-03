
## ����

### mysql��������
    
    A: atomicity.   ԭ����    һ�����������Ҫôȫ���ɹ���Ҫôȫ��ʧ��,������ͣ�����м�״̬
    C: consistency. һ����    �����һ���Զ�������������Ϊ�����������������Լ������ѭ������ִ�е�ǰ���ǺϷ�������״̬������Υ���κε�����������
    I:: isolation.  ������    ��ͬ��������໥���룬����Ӱ�� 
    D: durability.  �־���    ������ɺ�,�����ݵĸ����������Ե� 
    
### mysql���񼶱�    

    ����ߵ�һ���Ժ͵���ͣ�InnoDB֧�ֵĸ��뼶��Ϊ�� SERIALIZABLE�� REPEATABLE READ�� READ COMMITTED�� READ UNCOMMITTED
    
    SERIALIZABLE ��ȫ���У������������
    
    REPEATABLE READ ���ظ���(RR) Ĭ�ϸ��뼶�����ּ����£���ֹ�κεĲ�ѯ�ᱻ����������ģ����ָ��뼶�����ֻö�����˼��A�����һ�β�ѯ�����A��B���������һ�����ݲ��ύ��A����ڶ��β�ѯ�������A(����˲����ظ�������MVCC���)������A��������θ���ʱ�ᷢ��B��ӵ����ݡ�
         MySQL��������
         he default isolation level for InnoDB. It prevents any rows that are queried from being changed by other transactions, 
         thus blocking non-repeatable reads but not phantom reads. 
         It uses a moderately strict locking strategy so that all queries within a transaction see data from the same snapshot, 
         that is, the data as it was at the time the transaction started
         
         When a transaction with this isolation level performs UPDATE ... WHERE, DELETE ... WHERE, SELECT ... FOR UPDATE, and LOCK IN SHARE MODE operations,
         other transactions might have to wait.
         SELECT ... FOR SHARE replaces SELECT ... LOCK IN SHARE MODE in MySQL 8.0.1, but LOCK IN SHARE MODE remains available for backward compatibility.
         
    READ COMMITTED �����ظ���(RC) ���ּ������ظ���ʱ����ֲ�ͬ�������������A��һ�ζ���A����ʱ����B�ύ������A�ٴζ�ʱ�����AB����һ������A�У����ζ������Ľ����һ��
         MySQL��������
         An isolation level that uses a locking strategy that relaxes some of the protection between transactions, 
         in the interest of performance. Transactions cannot see uncommitted data from other transactions, 
         but they can see data that is committed by another transaction after the current transaction started. 
         Thus, a transaction never sees any bad data, but the data that it does see may depend to some extent on the timing of other transactions
         
         When a transaction with this isolation level performs UPDATE ... WHERE or DELETE ... WHERE operations, other transactions might have to wait. 
         The transaction can perform SELECT ... FOR UPDATE, and LOCK IN SHARE MODE operations without making other transactions wait.
         SELECT ... FOR SHARE replaces SELECT ... LOCK IN SHARE MODE in MySQL 8.0.1, but LOCK IN SHARE MODE remains available for backward compatibility
         
    READ UNCOMMITTED ��δ�ύ(RU) ���ּ������������ݣ�����һ�����������һ������δ�ύ������
    

## ��

### һЩ������������
    �鿴��ǰmysqlʹ�õ�����   show variables like '%storage_engine%';
    �鿴mysql�İ汾 select version();
    �鿴������뼶�� SELECT @@tx_isolation
    ����������뼶�� set session transaction isolation level rencad ucommitted;
    SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
   
    ��ֹ�Զ��ύ set autocommit=0;
     
    #������������,��������������ʼǰ����
    #set transaction read only;  #��������ֻ��
    set transaction read write;  #��������ɶ���д
     
    #��ʼһ������
    �������� start transaction;
    insert into student values('spw');
    �ع����� rollback
    �ύ���� commit;
   
    

### �����
    CREATE TABLE `student` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    CASE1: ��������������£����� innodb_autoinc_lock_mode ==0
        ����1 
        start TRANSACTION;
        insert into student (name) VALUE ('spw1');
       
        ����2
        start TRANSACTION;
        insert into student (name) VALUE ('spw1');
        COMMIT;
        
       �������������1����б���������2������    