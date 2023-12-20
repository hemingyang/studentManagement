> **写在前面的话：项目copy下来后，直接用idea导入应该是没有问题的（项目的文件夹和包结构要正确），根据你的需要修改pom中的mysql驱动和数据库资源文件后就可以部署到tomcat运行了！**
## 学生信息管理系统（**layUI + servlet + jdbc**）
***
 **使用原生 servlet作为后端进行开发**

1. **后端采用纯servlet进行 doget ，dopost 开发，同时搭配原生的jdbc，整个架构没有使用任何框架，可以说是非常原始了；** 
2.**有登录注册功能，学生信息添加，修改，删除，查询，教师信息添加，修改，删除，查询，班级信息添加，修改，删除，查询等基础功能。**    
3. **验证码工具类可以返回验证码图片和验证码内容，数据库连接工具类可以返回一个数据库连接和关闭数据库连接的方法（但是封装和设计模式不好，虽然线程安全，但使用会频繁创建数据库连接，开销很大），还有一个工具类借助gson将request的数据转换为json串；**   
4. **Dao层都是单表的CURD，没有复杂的业务所以也就没有添加事务的处理；**

5. **这里的业务层和控制层几乎耦合在了一起，一个业务一个servlet的方式造成了严重的代码冗余和资源浪费，在一个servlet里分别使用路径匹配和if-else方法匹配才是正确方法；**  

6. **典型的前后端不分离项目，前端集成了layui和jquery实现了非常精美和赏心悦目的效果，特别是登录模块和整个后台模块都比较精美，但是在开发过程中可以明显感到前端的开发难于后端！**    

7. **这里同时使用了util.date和sql.date，可以说是一大败笔，造成了很多地方的格式转换出现了问题（觉得别扭的小伙伴可以都换成util.date，然后用@JsonFormat与@DateTimeFormat注解就行，数据库字段可以使用datetime），后者是前者的子类，**

   
> **注：mysql5到mysql8还是做了很多改进的，不仅仅是安装和配置，就连它们的连接驱动包很多地方都做了改动。** 
***
![示例图片](https://github.com/DragonLog/studentManagement/blob/main/pictureForExample/show1.jpg)
![示例图片](https://github.com/DragonLog/studentManagement/blob/main/pictureForExample/show2.jpg)
![示例图片](https://github.com/DragonLog/studentManagement/blob/main/pictureForExample/show3.jpg)
![示例图片](https://github.com/DragonLog/studentManagement/blob/main/pictureForExample/show4.jpg)
![示例图片](https://github.com/DragonLog/studentManagement/blob/main/pictureForExample/show5.jpg)

