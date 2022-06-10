package application.security;

import application.filters.AuthenticationJWTFilter;
import application.repositories.UserRepository;
import application.services.serviceLayer.TokenService;
import application.services.serviceLayer.UserService;
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
public class SecurityConfigurations extends WebSecurityConfigurerAdapter { // Classe de segurança a nivel de prod e test com segurança!

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

				// CONF DE SEGURANÇA AUTENTICAÇÃO CONTROLLER
				.antMatchers("/auth").permitAll()

				// CONF DE SEGURANÇA CLASSROOM CONTROLLER
				.antMatchers(HttpMethod.GET,"/classes").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/classes/{id}").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.GET,"/classes/{id}/students").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.GET,"/classes/{id}/students/{id}").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.GET,"/classes/{id}/teacher").hasAnyRole("PRINCIPAL","TEACHER")
				.antMatchers(HttpMethod.PUT,"/classes/{idClass}/students/{idStudent}/updategrades").hasRole("TEACHER")
				.antMatchers(HttpMethod.POST,"/classes/createclassroom").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/setteacher").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/addstudent").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/removestudent").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.PUT,"/classes/{id}/removeteacher").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.DELETE,"/classes/{id}/removeclass").hasRole("PRINCIPAL")

				// CONF DE SEGURANÇA ÁRE DO USUÁRIO CONTROLLER
				.antMatchers("/userarea/**").authenticated()

				// CONF DE SEGURANÇA STUDENTS CONTROLLER
				.antMatchers(HttpMethod.GET,"/students").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/students/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.POST,"/students/register").hasRole("PRINCIPAL")

				// CONF DE SEGURANÇA TEACHER CONTROLLER
				.antMatchers(HttpMethod.GET,"/teachers").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/teachers/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.POST,"/teachers/register").hasRole("PRINCIPAL")

				// // CONF DE SEGURANÇA USER CONTROLLER
				.antMatchers(HttpMethod.GET,"/users").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.GET,"/users/{id}").hasRole("PRINCIPAL")
				.antMatchers(HttpMethod.DELETE,"/users/{id}/remove").hasRole("PRINCIPAL")

		.and().csrf().disable() // DESABILITA SEGURANÇA CONTRA INVASÃO, DESATIVAMOS ISSO POIS NÃO VAI AFETAR A APLICAÇÃO (A APLICAÇÃO É STATELESS)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // DIZ QUE A APLICAÇÃO NÃO TEM ESTADO, NÃO GUARDA INFORMAÇÕES DE LOGIN!
		.and().addFilterBefore(new AuthenticationJWTFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class); // Adiciona um FILTRO, Antes
	}                                                                                                                                  // de chegar nessa classe.
	
	
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
