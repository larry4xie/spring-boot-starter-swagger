package xyz.lxie.springboot.api;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Swagger AutoConfiguration
 *
 * @author xiegang
 * @since 16/5/28
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger.ui", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class SwaggerAutoConfiguration extends WebMvcConfigurerAdapter {
    protected static final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

    @Autowired
    private SwaggerProperties props;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(props.getTitle())
                .description(props.getDescription())
                .version(props.getVersion())
                .termsOfServiceUrl(props.getTermsOfServiceUrl())
                .contact(new Contact(props.getContact(), "", ""))
                .license(props.getLicense())
                .licenseUrl(props.getLicenseUrl())
                .build();

        AlternateTypeRule alternateTypeRule = newRule(
                typeResolver.resolve(DeferredResult.class,
                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class));

        List<ResponseMessage> responseMessages = Lists.newArrayList(
                new ResponseMessageBuilder().code(500).message("500 internal server error").responseModel(new ModelRef("Error"))
                        .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(props.getGroup())
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(excludedPathSelector())
                .build().pathMapping("/")
                .directModelSubstitute(Date.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(alternateTypeRule)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .forCodeGeneration(true)
                .enableUrlTemplating(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .ignoreUnknownPathExtensions(true)
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .useJaf(false)
                .defaultContentType(MediaType.APPLICATION_JSON);

        // this is the key to make spring mvc respond with json result only!
        configurer.defaultContentTypeStrategy(nativeWebRequest -> {
            List<MediaType> medias = new ArrayList<>();
            medias.add(MediaType.APPLICATION_JSON);
            return medias;
        });
    }

    protected Predicate<String> excludedPathSelector() {
        if (Strings.isNullOrEmpty(props.getExcludes())) {
            return any();
        }

        log.info("assembly excluded path selector as per config: {}", props.getExcludes());
        return and(Iterables.transform(Splitter.on(',').trimResults().omitEmptyStrings().split(props.getExcludes()),
                s -> not(ant(s.trim()))));

    }
}
