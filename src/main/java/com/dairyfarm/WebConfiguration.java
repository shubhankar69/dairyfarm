//package com.dairyfarm;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class WebConfiguration extends WebMvcConfigurationSupport   {
//
//  @Override
//  public void addViewControllers(ViewControllerRegistry registry) {
//      registry.addViewController("/{path:\\w+}")
//            .setViewName("forward:/");
//      registry.addViewController("/**/{path:\\w+}")
//            .setViewName("forward:/");
//      registry.addViewController("/{path:\\w+}/**{path:?!(\\.js|\\.css)$}")
//            .setViewName("forward:/");
//  }
//}
