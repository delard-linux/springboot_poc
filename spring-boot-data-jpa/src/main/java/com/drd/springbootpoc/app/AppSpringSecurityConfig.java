package com.drd.springbootpoc.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppSpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

		PasswordEncoder encoder = passwordEncoder();

		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		builder.inMemoryAuthentication()
			.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
			.withUser(users.username("david").password("12345").roles("USER"));

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/listar","/css/**","/js/**","/images/**").permitAll()
			.antMatchers("/ver/**").hasAnyRole("USER")
			.antMatchers("/uploads/**").hasAnyRole("USER")
			.antMatchers("/form/**").hasAnyRole("ADMIN")
			.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
			.antMatchers("/factura/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
		;


	}
	
	
	

}
