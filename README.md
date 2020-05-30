## 学习
#### mysql版本：5.7.21
#### jdk版本：1.8
#### jpa版本：2.0+


## 使用mybatis
```$xslt
pom.xml 引入 mybatis依赖
启动类注解配置
可选使用注解sql语句 或者 xml文件配置（resource/mapper/*）

```

## intelliJ 热加载
```
1. ctrl + alt + a 搜索 registry
2. settinig -> build -> build...automatically
3. intelliJ 右下角 Power Save Mode
```

## intelliJ 单项目开发环境多端口
```
1. Edit Configurations 添加一个新的启动项, Environment -> VM Options [-Dserver.port=8081]
```