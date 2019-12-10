# Linux高级指令

## 1、cut

> 具体的说就是在文件中负责剪切数据用的。cut命令从文件的每一行剪切字节和字段并将这些字节、字符和字段输出

* 基本语法

  ```linux
  cut [选项参数] filename
  说明：默认分隔符时制表符
  ```

* 选项参数说明

  | 选项参数 | 功能                         |
  | -------- | ---------------------------- |
  | -f       | 列号，提取第几列             |
  | -d       | 分隔符，按照指定分隔符分割列 |

* 实列说明

```linux
> cut -d " " -f 1 a.txt
根据空格切割a.txt，并返回第一列数据
> cut -d " " -f 2,3 a.txt
根据空格切割a.txt，并返回第2列和第3列
> cut a.txt | grep "guan" | cut -d " " -f 1
从a.txt中切割出guan
> ifconfig eth0 | grep "inet addr" | cut -d: -f2 | cut -d" " -f1
```

## 2、sed

> sed是一种流编辑器，他一次处理一行的内容。处理时，把当前处理的行存储在临时缓冲区中，称为“模式空间”，接着用sed命令处理缓冲区中的内容，处理完成后，把缓冲区的内容送往屏幕。接着处理下一行，这样不断重复，知道文件末尾。文件内容并没有改变，除非你使用重定向存储输出

* 基本语法

  ```
  sed[选项参数] 'command' filename
  ```

* 选项参数

  | 选项参数 | 功能                              |
  | -------- | --------------------------------- |
  | -e       | 直接在指令模式上进行sed的动作编辑 |

* 命令功能描述

  | 命令 | 功能描述                                |
  | ---- | --------------------------------------- |
  | a    | 新增，a的后面可以接字符串，在下一行出现 |
  | d    | 删除                                    |
  | s    | 查找并替换                              |

* 实例说明

```
> sed '2a xxx' a.txt
将xxx这个单词插入到a.txt第二行下并打印
> sed '/wo/d' a.txt
删除a.txt文件下所有包含wo的行
> sed 's/wo/ni/g' a.txt
将a.txt文件中的wo替换成ni
> sed -e '2d' -e 's/wo/ni/g' a.txt
将a.txt文件中第二行删除并将wo替换成ni
```

## 3、awk

> 一个强大的文本分析工具，把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分在进行分析处理

* 基本用法

  ```
  awk [选项参数] 'pattern1{action1} pattern2{action2}...' filename
  说明:
  pattern:表示AWK在数据中查找的内容，就是匹配模式
  action：在找到匹配内容时所执行的一系列命令
  ```

* 选项参数说明

  | 选项参数 | 功能                 |
  | -------- | -------------------- |
  | -F       | 指定输入文件分隔符   |
  | -v       | 赋值一个用户定义变量 |

* 实例说明

```
> awk -F: '/^root/{print $7}' passwd
搜索passwd文件中所有以root关键字开头的行，并输出该行的第7列数据
> awk -F: '/^root/{print $1","$7}' passwd
搜索passwd文件以root关键字开的所有行，并输出改行的第一列和第七列，中间以“，”隔开
> awk -F: 'BEGIN{print "user,shell"} {print $1","$7} END{print "dahai,/bin/zuishuai"}' password
只显示/etc/password的第一列和第七列，以逗号分隔，且在所用行前面添加列名user，shell在最后行添加“dahai，zuishuai”
> awk -v i=1 -F: '{print $3 + i}' passwd
将passwd文件中的用户id增加数值1，并输入
```

* awk的内置变量

  | 变量     | 说明                                   |
  | -------- | -------------------------------------- |
  | FILENAME | 文件名                                 |
  | NR       | 已读的记录数                           |
  | NF       | 浏览记录的域的个数（切割后，列的个数） |

* 实例说明

  ```
  > awk -F: '{print "filename:" FILENAME ",linenumber:" NR ",columns:" NF}' password
  统计passwd文件名，每行的行号，每列的列号
  > ifconfig eth0 | grep "inet addr" | awk -F: '{print $2}' | awk -F " " '{print $1}'
  切割IP
  > awk '/^$/{print NR}' a.txt
  统计a.txt中空行所在的行号
  ```

  