package br.com.joaoverissimo.curso.pontointeligente.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.joaoverissimo.curso.pontointeligente.security.util.JwtTokenUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfig {

    private static final Logger LOGGUER = LoggerFactory.getLogger(SwaggerConfig.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select() //
                .apis(RequestHandlerSelectors.basePackage("br.com.joaoverissimo.curso.pontointeligente")) //
                .paths(PathSelectors.any()) //
                .build() //
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() //
                .title("App ponto inteligente") //
                .version("1.0") //
                .description("Api Curso") //
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        String token;
        try {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername("admin@kazale.com");
            token = "Bearer " + this.jwtTokenUtil.obterToken(userDetails);
        } catch (Exception e) {
            LOGGUER.error("Erro de autenticação no Swagger", e);
            token = "";
        }

        return new SecurityConfiguration(null, null, null, null, token, ApiKeyVehicle.HEADER, "Authorization", ",");
    }

}
