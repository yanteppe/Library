package com.library;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticateHandler implements AuthenticationFailureHandler {
   private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

   @Override
   public void onAuthenticationFailure(HttpServletRequest httpServletRequest,HttpServletResponse response,
                                       AuthenticationException exception) throws IOException, ServletException {
      HttpSession session = httpServletRequest.getSession(false);
      if (session != null) session.setAttribute("loginFailed", "login failed");
      if (response.isCommitted()) return;
      redirectStrategy.sendRedirect(httpServletRequest, response, "index.xhtml");
   }
}
