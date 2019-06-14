# numpy中array和asarray的区别

array和asarray都可以将结构数据转化为ndarray，但是主要区别就是当数据源是ndarray时，array仍然会copy出一个副本，占用新的内存，但asarray不会。

举例说明：

输入：

``` python
import numpy as np  
  
#example 1:  
data1=[[1,1,1],[1,1,1],[1,1,1]]  
arr2=np.array(data1)  
arr3=np.asarray(data1)  
data1[1][1]=2  
print 'data1:\n',data1  
print 'arr2:\n',arr2  
print 'arr3:\n',arr3  
```

输出：

``` python
data1:  
[[1, 1, 1], [1, 2, 1], [1, 1, 1]]  
arr2:  
[[1 1 1]  
 [1 1 1]  
 [1 1 1]]  
arr3:  
[[1 1 1]  
 [1 1 1]  
 [1 1 1]]  
```


结论：面对元组数据结构，array和asarray没有区别，都对元数据进行了复制并转化为ndarray。


输入：


``` python
import numpy as np  
  
#example 2:  
arr1=np.ones((3,3))  
arr2=np.array(arr1)  
arr3=np.asarray(arr1)  
arr1[1]=2  
print 'arr1:\n',arr1  
print 'arr2:\n',arr2  
print 'arr3:\n',arr3  
```


输出：


``` python
arr1:  
[[ 1.  1.  1.]  
 [ 2.  2.  2.]  
 [ 1.  1.  1.]]  
arr2:  
[[ 1.  1.  1.]  
 [ 1.  1.  1.]  
 [ 1.  1.  1.]]  
arr3:  
[[ 1.  1.  1.]  
 [ 2.  2.  2.]  
 [ 1.  1.  1.]]  
```


结论：当数据源是ndarray时，array会copy出一个副本，占用新的内存，但asarray不会。