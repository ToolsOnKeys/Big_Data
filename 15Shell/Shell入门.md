# Shell

## 1、Shell概述

* Shell是一个命令解释器，他接受应用程序/用户命令，然后调用操作系统内核。

* Shell还是一个功能相当强大的编程语言，易编写、易调试、灵活性强。

## 2、Shell解析器

### ①、查看Linux提供的Shell解析器

```linux
> cat /etc/shells
/bin/sh
/bin/bash
/sbin/nologin
/bin/dash
/bin/tcsh
/bin/csh
```

### ②、bash和sh的关系

* sh是bash的一种特殊的模式，也就是/bin/sh相当于/bin/bash --posix。说白了sh就是开启了POSIX（可以指操作系统接口）标准的bash。sh一般设成bash的软链。

### ③、Centos默认的解析器是bash

## 3、Shell脚本入门

### ①、脚本格式

* 脚本以  #！/bin/bash 开头，即指定解析器。

### ②、执行脚本的几种执行方式

* 采用bash和sh的相对路径或绝对路径的方式（不用赋予脚本x的权限）

  ```linux
  > sh xxx.sh   #可以指定文件的全路径，或在文件当前路径使用相对路径
  > bash xxx.sh   #可以指定文件的全路径，或在文件当前路径使用相对路径
  ```

* 采用输入脚本的绝对路径或相对路径执行脚本（需要可执行x的权限）

  ```linux
  > chmod 777 xxx.sh #赋权
  > ./xxx.sh 
  ```

  * 注意：第一种执行方法，本质是bash解析器帮你执行了你的脚本，所以脚本本身不需要由执行权限。

* 在脚本的路径前加 “.”

  ```linux
  > . xxx.sh
  # 前两种方式都是在当前shell中打开一个子shell来执行脚本语言，当脚本内容结束，则子shell关闭，回到父shell中。
  # 当前方法可以是脚本内容在当前shell理智型，无需打开子shell。
  # 开子shell和不开子shell的区别在于，环境变量的继承关系，如在子shell中设置的当前变量，父shell是不可见。
  ```

## 4、Shell中的变量

### ①、系统变量

* 1、常用的系统变量

  HOME、PWD、SHELL

* 2、相关系统变量的linux实操

  ```linux
  > echo $HOME  #查看系统变量的值
  > set #显示当前shell中所有的环境变量
  ```

### ②、自定义变量

* 1、基本语法

  * 定义变量：变量=值
  * 撤销变量：unset 变量
  * 声明静态变量：readonly变量。注意：静态变量不能unset，且不能重新赋值

* 2、变量定义的规则

  * 变量名称可以是以字母、数字、下划线组成，但是不能以数字开头，环境变量名建议大写。
  * 等号两侧不能有空格
  * 在bash中，变量默认类型都是字符串类型，无法直接进行数值运算。
  * 变量的值如果有空格，需要使用双引号或单引号括起来。

* 3、案例实操

  * 定义变量或重新赋值变量

    ``` linux
    > A=3
    ```

  * 撤销变量

    ```linux
    > unset A
    ```

  * 声明静态的变量

    ```linux
    > readonly B=2 # 不可撤销和重新赋值。
    ```

  * 在bash中，变量默认是字符串类型，无法直接进行数值运算

    ```linux
    > C=1+2
    > echo $C   #结果是1+2
    ```

  * 变量的值如果有空格，需要使用双引号或单引号括起来。

    ```linux
    > D=I love you # 报错
    > D="I love you" 
    > D='I love you'
    ```

  * 可以把变量提升为全局环境变量，可供其他Shell程序使用-》   export 变量名

### ③、特殊变量：$n    $#    $*     $@     $?

* 1、基本语法

  ```linux
  > $n #功能描述：n为数字，$0代表脚本名称，$1-9代表第一到第九个参数，10个以上的参数使用${10}
  > $#   #功能描述：获取所有输入参数的个数，常用于循环
  > $* #功能描述：这个变量代表命令行中所有的参数，即把所有的参数看成一个整体
  > $@ #功能描述：这个变量也代表命令行中所有的参数，不过$@把每个参数区分对待
  > $? #功能描述：最后一次执行的命令的返回状态。如果这个变量的值为0，证明上一次命令正常执行；如果这个变量的值非0，则证明上一个命令执行不正确。
  ```

## 5、运算符

### ①、基本语法

* $((运算式))    或       $[运算式]
* expr 运算式     #注意：运算式中运算符两边需要有空格;\\*表示乘法

### ②、案列实操

```linux
> expr 2 + 3           # 5
> expr `expr 2 + 3` \* 4      #（2+3）*4  运算并直接输出运算结果
> A=$[(2+3)*4]      #给A赋值（2+3）*4
```

## 6、条件判断

### ①、基本语法

```linux
> [ 条件 ]   #注意：条件前后需要有空格；条件非空即为true，反之为false
```

### ②、常用判断条件

* （1）两个整数之间比较

  ​     = 字符串比较

​            -lt 小于（less than）                  -le 小于等于（less equal）

​            -eq 等于（equal）                     -gt 大于（greater than）

​            -ge 大于等于（greater equal）    -ne 不等于（Not equal）

        ```linux
> [ 23 -ge 22 ]
        ```

* （2）按照文件权限进行判断

​              -r 有读的权限（read）              

​              -w 有写的权限（write）

​              -x 有执行的权限（execute）

```linux
> [ -x xxx.sh ]
```

* （3）按照文件类型进行判断

​               -f 文件存在并且是一个常规的文件（file）

​               -e 文件存在（existence）          

​               -d 文件存在并是一个目录（directory）

```linux
> [ -f sss.txt ]
```

## 7、流程控制

### ①、if判断

* 基本语法

  ```linux
  if [ $1 -eq "1" ]
  then
          echo "banzhang zhen shuai"
  elif [ $1 -eq "2" ]
  then
          echo "cls zhen mei"
  fi
  # 注意：
  # if 后面要有空格
  # [ 条件式 ] 条件是两边要有空格
  ```

### ②、case语句

* 基本语法

  ```linux
  case $1 in
  "1")
          echo "banzhang"
  ;;
  
  "2")
          echo "cls"
  ;;
  *)
          echo "renyao"
  ;;
  esac
  # 注意：
  # case行尾必须为单词 in 
  # 每个模式匹配需要以右括号）结束
  # 双分号；；表示命令序列结束，相当于java中的break
  # 最后的*） 表示默认模式，相当于default
  ```

### ③、for循环

* 基本语法

  ```linux
  for((i=0;i<=100;i++))
  do
          s=$[$s+$i]
  done
  ```

  ```linux
  for i in "$*" 
  #$*中的所有参数看成是一个整体，所以这个for循环只会循环一次 
          do 
                  echo "ban zhang love $i"
          done 
  
  for j in "$@" 
  #$@中的每个参数都看成是独立的，所以“$@”中有几个参数，就会循环几次 
          do 
                  echo "ban zhang love $j" 
  done
  ```

### ④、while循环

* 基本语法

  ```linux
  while [ $i -le 100 ]
  do
          s=$[$s+$i]
          i=$[$i+1]
  done
  ```

## 8、read读取控制台输入

### ①、基本语法

* read（选项）（参数）

  * 选项：

  ​      -p：指定读取值时的提示符；

  ​       -t：指定读取值时等待的时间（秒）。

  * 参数

  ​       变量：指定读取值的变量名

```linux
read -t 7 -p "Enter your name in 7 seconds " NAME
# 提示7秒内，读取控制台输入，并赋值给NAME
```

## 9、函数

### ①、系统函数

* 1、basename 基本语法

  ```linux
  basename [string / pathname][suffix]
  # 功能描述：basename命令会删掉所有的前缀包括最后一个字符/，然后将字符串显示出来。
  # 选项：suffix为后缀，如果suffix被指定了，basename会将pathname或string中的suffix去掉。
  >  basename /home/atguigu/banzhang.txt .txt # 截取某一路径下以.txt结尾的文件名称。
  ```

* 2、dirname 基本语法

  ```linux
  dirname 文件的绝对路径
  # 功能描述：从给定的包含绝对路径的文件名中去除文件名（非目录部分），然后返回剩下的路径（目录部分）
  > dirname /home/atguigu/banzhang.txt  # 返回结果是 /home/atguigu
  ```

### ②、自定义函数

* 基本语法

  ```linux
  [ function ] funname[()]
  {
  	Action;
  	[return int;]
  }
  funname
  # 必须在调用函数地方之前，先声明函数，shell脚本是逐行运行。不会像其他语言一样先编译后运行
  # 函数返回值，只能通过$？系统变量获取，可以显示加：return 返回，如果不加，将默认以最后一条命令运行结果，作为返回值。
  
  function sum()   #声明函数
  {
      s=0
      s=$[ $1 + $2 ]
      echo "$s"
  }
  read -p "Please input the number1: " n1;
  read -p "Please input the number2: " n2;
  sum $n1 $n2;   # 调用sum函数
  ```

  