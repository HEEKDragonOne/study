## Bean的生命周期

**Bean的生命周期**：`实例化` → `属性注入` → `初始化` → `使用` → `销毁` 。
![Bean的生命周期](./beansmzq.png)

## Bean的初始化和销毁

- 初始化阶段执行顺序：构造方法 > @PostConstruct注解方法 > InitializingBean接口的afterPropertiesSet方法 >
@Bean的initMethod方法。

- 销毁阶段执行顺序：@PreDestroy注解方法 > DisposableBean接口的destroy方法 > @Bean的destroyMethod方法。

### 方式一：手动配置@Bean

通过手动使用`@Bean`的方式进行配置初始化和销毁。

对应Bean：

```java
package com.tothefor.study.beans;


public class User {


    public User() {
        System.out.println("配置Bean User无参构造执行...");
    }

    // 初始化方法，对应initMethod
    public void init() {
        System.out.println("配置Bean initMethod方法执行");
    }

    // 销毁方法，对应destroyMethod
    public void destroyMethod() {
        System.out.println("配置Bean destroyMethod方法执行");
    }

}


```

Bean的配置类：

```java
package com.tothefor.study.beans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // 手动注册Bean，并指定初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destroyMethod")
    public User user() {
        return new User();
    }

}

```

输出结果：

```bash
User无参构造执行...
配置Bean initMethod方法执行
配置Bean destroyMethod方法执行
```

### 方式二：注解方式

通过使用注解`@PostConstruct`和`@PreDestroy`实现初始化和销毁。

对应Bean：

```java
package com.tothefor.study.beans;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User {


    public User() {
        System.out.println("User无参构造执行...");
    }

    // 初始化方法，对应initMethod
    public void init() {
        System.out.println("配置Bean initMethod方法执行");
    }

    // 销毁方法，对应destroyMethod
    public void destroyMethod() {
        System.out.println("配置Bean destroyMethod方法执行");
    }

    @PostConstruct
    public void initPostConstruct() {
        System.out.println("注解方式 initMethod方法执行");
    }

    // 容器销毁前执行销毁操作
    @PreDestroy
    public void destroyPreDestroy() {
        System.out.println("注解方式 destroyMethod方法执行");
    }

}

```

输出：

```bash
User无参构造执行...
注解方式 initMethod方法执行
配置Bean initMethod方法执行
注解方式 destroyMethod方法执行
配置Bean destroyMethod方法执行
```

### 方式三：实现接口

通过实现`InitializingBean`接口的`afterPropertiesSet`方法和`DisposableBean`接口的`destroy`方法实现初始化和销毁。

```java
package com.tothefor.study.beans;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User implements InitializingBean, DisposableBean {


    public User() {
        System.out.println("User无参构造执行...");
    }

    // 初始化方法，对应initMethod
    public void init() {
        System.out.println("配置Bean initMethod方法执行");
    }

    // 销毁方法，对应destroyMethod
    public void destroyMethod() {
        System.out.println("配置Bean destroyMethod方法执行");
    }

    @PostConstruct
    public void initPostConstruct() {
        System.out.println("注解方式 initMethod方法执行");
    }

    // 容器销毁前执行销毁操作
    @PreDestroy
    public void destroyPreDestroy() {
        System.out.println("注解方式 destroyMethod方法执行");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("接口方式 initMethod方法执行");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("接口方式 destroyMethod方法执行");

    }
}

```

输出结果：

```bash
User无参构造执行...
注解方式 initMethod方法执行
接口方式 initMethod方法执行
配置Bean initMethod方法执行
注解方式 destroyMethod方法执行
接口方式 destroyMethod方法执行
配置Bean destroyMethod方法执行
```

总结：

- 初始化阶段执行顺序： 构造方法 > `@PostConstruct`注解方法 > InitializingBean接口的`afterPropertiesSet`方法 >
  @Bean的initMethod方法。
- 销毁阶段执行顺序：`@PreDestroy`注解方法 > DisposableBean接口的`destroy`方法 > @Bean的destroyMethod方法。





