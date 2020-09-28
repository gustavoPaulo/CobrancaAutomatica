package com.CobrancaAutomatica;

import com.CobrancaAutomatica.CobrancaAutomaticaApplication;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
@EntityScan(basePackages = {"com.CobrancaAutomatica.model"})
@ComponentScans({@ComponentScan(basePackages = {"com.*"}), @ComponentScan(basePackageClasses = {CobrancaAutomaticaApplication.class})})
public class CobrancaAutomaticaApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
  public static void main(String[] args) {
    SpringApplication.run(CobrancaAutomaticaApplication.class, args);
  }
  
  @Bean
  public LocaleResolver localeResolver() {
    return (LocaleResolver)new FixedLocaleResolver(new Locale("pt", "BR"));
  }
}
