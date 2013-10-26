package fr.esiea.fc.control;

import fr.esiea.fc.services.UrlValidatorService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Check the user's URL for his access
 * @author Dispa Cécile
 */
public class UrlFilter implements javax.servlet.Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        UrlValidatorService validar = new UrlValidatorService();

        int resVal = validar.validateUrl(req);

        switch (resVal) {
            case 0:
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("error", "Vous n'avez pas les droits.");
                request.getRequestDispatcher("index.htm").forward(request, response);
                break;
            case 2:
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("error", "Veuillez vous connecter avec l'utilisateur demandé.");
                request.getRequestDispatcher("index.htm").forward(request, response);
                break;
            case 1: //OK and continue
                chain.doFilter(request, response);
                break;
            case 3:
                request.setAttribute("error", "Il est obligatoire de changer de mot de passe.");
                request.getRequestDispatcher("setPassword.form").forward(request, response);
                
                break;
        }
    }

    public void destroy() {
    }
}
