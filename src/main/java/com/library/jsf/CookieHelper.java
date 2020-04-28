package com.library.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
   public static final String COOKIE_LANG = "spring-library-lang";

   public static void setCookie(String name, String value, int expiry) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
      Cookie cookie = null;
      Cookie[] userCookies = request.getCookies();
      if (userCookies != null && userCookies.length > 0 ) {
         for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(name)) {
               cookie = userCookies[i];
               break;
            }
         }
      }
      // Для перезаписи существубщего cookie name и path должны совпадать (Иначе может создасться cookie с одним именем но разными path)
      if (cookie != null) {
         cookie.setValue(value);
      } else {
         cookie = new Cookie(name, value);
      }
      cookie.setPath(request.getContextPath());
      cookie.setMaxAge(expiry);
      var response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
      response.addCookie(cookie);
   }

   public static Cookie getCookie(String name) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
      Cookie cookie = null;
      Cookie[] userCookies = request.getCookies();
      if (userCookies != null && userCookies.length > 0 ) {
         for (int i = 0; i < userCookies.length; i++) {
            if (userCookies[i].getName().equals(name)) {
               cookie = userCookies[i];
               return cookie;
            }
         }
      }
      return null;
   }
}