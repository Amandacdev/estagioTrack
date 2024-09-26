package br.edu.ifpb.pweb2.estagiotrack.interceptor;

import br.edu.ifpb.pweb2.estagiotrack.model.Aluno;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.sqm.mutation.internal.Handler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
        throws Exception {
        boolean allowed = false;
        HttpSession httpSession = request.getSession(false);

        if(httpSession != null
            && ((Aluno) httpSession.getAttribute("user")) != null){
            allowed = true;
        } else {
            String baseUrl = request.getContextPath();
            String paginaLogin = baseUrl + "/auth";
            response.sendRedirect(response.encodeRedirectURL(paginaLogin));
            allowed = false;
        }

        return allowed;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
        throws Exception{
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView)
        throws Exception {

    }
}
