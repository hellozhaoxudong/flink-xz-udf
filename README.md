## Flink UDF
### 一、版本
Flink 1.16.1  


**release目录下时打包好的jar，直接放在flink的lib目录下，重新启动flink即可**

---
### 二、自定义函数
#### DecodeUS7ASCII()
- 说明  
  用于解决Oracle数据库编码为 AMERICAN_AMERICA.US7ASCII 时，Flink及Flink CDC使用过程中，中文乱码的问题。

  已验证适用于Flink、FlinkCDC。


- 使用示例  
```sql
-- 注册来源表
CREATE TABLE CDC_SOURCE_9 (
    COL1 STRING NOT NULL,
    COL2 STRING,
    COL3 STRING,
    PRIMARY KEY(COL1) NOT ENFORCED
) WITH (
    'connector' = 'jdbc',
    'url'  = 'jdbc:oracle:thin:@172.25.7.206:1521/ORCL',
    'table-name'= 'CDC_SOURCE_9',
    'username' = 'root',
    'password' = 'root'
);

-- 注册目标表
CREATE TABLE CDC_TARGET_9 (
    COL1 STRING NOT NULL,
    COL2 STRING,
    COL3 STRING,
    PRIMARY KEY(COL1) NOT ENFORCED
) WITH (
    'connector' = 'jdbc',
    'url'  = 'jdbc:oracle:thin:@172.25.7.206:1521/ORCL',
    'table-name'= 'CDC_TARGET_9',
    'username' = 'root',
    'password' = 'root'
);

-- 注册自定义函数
CREATE FUNCTION DecodeUS7ASCII as 'com.zx.udf.DecodeUS7ASCII';

-- 查询数据时，使用该自定义函数进行数据转码
SELECT COL1, DecodeUS7ASCII(COL2), DecodeUS7ASCII(COL3) from CDC_SOURCE_9;

-- 写入数据时，使用该自定义函数进行数据转码
insert into CDC_TARGET_9 select SELECT COL1, DecodeUS7ASCII(COL2), DecodeUS7ASCII(COL3) from CDC_SOURCE_9;
```

---

