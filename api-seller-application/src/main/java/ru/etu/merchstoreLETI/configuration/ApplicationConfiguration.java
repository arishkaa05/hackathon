package ru.etu.merchstoreLETI.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ru.etu.merchstoreLETI.security.JwtTokenFilter;
import ru.etu.merchstoreLETI.security.JwtTokenProvider;


/**
 * Класс используется для настройки параметров безопасности в приложении.
 * Также для настройки необходимых бинов и Swagger Open API.
 * '@EnableWebSecurity' - включает модуль безопасности Spring Web в приложении.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfiguration {
    private final JwtTokenProvider tokenProvider;

    /**
     * Создает экземпляр класса BCryptPasswordEncoder для использования в приложении.
     *
     * @return BCryptPasswordEncoder, используемый для хеширования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает AuthenticationManager для использования в процессе аутентификации.
     *
     * @return AuthenticationManager, используемый в приложении
     * @throws Exception, если возникает ошибка при создании AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Создает и настраивает экземпляр OpenAPI для API LETI магазина товаров.
     *
     * @return Экземпляр OpenAPI с настройками безопасности, компонентами и информацией API.
     */
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Merch Store LETI API")
                        .description("Spring Boot Application")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Владислав")
                                .email("ooovladislavchik@gmail.com")
                                .url("https://t.me/ProstoVladTut")));
    }

    /**
     * Создает цепочку фильтров безопасности HTTP.
     *
     * @param httpSecurity Объект конфигурации HTTP безопасности.
     * @return Цепочка фильтров безопасности HTTP.
     * @throws Exception В случае ошибок конфигурации HTTP безопасности.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    cors.configurationSource(request -> {
                        CorsConfiguration corsConfig = new CorsConfiguration();
                        corsConfig.addAllowedOrigin("http://localhost:5173");
                        corsConfig.addAllowedMethod("*");
                        corsConfig.addAllowedHeader("*");
                        corsConfig.setAllowCredentials(true); // Set credentials to true
                        return corsConfig;
                    });
                })
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling->exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("Unauthorized");
                        })
                        .accessDeniedHandler(((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());;
                            response.getWriter().write("Unauthorized");
                        })))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/subcategory/all").permitAll()
                        .requestMatchers("/api/v1/subcategory/all/pagination").permitAll()
                        .requestMatchers("/api/v1/subcategory/findById/**").permitAll()
                        .requestMatchers("/api/v1/subcategory/findById/pagination/**").permitAll()
                        .requestMatchers("/api/v1/subcategory/findById/{id}/withProducts").permitAll()
                        .requestMatchers("/api/v1/subcategory/findByTitleUrl/{titleUrl}/withProducts").permitAll()
                        .requestMatchers("/api/v1/subcategory/{id}/products").permitAll()
                        .requestMatchers("/api/v1/subcategory/byCategory/{categoryId}").permitAll()
                        .requestMatchers("/api/v1/product/findById/**").permitAll()
                        .requestMatchers("/api/v1/product/{productId}/subcategories").permitAll()
                        .requestMatchers("/api/v1/product/all").permitAll()
                        .requestMatchers("/api/v1/product/search").permitAll()
                        .requestMatchers("/api/v1/product/all/pagination").permitAll()
                        .requestMatchers("/api/v1/product/new-products").permitAll()
                        .requestMatchers("/api/v1/product/byCategory/{categoryId}").permitAll()
                        .requestMatchers("/api/v1/product/{productId}/categories").permitAll()
                        .requestMatchers("/api/v1/product/findByTitleForUrl/{titleUrl}").permitAll()
                        .requestMatchers("/api/v1/comment/findById/**").permitAll()
                        .requestMatchers("/api/v1/comment/product/{productId}/withUserUsername").permitAll()
                        .requestMatchers("/api/v1/comment/all").permitAll()
                        .requestMatchers("/api/v1/comment/all/pagination").permitAll()
                        .requestMatchers("/api/v1/comment/search").permitAll()
                        .requestMatchers("/api/v1/comment/product/{productId}").permitAll()
                        .requestMatchers("/api/v1/category/all").permitAll()
                        .requestMatchers("/api/v1/category/all/withSubcategories").permitAll()
                        .requestMatchers("/api/v1/category/findByTitle/{title}/withSubcategories").permitAll()
                        .requestMatchers("/api/v1/category/all/pagination").permitAll()
                        .requestMatchers("/api/v1/category/findById/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
