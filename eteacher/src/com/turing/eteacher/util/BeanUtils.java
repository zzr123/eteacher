package com.turing.eteacher.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 处理bean的util类
 * 
 * @author caojian
 */
public class BeanUtils {

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static final Log log = LogFactory.getLog(BeanUtils.class);

	/**
	 * 对象属性值复制把forEntity的对象属性值赋值到toEntity
	 * 
	 * @param forEntity
	 * @param toEntity
	 * @return
	 */
	public static void copyToModel(Object model, Object toModel) {
		Method m = null;
		Method tm = null;
		String filedName = null;
		Object filedValue = null;
		Field[] fields = toModel.getClass().getDeclaredFields();
		for (Field field : fields) {
			filedName = field.getName();
			filedName = filedName.substring(0, 1).toUpperCase()
					+ filedName.substring(1); // 将属性的首字符大写，方便构造get，set方法
			String type = field.getType().toString(); // 获取属性的类型
			if (isListChild(type))// 如果type是类类型，则前面包含"class "，后面跟类名
			{
				try {
					m = model.getClass().getMethod("get" + filedName);
					filedValue = m.invoke(model); // 调用getter方法获取属性值
					if (filedValue != null && !"createTime".equals(field.getName()))// 值为空的不处理，创建时间也不处理。
					{
						tm = toModel.getClass().getMethod("set" + filedName,
								field.getType());
						tm.invoke(toModel, filedValue);
					}
				} catch (NoSuchMethodException e) {
					// 找不到对应的get或set方法就跳过
					// continue;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 
	 * @Title: isListChild
	 * @Description: TODO(判断是否为可替换类型)
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private static boolean isListChild(String str) {
		List strlist = new ArrayList<String>();
		strlist.add("class java.lang.String");
		strlist.add("class java.lang.Integer");
		strlist.add("int");
		strlist.add("class java.lang.Short");
		strlist.add("short");
		strlist.add("class java.lang.Double");
		strlist.add("double");
		strlist.add("class java.lang.Boolean");
		strlist.add("boolean");
		strlist.add("class java.util.Date");
		if (strlist.contains(str) == true) {
			return true;
		}

		return false;
	}
}
