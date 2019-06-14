# 下载Coursera-notebooks全部作业文件

I have just found a way to download all the assignment files from the coursera-notebook hub.

This saved me hours of painful single file downloading:

(adapted from [here](https://stackoverflow.com/a/47355754) )

1. Go to the home of the coursera-notebook hub
2. Create a new python notebook
3. Execute `!tar cvfphz allfiles.tar.gz *` in a cell (notes:此处h是为了解决软链接问题）
4. Download the archive !

Enjoy!

If the resulting archive is too big and you can't download it

Open the python notebook where you executed last command and execute the following in a cell:

```
`!split -b 200m allfiles.tar.gz allfiles.tar.gz.part.`
```

　

This will split the archive into 200Mb blocks that you can download without a problem (if there is still a problem reduce the size by changing 200m to a lower value)

Then when you have downloaded all the split files reunite them on your system using the following command line (in a linux environment, or use cmder if you are on Windows):

```
`cat allfiles.tar.gz.part.* > allfiles.tar.gz`
```

PS: This is in fact valid in any Jupyter-notebook hub