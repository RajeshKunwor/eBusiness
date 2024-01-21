package Authentication.Filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Authentication.Beans.AutenticationBean;

/**
 *
 * @author anil
 */
//Login filter class which is responsible for filtering user actitvities
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {  }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse resp = (HttpServletResponse) response;
       //Retrieve the authtication bean
       AutenticationBean session = (AutenticationBean) req.getSession().getAttribute("authBean");
       //Any after login accessible pages should be listed here
       String[] afterLog = {"logout.xhtml", "default.xhtml","searchOrderResult.faces",
       "searchOrder.faces", "searchMovie.faces","searchMovieResult.faces","searchGame.faces",
       "searchGameResult.faces","searchCustomer.faces","searchCustomeResult.faces",
       "listOrders.faces","listGames.faces","listCustomers.faces","customers.faces",
       "createNewOrder.faces","createNewCustomer.faces","createNewGame.faces",
       "createNewMovie.faces"
       
       
       
       };
       String url=req.getRequestURI();
       if (session==null || !session.isLogged()) {
           boolean risk=false;
           for (int i=0; i<afterLog.length; i++) {
                if (url.indexOf(afterLog[i])>=0) 
                {risk=true;
                break;
                }
           }
           if (risk) {
               resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
           } 
           else {
               chain.doFilter(request, response);
           }
       } else {
           if (url.indexOf("register.xhtml")>=0 || url.indexOf("login.xhtml")>=0
                   || url.indexOf("emailVerification.xhtml")>=0 || url.indexOf("emailRecovery.xhtml")>=0
                   || url.indexOf("userRecovery.xhtml")>=0 ) {
               resp.sendRedirect(req.getServletContext().getContextPath()+"/default.xhtml");
           }
           else if (url.indexOf("logout.xhtml")>=0) {
               req.getSession().removeAttribute("authBean");
               resp.sendRedirect(req.getServletContext().getContextPath()+"/login.xhtml");
           }
           else {
               chain.doFilter(request, response);
           }
       }
    }
    @Override
    public void destroy() {  }
    
}
