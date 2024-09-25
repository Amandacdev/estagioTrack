package br.edu.ifpb.pweb2.estagiotrack.config;

import br.edu.ifpb.pweb2.estagiotrack.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry
                .addInterceptor(authInterceptor)
                .addPathPatterns("/**","/alunos/**","/empresas/**")
                .excludePathPatterns("/auth/**","/css/**");
    }

}
