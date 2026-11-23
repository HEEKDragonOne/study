package com.tothefor.study.beans;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
public class ControllerLogPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 仅处理标注了@RestController的Bean
        if (bean.getClass().isAnnotationPresent(RestController.class)) {
            log.info("【日志增强】为Controller[{}]创建CGLIB代理对象", beanName);
            // CGLIB 通过继承目标类生成代理子类，代理类保留 @RestController / @GetMapping 注解
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                // Object 的 toString/equals/hashCode 等方法不做增强，避免干扰
                if (method.getDeclaringClass() == Object.class) {
                    return proxy.invokeSuper(obj, args);
                }
                long startTime = System.currentTimeMillis();
                String className = bean.getClass().getSimpleName();
                String methodName = method.getName();
                // 打印入参
                log.info("【接口请求】{}.{} 入参：{}", className, methodName, JSON.toJSONString(args));
                Object result;
                try {
                    // 调用父类（即原始 Controller）的真实方法
                    result = proxy.invokeSuper(obj, args);
                    // 打印出参
                    log.info("【接口响应】{}.{} 出参：{}", className, methodName, JSON.toJSONString(result));
                } catch (Throwable e) {
                    log.error("【接口异常】{}.{} 执行异常", className, methodName, e);
                    throw e;
                } finally {
                    // 打印响应时间
                    long endTime = System.currentTimeMillis();
                    log.info("【接口耗时】{}.{} 执行耗时：{}ms", className, methodName, (endTime - startTime));
                }
                return result;
            });
            return enhancer.create();
        }
        return bean;
    }
}
