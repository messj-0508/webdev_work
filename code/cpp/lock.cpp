//定义读写锁的数据结构：
typedef struct RWLOCK_st{  
    spinlock  ReadLock;            // 读锁
    spinlock  WriteLock;           // 写锁
    UINT uReadcount;               // 读者计数器
}RWLOCK; 


// 读操作保护的伪代码 


RWLock_LockRead()
{
    ReadLock->acquire();            // 上锁锁住计数器变量的读写，即读锁
    uReadcount += 1;                // 读者计数器加1
    if(uReadcount > 0)              // 判断是否触发写锁获取条件————读者非零
        WriteLock->acquire();       // 获取写锁
    ReadLock->release();            // 解锁计数器变量的读写，即读锁
}

RWLock_UnlockRead()
{
    ReadLock->acquire();            // 上锁锁住计数器变量的读写，即读锁
    uReadcount -= 1;                // 读者计数器减1
    if(uReadcount == 0)             // 判断是否触发写锁释放条件————读者为零
        WriteLock->release();       // 释放写锁
    ReadLock->release();            // 解锁计数器变量的读写，即读锁
}


//写操作保护的伪代码 


RWLock_LockWrite()
{                
    WriteLock->acquire();           // 获取写锁
}

RWLock_UnlockWrite()
{                
    WriteLock->release();           // 释放写锁
} 


方案：将读写操作定义为互斥关系，将不同写操作定义为读操作。

优点：保证了多处理器上的读写操作的同步互斥关系，实现简单。

缺点：读操作比较频繁时，计数uReadCount可能一直无法归零，会导致写操作无法进行，从而出现写操作饿死现象 

优化方案：为读操作设置副本文件，使得读写操作不存在互斥关系。
         RWLock_LockRead():
         1. 在获取读锁后，立即判断读者计数器是否为0。
            1.1. 若为0，则建立副本文件并链接至读写锁（副本文件唯一）；
         2. 读者计数器加1
         3. 释放读锁

         RWLock_UnlockRead():
         1. 获取读锁
         2. 读者计数器减1
         3. 判断读者计数器是否为0，若为0，则取消链接并删除副本文件；
         4. 释放读锁
            
优点：解决了写操作可能出现的饿死现象

缺点：副本文件与原文件可能存在滞后的问题。