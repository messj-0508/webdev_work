
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


### 中文版

1.使用英语来书写提交记录，使用英文路径。
2.提交与issu相关·
3.命名规则
4.提交内容中不能包含敏感信息。如果上传这些信息，可执行以下命令：

``` bash
#例如，敏感文件是config.php
rm -rf config.php
git reset --hard HEAD^ //如果你想回退到更老的版本 , 将HEAD^替换成更老的版本号 (HEAD^ 代表上一个版本号 , HEAD^^ means 代表上上一个版本). 
git add .
git commit -m "remove config.php"
git push --force
```

5.需要写一个项目介绍文档，如README.md