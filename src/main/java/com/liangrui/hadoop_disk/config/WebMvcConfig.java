package com.liangrui.hadoop_disk.config;






import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**

 * <pre>

 * @Description:

 * @Aouth: cao_wencao

 * @Date: 2019-01-23 17:28

 * </pre>

 */

@Configuration

public class WebMvcConfig implements  WebMvcConfigurer {

    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/yunpansysimg/**").addResourceLocations("file:D://yunpansysimg/");

    }

}
