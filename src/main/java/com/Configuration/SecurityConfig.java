package com.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Helper.JwtAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	String[] allowedUrl = { "/v3/api-docs/**", "/api-docs.yaml", "/swagger-ui/**", "/swagger-resources/**",
			"/webjars/**" };

	@Autowired
	private JwtAuthenticationEntryPoint Point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())

				.authorizeHttpRequests(auth -> auth.requestMatchers("/").authenticated()
						.requestMatchers("/auth/signUp/Customer", "/auth/login/customer", "/auth/login/customerWithOtp",
								"/cart/**", "/order/**", "/sellerApp/**")
						.permitAll().requestMatchers(allowedUrl).permitAll().anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(Point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	// AIzaSyBr55CoPEN38ircJBgP9WkaG6GDrqBTz5I

}
