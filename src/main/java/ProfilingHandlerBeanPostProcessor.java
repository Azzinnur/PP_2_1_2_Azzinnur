import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if(beanClass != null) {
            Method[] declaredMethods = beanClass.getDeclaredMethods();
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                for (Method declaredMethod : declaredMethods) {
                    if (method.getName().equals(declaredMethod.getName())) {
                        long before = System.nanoTime();
                        Object methodReturnValue = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println();
                        double result = (double) (after - before) / 1_000_000;
                        System.out.printf("Время работы метода = %.3f миллисекунд \n", result);
                        System.out.println(method.getName());
                        return methodReturnValue;
                    }
                }
                return method.invoke(bean, args);
            });
        }
        return bean;
    }
}
