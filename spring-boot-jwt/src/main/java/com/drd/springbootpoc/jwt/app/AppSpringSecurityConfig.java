package com.drd.springbootpoc.jwt.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.drd.springbootpoc.jwt.app.auth.handler.LoginSuccessHandler;
import com.drd.springbootpoc.jwt.app.model.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class AppSpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler; 

	@Autowired
	private JpaUserDetailsService userDetailsService; 
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**","/js/**","/images/**","/locale").permitAll()
			//Configuracion de seguridad de la consola H2 de SpringBoot
			.antMatchers("/h2-console/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
			.and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
			.and().headers().frameOptions().sameOrigin()//allow use of frame to same origin urls
			.and()
			    .formLogin()
			    	.successHandler(loginSuccessHandler)
			        .loginPage("/login")
			        .permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/error_403")
			.and()
				.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
}
