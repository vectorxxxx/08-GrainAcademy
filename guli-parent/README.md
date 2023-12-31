## 1、InitializingBean 是什么？

`InitializingBean` 是 Spring 框架中用来标记需要在实例化后进行初始化的对象的接口。它是 `Object` 类的子类，这意味着任何对象都可以实现这个接口，但通常用于标记 Spring 管理的需要在实例化之前进行初始化的对象。

当实现了 `InitializingBean` 接口的对象被实例化时，Spring 会调用该对象的 `afterPropertiesSet()` 方法来进行初始化。这个方法只会在一次被调用，并且是在所有属性设置完成后调用的。

下面是一个使用 `InitializingBean` 的示例：
```java
@Component
public class MyBean implements InitializingBean {

    private String message;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 在这里进行初始化
        message = "Hello, world!";
    }

    // 其他bean的方法...
}
```
在这个例子中，`MyBean` 类实现了 `InitializingBean` 接口，并重写了 `afterPropertiesSet()` 方法来设置 `message` 字段为 "Hello, world!"。在对象被实例化后，Spring 会调用这个方法来对对象进行初始化，以便在它被使用之前进行设置。



## 2、Objects.requireNonNull

 `Objects.requireNonNull()` 是 Java 中的一个静态方法，它接受一个参数 `obj`，如果该参数为 `null`，则抛出一个 `NullPointerException` 异常。这个方法的主要目的是确保传入的对象不为 `null`，从而避免在程序中出现潜在的 `NullPointerException` 问题。

在 Java 中，当一个对象被引用时，如果该对象为 `null`，那么对它的任何操作都可能导致 `NullPointerException` 异常。为了避免这种情况，通常需要在使用对象之前检查它是否为 `null`。`Objects.requireNonNull()` 方法就是用来实现这个目的的。

下面是一个使用 `Objects.requireNonNull()` 的示例：
```
String str = "Hello, world!";
Objects.requireNonNull(str, "str cannot be null");
```
在这个例子中，我们使用 `Objects.requireNonNull()` 方法来检查 `str` 是否为 `null`。如果 `str` 为 `null`，则会抛出一个 `NullPointerException` 异常，其中包含了自定义的错误消息 "str cannot be null"。

需要注意的是，`Objects.requireNonNull()` 方法不会修改原始对象，它只会检查对象是否为 `null`，并抛出异常。因此，在使用 `Objects.requireNonNull()` 方法时，不需要担心原始对象会被修改。



## 3、视频点播服务上传

```bash
mvn install:install-file -DgroupId=com.aliyun -DartifactId=aliyun-sdk-vod-upload -Dversion=1.4.15 -Dpackaging=jar -Dfile=aliyun-java-vod-upload-1.4.15.jar -Dmaven.repo.local=D:\.m2\repository-mine -s D:\workspace-mine\apache-maven-3.8.3\conf\settings.xml
```



## 4、CMS 是什么？

CMS（Content Management System，内容管理系统）是一种用于创建、管理网站和应用程序内容的软件。它允许用户创建、修改和删除内容，同时提供模板、插件和其他功能，以便用户可以轻松地定制网站和应用程序的外观和功能。

CMS 通常包括以下功能：

1. 内容管理：用户可以通过 CMS 界面创建、编辑和删除内容，例如文章、新闻、产品等。
2. 模板管理：CMS 允许用户创建和管理模板，以便用户可以轻松地更改网站的外观和布局。
3. 插件管理：CMS 允许用户安装和删除插件，以扩展其功能。
4. 用户管理：CMS 可以管理用户，包括添加、删除和修改用户信息。
5. 权限管理：CMS 可以设置用户权限，以便用户只能看到他们有权限查看的内容。
6. 数据分析：CMS 可以收集和分析网站数据，以便用户可以了解网站的流量、访问者和搜索引擎优化情况。

CMS 在现代互联网应用中非常流行，因为它可以简化网站管理和开发过程，提高网站的性能和用户体验。



## 5、CRM 是什么？

CRM（Customer Relationship Management，客户关系管理）是一种用于管理客户信息、关系和 interactions 的软件。它可以帮助企业更好地了解客户，提高客户满意度，并优化业务流程。

CRM 通常包括以下功能：

1. 客户信息管理：CRM 允许用户输入、编辑和查看客户信息，例如姓名、联系方式、购买历史等。
2. 关系管理：CRM 允许用户创建、编辑和删除客户关系，例如联系人、潜在客户、客户等。
3. 互动管理：CRM 允许用户记录客户与企业的互动，例如电子邮件、电话、社交媒体等。
4. 机会管理：CRM 允许用户管理客户机会，例如销售机会、市场机会等。
5. 权限管理：CRM 可以设置用户权限，以便用户只能看到他们有权限查看的客户信息。
6. 数据分析：CRM 可以收集和分析客户数据，以便用户可以了解客户的行为、偏好和需求。

CRM 在现代商业中非常流行，因为它可以帮助企业更好地管理客户关系，提高客户满意度，并优化业务流程。



## 6、Redis 命令

```shell
# 查询所有键
keys *
```



## 7、OAuth2 是什么？

OAuth2 的全名是 Open Authorization 2.0，它是 OAuth 协议的一个版本，旨在为现代应用程序提供更安全和可靠的授权和访问控制。

OAuth2 的名称中“OAuth”代表开放授权，“2.0”代表这是协议的第二版本，具有比第一个版本更多的功能和更好的安全性



## 8、令牌类比仆从钥匙是什么？

令牌类比为仆从钥匙，是一种用于授权验证和访问控制的特殊凭证。令牌通常由服务器生成，并发送给客户端应用程序以进行身份验证和授权。客户端应用程序可以使用令牌来请求访问控制资源的权限。

令牌类比为仆从钥匙，是因为它们具有相同的用途。令牌用于验证客户端的身份，并允许客户端访问特定的资源。这类似于仆从钥匙，它们用于打开和关闭主人的门，允许或拒绝仆人进入。

令牌在OAuth2协议中具有重要的角色，因为它允许客户端应用程序访问受保护的资源，而无需将用户身份暴露给客户端应用程序。使用令牌，客户端应用程序可以请求访问控制资源的权限，并在服务器验证其身份后获得访问权限。

总之，令牌类比为仆从钥匙，它们用于授权验证和访问控制，是 OAuth2 协议中的重要组成部分。



## 9、Canal 中 MySQL 相关命令

```shell
# 进入MySQL命令行
mysql -u root -p

# 检查binlog功能是否开启
mysql> show variables like 'log_bin';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_bin       | ON    |
+---------------+-------+
1 row in set (0.00 sec)

# 在mysql里面添加以下的相关用户和权限
mysql> CREATE USER 'canal'@'%' IDENTIFIED BY 'canal';
mysql> GRANT SHOW VIEW, SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
mysql> FLUSH PRIVILEGES;
```



## 10、ObjectMapper 是什么？

ObjectMapper 是一个用于将对象转换为 JSON 和将 JSON 转换为对象的 Java 库。它是一个高性能的库，具有简洁的 API，可以轻松地将 Java 对象序列化为 JSON 字符串，或将 JSON 字符串反序列化为 Java 对象。ObjectMapper 基于 Jackson 库，是 Java EE 5 和 6 的标准库之一。

ObjectMapper 提供了丰富的功能，例如支持泛型、集合、日期、数字、布尔值、空值处理等。它还支持自定义序列化和反序列化器，以支持自定义类型。ObjectMapper 还提供了强大的流 API，可以处理大型 JSON 数据。

总之，ObjectMapper 是一个强大的库，可以轻松地将 Java 对象转换为 JSON 字符串，或将 JSON 字符串转换为 Java 对象。



## 11、CSRF 是什么？

CSRF（跨站请求伪造，Cross-Site Request Forgery）是一种攻击，攻击者通过伪造 HTTP 请求来欺骗用户，从而获取用户的敏感信息，如密码、信用卡信息等。CSRF 攻击通常发生在用户登录、注销等敏感操作时。

CSRF 攻击的主要原因是用户在登录后，浏览器会自动记住用户身份，从而在后续的请求中自动添加用户身份信息。攻击者可以通过修改请求头中的 Cookie 或 User-Agent 来伪造请求，从而欺骗用户。

CSRF 攻击的常见方法包括：

1. 伪造请求头：攻击者通过修改请求头中的 Cookie 或 User-Agent 来伪造请求。
2. 利用 URL 重定向：攻击者通过将用户重定向到恶意网站来欺骗用户。
3. 利用 POST 请求：攻击者通过在 POST 请求中添加恶意数据来欺骗用户。

CSRF 攻击是一种非常有害的攻击，因为它可以欺骗用户并获取他们的敏感信息。为了避免 CSRF 攻击，网站需要采取一系列安全措施，例如验证请求的来源、验证用户身份等。



## 12、字段修改

```mysql
# guli_e
alter table edu_chapter
    add is_deleted tinyint(1) unsigned default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除';
alter table edu_course_description
    add is_deleted tinyint(1) unsigned default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除';
alter table edu_subject
    add is_deleted tinyint(1) unsigned default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除';
alter table edu_video
    add is_deleted tinyint(1) unsigned default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除';
# guli
alter table statistics_daily
    add is_deleted tinyint(1) unsigned default 0 not null comment '逻辑删除 1（true）已删除， 0（false）未删除';
```



## 13、Dockerfile 文件干嘛用的？

Dockerfile 是一个文本文件，其中包含用于构建 Docker 镜像的指令。Docker 镜像是一个预先编译好的应用程序，可以部署到任何支持 Docker 的平台。Dockerfile 是构建 Docker 镜像的关键文件，它定义了如何构建镜像以及如何安装应用程序所需的所有依赖项。

Dockerfile 文件通常位于项目的根目录下，并且它的名称通常为"Dockerfile"。当然，你也可以为 Dockerfile 指定其他名称，但 Docker 会自动将其重命名为"Dockerfile"。

Dockerfile 的主要用途包括：

1. 定义镜像的基础映像：Dockerfile 的第一行通常定义了要使用的镜像，例如：FROM openjdk:8。
2. 安装依赖项：Dockerfile 可以使用 COPY、ADD、RUN 等指令安装应用程序所需的依赖项。
3. 配置应用程序：Dockerfile 可以使用 CMD、ENTRYPOINT 等指令配置应用程序的启动命令和入口点。
4. 构建和打包镜像：Dockerfile 中定义了如何构建镜像，包括如何将应用程序的文件复制到镜像中。

Dockerfile 的用途因项目而异，但通常用于构建和部署应用程序。例如，对于 Java 应用程序，Dockerfile 可能包括以下内容：

```
FROM openjdk:8
COPY ./target/myapp.jar /app.jar
CMD java -jar /app.jar
```

这将从 OpenJDK 8 镜像中构建应用程序，并将应用程序的 jar 文件复制到镜像中，最后使用 java 命令启动应用程序。
