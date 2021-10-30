# CodeSim

## 编译需要
JDK >= 1.8  
maven 3.6.3 （程序开发基于此版本clang）  
clang 13.0.0 （程序开发基于此版本clang）

## 编译说明  

- 确保可靠的互联网连接，程序依靠maven管理java库依赖。  
- 在程序源代码根目录下，运行 `mvn package` 命令。
- `./target/codesim-1.0-jar-with-dependencies.jar` 就是目标程序。

## 运行工具简要说明

在程序源代码根目录下 运行  
`./codesim [-v|--verbose] [-h|--help] code1 code2`  
`./codesim`是一个简单的脚本程序，用于调用生成的jar文件。

## 补充信息

程序设计中假设接受的程序都可以正常通过编译，因此程序对无法通过编译的程序导致的错误不做任何处理。  
另外，原则上讲，程序也可以接受C程序源代码。但除非后缀名明确为`.c`，否则程序都将把输入文件作为cpp文件编译。  
