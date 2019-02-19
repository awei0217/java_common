package tools;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @描述 校验研发定义的实体类类型,使用反射,对性能要求高的请不要使用，不支持嵌套使用,比如Persion中引用一个Student ,那么不支持校验Student的字段是否为空，请注意 基本类型 字段存在默认值，不会做为空校验。
 * @创建人 sunpengwei
 * @创建时间 2016/12/16.
 * @邮箱
 */
public class ValidateClass {
	/**
	 *
	 * @param obj  需要校验的实体,参数中存在null  或者 ""  或者"  "  返回false,否则返回true
	 * @param flag 如果flag 为true  跳过这些参数；如果flag为false,则只检验这些参数
	 * @param param 如果param 为null  则校验全部;
	 * @return
	 */
	public static boolean validateClass(Object obj, ArrayList<String> param,Boolean flag){
		if(obj == null){
			return false;
		}
		/*校验引用类型*/
		return validateQuoteType(obj,param,flag);
	}
	private static boolean validateQuoteType(Object obj, ArrayList<String> param,Boolean flag) {
			if(flag){ //跳过param中参数
				return skipValidateParam(obj,param);
			}else{ //只校验param中参数
				return onlyValidateParam(obj,param);
			}
	}
	/**
	 * 只校验param中参数
	 * @param obj
	 * @param param
	 * @return
	 */
	private static boolean onlyValidateParam(Object obj, ArrayList<String> param) {
		try{
			Field[] fields = obj.getClass().getDeclaredFields();
			if(param!=null && param.size()>0){
				for(Field field:fields){
					field.setAccessible(true);
					if ((param.contains(field.getName()))) {
						Object value = field.get(obj);
						if (value == null) {
							return false;
						}
						if (field.getType().getName().equals("java.lang.String")) {
							String str = (String) value;
							if (StringUtils.isEmpty(str)) {
								return false;
							}
						}
					}
				}
			}else{
				return validateAll(fields,obj);
			}
		}catch (Exception e){
			throw new RuntimeException("检验参数为空时抛出异常",e);
		}
		return true;
	}


	/**
	 * 跳过param中参数的检验
	 * @param obj
	 * @param param
	 * @return
	 */
	private static boolean skipValidateParam(Object obj, ArrayList<String> param) {
		try{
			Field[] fields = obj.getClass().getDeclaredFields();
			if(param!=null && param.size()>0){
				for(Field field:fields){
					field.setAccessible(true);
					if (!(param.contains(field.getName()))) {
						Object value = field.get(obj);
						if (value == null) {
							return false;
						}
						if (field.getType().getName().equals("java.lang.String")) {
							String str = (String) value;
							if (StringUtils.isEmpty(str)) {
								return false;
							}
						}
					}
				}
			}else{
				return validateAll(fields,obj);
			}
		}catch (Exception e){
			throw new RuntimeException("检验参数为空时抛出异常",e);
		}
		return true;
	}


	/**
	 * 校验所有
	 * @param fields
	 * @param obj
	 * @return
	 */
	private static boolean validateAll(Field[] fields, Object obj) {
		try {
			for(Field field:fields){
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value == null) {
					return false;
				}
				if (field.getType().getName().equals("java.lang.String")) {
					String str = (String) value;
					if (StringUtils.isBlank(str)) {
						return false;
					}
				}
			}
		}catch (Exception e){
			throw new RuntimeException("检验参数为空时抛出异常");
		}
		return true;
	}

	public static void main(String[] args) {

	}
}
