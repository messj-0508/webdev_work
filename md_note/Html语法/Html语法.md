# Html语法

---

HTML 是用来描述网页的一种语言。

- HTML 指的是超文本标记语言 (**H**yper **T**ext **M**arkup **L**anguage)
- HTML 不是一种编程语言，而是一种*标记语言* (markup language)
- 标记语言是一套*标记标签* (markup tag)
- HTML 使用*标记标签*来描述网页

---

## 一. html基本模板

1. 一般在文件开头打上<!DOCTYPE html>，表明文件类型

2. HTML文件以<html>开始，以</html>结束。

3. 而<head>标签中的内容不显示在浏览器上，一般是属性的设置；<body>标签中的内容为要显示的文章内容

4. 标题与段落

       <h1>代表标题1
       
       <h2>代表标题2
       
       …….
       
       以此类推
       
       <p>代表段落

5. 注释：<!---comments--->

6. 换行符：<br/>直接换行

   而<hr>,增加一条横线

7. &nbsp表示空格

   ```html
   <!DOCTYPE html>
   <html>
   	<head>
   		<title>网页标题</title>
   	</head>
   	<body>
   		<h1>this is a heading</h1>
   	</body>
   </html>
   ```

----

## 二. 链接类标签

1. <a>标签是超链接标签，链接的目的地在href属性指定

``` html
	<a href="adress">This is a link!</a>
```

2. 图片:img,源文件(src),替换文件(alt)(在无法显示时打开），宽度和高度

```html
	<img src="adress" alt="imgname" width="100" height="123">
```

3. 图片为链接：href为要链接到的地址，src为图片地址，alt为名称

```html
	<a href="default.asp">
    	<img src="adress" alt="imgname" width="100" height="123">
	</a>
```

---

## 三. 常用标签

```html
1. <b>标签：定义粗体文本。

2. <small>标签：定义小号字。

3. <big>标签：定义大号字。

4. <table>标签：定义表格

5. <th>标签：定义表格的表头。

6. <tr>标签：定义表格的行。

7. <td>   标签：表格单元。

8. <thead>标签：定义表格的页眉。

9. <tbody>标签：定义表格的主体。

10. <tfoot>标签：定义表格的页脚。

11. <ul>标签：定义无序列表。

12. <li>标签：定义列表项。

13. <div>标签：定义文档中的分区或节（division/section）。

14. <span>标签：定义 span，用来组合文档中的行内元素。

15. <head>标签：定义关于文档的信息。

16. <title>标签：定义文档标题。

17. <base>标签：定义页面上所有链接的默认地址或默认目标。

18. <link>标签：定义文档与外部资源之间的关系。

19. <meta>标签：定义关于 HTML 文档的元数据。

20. <script>标签：定义客户端脚本。

21. <style>标签：定义文档的样式信息。
```








