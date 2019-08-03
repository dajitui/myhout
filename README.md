# myhout
下载sql语句进行导入数据库，然后配置好项目里面的配置
DemoApplicationTests，contextLoads执行这个方法，自己可以重新写（主要是向数据库写入大量假数据）
将target下class里面dajitui.data删掉，访问下http://localhost:8080/insert进行将数据库的数据迁移到dajitui.data文件里面
访问http://localhost:8080/mahout?uid=5可以查看推荐的内容id
访问http://localhost:8080/tongji可以将所有用户推荐的内容id保存到数据库，里面逻辑自己改