package com.crio.cred;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import springfox.documentation.oas.annotations.EnableOpenApi;

import javax.servlet.Filter;

/**
 * The type Cred application.
 *
 * @author harikesh.pallantla
 */
@SpringBootApplication
@EnableOpenApi
public class CredApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CredApplication.class, args);
    }

    /**
     * Shallow etag header filter. With this method the etag header is generated.
     *
     * @return the filter
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(false)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setSkipNullEnabled(true);
        return modelMapper;
    }
}
