
### Eglish Version

1.use English annotation
2.commit relate to issue
3.name rule
4.The submitted content does not contain sensitive information. Once uploaded these sensitive information , you can remove the information by these command as follow

``` bash
#For example , the file named "config.php" contains your password or other sensitive information . 
rm -rf config.php
git reset --hard HEAD^ // if you want to come back older version , replace HEAD^ with the version number (HEAD^ means the last version number , HEAD^^ means the last two version number). 
git add .
git commit -m "remove config.php"
git push --force
```

5.Please write a file to introduce your program.


### ���İ�

1.ʹ��Ӣ������д�ύ��¼��ʹ��Ӣ��·����
2.�ύ��issu��ء�
3.��������
4.�ύ�����в��ܰ���������Ϣ������ϴ���Щ��Ϣ����ִ���������

``` bash
#���磬�����ļ���config.php
rm -rf config.php
git reset --hard HEAD^ //���������˵����ϵİ汾 , ��HEAD^�滻�ɸ��ϵİ汾�� (HEAD^ ������һ���汾�� , HEAD^^ means ��������һ���汾). 
git add .
git commit -m "remove config.php"
git push --force
```

5.��Ҫдһ����Ŀ�����ĵ�����README.md