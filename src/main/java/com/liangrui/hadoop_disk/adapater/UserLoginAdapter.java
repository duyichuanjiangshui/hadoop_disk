package com.liangrui.hadoop_disk.adapater;

 

import com.liangrui.hadoop_disk.handler.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**

 * description:

 * author:cb

 * datetime:2019年7月28日  下午6:22:29

 */

 

@SpringBootApplication

@EnableCaching

public class UserLoginAdapter implements WebMvcConfigurer{

	

	@Autowired

	private UserLoginInterceptor userLoginInterceptor;

 

	@Override

	public void addInterceptors(InterceptorRegistry registry) {

		

		//添加对用户是否登录的拦截器，并添加过滤项、排除项

		registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**")

			.excludePathPatterns("/static/**")//排除样式、脚本、图片等资源文件
				.excludePathPatterns("/index/login")//排除登录页面
				.excludePathPatterns("/index/forgetpassword")
				.excludePathPatterns("/index/register")
				.excludePathPatterns("/uploadAndDown/**")
			.excludePathPatterns("/user/**");

		 

	}

	 

}
