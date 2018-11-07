package tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sunpengwei
 * @date 2017/9/14
 */
public class ApplicationContextUtils implements ApplicationContextAware {


	private static ApplicationContext applicationContext;

	/**
	 * 根据beanId 获取bean实例
	 * @param beanName
	 * @param <T>
	 * @return 返回的对象
	 */
	public static <T> T getBean(String beanName) throws BeansException{

		return (T) applicationContext.getBean(beanName);
	}


	/**
	 *
	 * @param clazz 根据class来获取类对象
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException{

		return (T) applicationContext.getBean(clazz);
	}

	/**
	 * 根据beanName 和 clazz 获取对象
	 * @param beanName
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(String beanName ,Class<T> clazz) throws BeansException{

		return (T) applicationContext.getBean(beanName,clazz);
	}

	/**
	 * 根据beanName 和 参数 获取对象
	 * @param beanName
	 * @param objects
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(String beanName ,Object ... objects) throws BeansException{

		return (T) applicationContext.getBean(beanName,objects);
	}

	/**
	 * 根据过个beanName 获取对象
	 * @param beanNames
	 * @return
	 * @throws BeansException
	 */
	public static Collection<Object> getBeans(String... beanNames) throws BeansException {
		Collection<Object> beans = new ArrayList<Object>();
		for (String name : beanNames) {
			beans.add(applicationContext.getBean(name));
		}
		return beans;
	}

	/**
	 * 根据beanClass类型获取该bean的所有实例 （因为一个接口会有多个实现类）
	 * @param clazz
	 * @return Map key为beanName value 为bean对象
	 * @throws BeansException
	 */
	public static Map<?, ?> getBeans(Class<?> clazz) throws BeansException {
		Map<String, Object> beans = new HashMap<String, Object>();
		String[] names = applicationContext.getBeanNamesForType(clazz);
		for (String name : names) {
			beans.put(name, applicationContext.getBean(name));
		}
		return beans;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}


	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * @param name
	 * @return
	 */
	public static String[] getAliases(String name) {
		return applicationContext.getAliases(name);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtils.applicationContext = applicationContext;
	}


}
