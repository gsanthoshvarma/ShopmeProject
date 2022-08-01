package com.shopme.admin.security;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SMvcConfigure implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		Path photoDir = Paths.get("user-photos");
		String completePath = photoDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/ "+dirName+ "/**").addResourceLocations("file:/"+completePath+ "/");
	}

}
