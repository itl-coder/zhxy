package com.example.zhxy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    /**
     * 管理员分组
     *
     * @return
     */
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理员")
                .apiInfo(adminApiInfo())
                .select()
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
    }

    /**
     * 教职工分组
     *
     * @return
     */
    @Bean
    public Docket teacherApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("教职工")
                .apiInfo(teacherApiInfo())
                .select()
                .paths(PathSelectors.regex("/teacher/.*"))
                .build();
    }

    /**
     * 普通用户分组
     *
     * @return
     */
    @Bean
    public Docket studentApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("普通用户")
                .apiInfo(studentApiInfo())
                .select()
                .paths(PathSelectors.regex("/user/.*"))
                .build();
    }


    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理系统API文档")
                .description("本文档描述了管理员接口测试")
                .version("1.6")
                .contact(new Contact("", "", ""))
                .build();
    }

    private ApiInfo teacherApiInfo() {
        return new ApiInfoBuilder()
                .title("网站API文档")
                .description("本文档描述了教职工接口测试")
                .version("1.6")
                .contact(new Contact("", "", ""))
                .build();
    }


    private ApiInfo studentApiInfo() {
        return new ApiInfoBuilder()
                .title("网站API文档")
                .description("本文档描述了普通用户接口文档测试")
                .version("1.6")
                .contact(new Contact("", "", ""))
                .build();
    }
}