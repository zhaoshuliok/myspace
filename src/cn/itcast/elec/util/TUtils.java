package cn.itcast.elec.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TUtils {

	/**泛型转换，在父类中的泛型中，获取子类传递的真实类型*/
	public static Class getActualType(Class entity) {
		Type type = entity.getGenericSuperclass();
		//表示没有对Action类产生代理对象
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
			return entityClass;			
		}
		//使用shiro的注解对Action类产生了代理
		else{
			ParameterizedType parameterizedType = (ParameterizedType) entity.getSuperclass().getGenericSuperclass();
			Class entityClass = (Class) parameterizedType.getActualTypeArguments()[0];
			return entityClass;			
		}
	}

}
