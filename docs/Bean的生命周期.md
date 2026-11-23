## Bean的生命周期

**Bean的生命周期**：`实例化` → `属性注入` → `初始化` → `使用` → `销毁` 。
![Bean的生命周期](./beansmzq.png)

## Bean的解析与注册

容器启动的第一步，不是直接创建Bean，而是解析并注册Bean的元数据定义。`BeanDefinition`
是Bean的元数据载体，相当于Bean的“出生证明”，包含了Bean的全量信息：全类名、作用域、是否懒加载、依赖关系、初始化方法、销毁方法等。Spring容器完全基于这个元数据来创建Bean实例。

执行流程：

- 容器启动时，扫描@Component、@Service、@Controller、@Bean等注解标注的类/方法，解析生成对应的BeanDefinition；
- 将所有BeanDefinition注册到BeanDefinitionRegistry（Bean定义注册中心）；
- 执行所有BeanFactoryPostProcessor实现类，允许开发者在Bean实例化之前，修改、新增、删除BeanDefinition

### 自定义BeanFactoryPostProcessor实现类

通过实现BeanFactoryPostProcessor来控制Bean的示例。
指定Bean：

```java
package com.tothefor.study.beans;


import org.springframework.stereotype.Component;


@Component
public class User {


    public User() {
        System.out.println("User无参构造执行...");
    }


    public void initImpl() {
        System.out.println("BeanFactoryPostProcessor的 initMethod方法执行");
    }


    public void destroyMethodImpl() {
        System.out.println("BeanFactoryPostProcessor的 destroyMethod方法执行");
    }

}

```

实现类：

```java
package com.tothefor.study.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


@Component
public class BeanFactoryPostProcessorImpl implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryPostProcessor】执行开始，容器中已注册的BeanDefinition数量：" + beanFactory.getBeanDefinitionCount());
        // 获取指定Bean的定义信息
        String beanName = "user";
        if (beanFactory.containsBeanDefinition(beanName)) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            // 指定Bean的初始化方法
            beanDefinition.setInitMethodName("initImpl");
            // 指定Bean的销毁方法
            beanDefinition.setDestroyMethodName("destroyMethodImpl");
        }
        System.out.println("【BeanFactoryPostProcessor】执行结束");
    }
}

```

输出：

```bash
【BeanFactoryPostProcessor】执行开始，容器中已注册的BeanDefinition数量：134
【BeanFactoryPostProcessor】执行结束
User无参构造执行...
BeanFactoryPostProcessor的 initMethod方法执行
BeanFactoryPostProcessor的 destroyMethod方法执行
```

### 补充

如果在BeanFactoryPostProcessor的实现类中指定了Bean的init和destroy方法，那么原本Bean的init和destroy方法就会被覆盖。

Bean：

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

    public void initImpl() {
        System.out.println("BeanFactoryPostProcessor的 initMethod方法执行");
    }

    // 销毁方法，对应destroyMethod
    public void destroyMethod() {
        System.out.println("配置Bean destroyMethod方法执行");
    }

    public void destroyMethodImpl() {
        System.out.println("BeanFactoryPostProcessor的 destroyMethod方法执行");
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

输出：

```bash
【BeanFactoryPostProcessor】执行开始，容器中已注册的BeanDefinition数量：135
【BeanFactoryPostProcessor】执行结束
User无参构造执行...
注解方式 initMethod方法执行
接口方式 initMethod方法执行
BeanFactoryPostProcessor的 initMethod方法执行
注解方式 destroyMethod方法执行
接口方式 destroyMethod方法执行
BeanFactoryPostProcessor的 destroyMethod方法执行
```

可以看到，原本通过Bean的配置类指定的init和destroy方法是没有输出的。

## Bean的初始化和销毁

- 初始化阶段执行顺序：构造方法 > @PostConstruct注解方法 > InitializingBean接口的afterPropertiesSet方法 >
  @Bean的initMethod方法（BeanFactoryPostProcessor中指定的inti方法）。

- 销毁阶段执行顺序：@PreDestroy注解方法 > DisposableBean接口的destroy方法 >
  @Bean的destroyMethod方法（BeanFactoryPostProcessor中指定的destroy方法）。

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





