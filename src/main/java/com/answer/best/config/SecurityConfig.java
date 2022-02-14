package com.answer.best.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.answer.best.dao.UserService;
import com.answer.best.exception.ExpiredException;
import com.answer.best.filter.JwtRequestFilter;
import com.answer.best.store.EndPointStore;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${endpoints}")
	private String urls;

	@Autowired
	UserService userService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Autowired
	ExpiredException expiredException;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userService);
	}

	@Override
	public void configure(HttpSecurity security) throws Exception {
		security.csrf().disable().authorizeRequests()
				.antMatchers(EndPointStore.ADD_QUESTION, EndPointStore.GET_ALL_QUESTIONS, EndPointStore.SAVE_USERANSWER,
						EndPointStore.AUTHENTICATE,"/")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(expiredException).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		security.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
