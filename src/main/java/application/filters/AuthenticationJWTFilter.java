package application.filters;

import application.entities.users.User;
import application.repositories.UserRepository;
import application.services.serviceLayer.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationJWTFilter extends OncePerRequestFilter { // Filtro de autenticação! antes da API chamar a classe de segurança, ela vai chamar essa aqui antes.
                                                                      //(NAS REQUISIÇÕES), NÃO QUANDO SOBE A APLICAÇÃO.
    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationJWTFilter(TokenService tokenService, UserRepository userRepository) { // Em filtros, não podemos fazer injeção de dependência automática.
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) // Método para prosseguir com a requisição!
            throws ServletException, IOException {
        String token = recoverToken(request);
        boolean valid = tokenService.isTokenValid(token); // retorna V/F se o token está valido
        if (valid) { // se o token estiver válido
            recoverUser(token); // autentica o usuário.
        }
        filterChain.doFilter(request, response);
    }

    private void recoverUser(String token) { // Método para recuperar o usuário e valida-lo através do token.
        Long idUser = tokenService.getIdUsuario(token);
        User user = userRepository.findById(idUser).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) { // Método para recuperar o token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
