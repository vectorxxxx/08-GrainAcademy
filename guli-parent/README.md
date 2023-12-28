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

