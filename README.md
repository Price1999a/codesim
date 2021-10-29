# CodeSim

## 编译需要
JDK > 1.8  
maven > 3.6.3  
clang 13.0.0 （程序基于此版本clang）  

## 编译说明  

- 确保可靠的互联网连接，程序依靠maven管理java库依赖。  
- 在程序源代码根目录下，运行 `mvn package` 命令。  
- `./target/codesim-1.0-jar-with-dependencies.jar` 就是目标程序。  

## 运行工具简要说明  

在程序源代码根目录下 运行  
`java ./target/codesim-1.0-jar-with-dependencies.jar [-v|--verbose] [-h|--help] code1 code2`  

## 补充信息
程序设计中假设接受的程序在编译时没有error，因此程序并未对无法通过编译的程序导致的错误做出任何处理。