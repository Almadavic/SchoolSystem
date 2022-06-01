package application.security;

import application.filter.AuthenticationJWTFilter;
import application.repository.UserRepository;
import application.service.serviceLayer.TokenService;
import application.service.serviceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter { // Classe de segurança a nivel de prod e test

		//////
	@Autowired
	private UserService userSerivice;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {    // AuthManager
		return super.authenticationManager();
	}
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSerivice).passwordEncoder(new BCryptPasswordEncoder()); // Aqui diz como será a autenticação, puxando a classeserService que tem esse papel.
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/auth").permitAll()
				.antMatchers(HttpMethod.GET,"/classes").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/classes/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/classes/{id}/students").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.GET,"/classes/{id}/students/{id}").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.GET,"/classes/{id}/teacher").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.PUT,"/classes/{idClass}/students/{idStudent}/updategrades").hasRole("TEACHER")
				.antMatchers(HttpMethod.POST,"/classes/createclassroom").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/setteacher").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/addstudent").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/removestudent").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/removeteacher").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.DELETE,"/classes/{id}/removeclass").hasRole("PRINCIPAL")    // TERMINOU TODAS DO CONTROLLER ClassRoomController
				.antMatchers("/userarea/**").authenticated()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AuthenticationJWTFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	//Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/**.html",
						"/v3/api-docs/**",
						"/webjars/**",
						"/configuration/**",
						"/swagger-resources/**",
						"/swagger-ui/**",
						"/h2-console/**");

	}
	
}
