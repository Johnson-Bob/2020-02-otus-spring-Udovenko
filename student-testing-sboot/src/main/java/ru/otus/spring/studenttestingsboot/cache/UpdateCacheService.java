package ru.otus.spring.studenttestingsboot.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import ru.otus.spring.studenttestingsboot.annotation.UpdateCache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateCacheService implements BeanPostProcessor {
    private Map<Object, Set<Method>> annotatedMethods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Set<Method> methodNames = Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(UpdateCache.class))
                .collect(Collectors.toSet());
        if (!methodNames.isEmpty()) {
            annotatedMethods.put(bean, methodNames);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void updateCache() {
        for (Map.Entry<Object, Set<Method>> beanSetEntry : annotatedMethods.entrySet()) {
            for (Method method : beanSetEntry.getValue()) {
                method.setAccessible(true);
                ReflectionUtils.invokeMethod(method, beanSetEntry.getKey());
            }
        }
    }
}
