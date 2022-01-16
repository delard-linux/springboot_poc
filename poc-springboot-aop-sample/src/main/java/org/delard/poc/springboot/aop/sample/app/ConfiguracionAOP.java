package org.delard.poc.springboot.aop.sample.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("org.delard.poc.springboot.aop.sample.app")
public class ConfiguracionAOP {

}
