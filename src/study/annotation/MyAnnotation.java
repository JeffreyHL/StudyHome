package study.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 
 * @author HL
 */

@Target({ElementType.METHOD, ElementType.TYPE}) // 注解使用范围。取值ElementType：CONSTRUCTOR-构造器；FIELD-域；LOCAL_VARIABLE-局部变量；
							// METHOD-方法；PACKAGE-包；PARAMETER-参数；TYPE-类、接口（包括注解类型）、enum声明
@Retention(RetentionPolicy.RUNTIME) // 注解的生命周期。取值RetentionPolicy：SOURCE-源文件；CLASS-class文件；RUNTIME-运行时
@Documented // 注解暴露为公共API，可以被文档化
@Inherited // 标注过的类的子类继承类注解
// 定义注解格式： public @interface 注解名 {定义体}
public @interface MyAnnotation {

	// 参数数据类型：所有基本类型、Class、String、enum、Annotation、以上所有类型的数组
	public int _int() default 0;

	public int[] _intArry() default { 0, 1, 2 };

	public enum type {
		BIG, SMALL, NORMAL
	};
	
	public Annotation _annotation() default @Annotation("Hello Default!");
	
	@SuppressWarnings("rawtypes")
	public Class _class() default Object.class;
}
