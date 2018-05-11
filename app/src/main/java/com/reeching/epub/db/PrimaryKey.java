package com.reeching.epub.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 绍轩 on 2017/11/20.
 */

@Target(ElementType.FIELD) //注解用在字段上
@Retention(RetentionPolicy.RUNTIME)//不加这个不起作用
public @interface PrimaryKey {
}