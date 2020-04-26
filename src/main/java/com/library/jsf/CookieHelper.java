package com.library.jsf;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
   public static final String COOKIE_LANG = "spring-library-lang";

   public static void setCookie(String name, String value, int expiry) {
      var facesContext = FacesContext.getCurrentInstance();
      var request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
      Cookie cookie = null;
      Cookie[] userCookies = request.getCookies();
      if (userCookies != null && userCookies.length > 0) {
         for (var userCookie : userCookies) {
            if (userCookie.getName().equals(name)) {
               cookie = userCookie;
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
      var facesContext = FacesContext.getCurrentInstance();
      HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
      Cookie cookie = null;
      Cookie[] userCookies = request.getCookies();
      if (userCookies != null && userCookies.length > 0) {
         for (var userCookie : userCookies) {
            if (userCookie.getName().equals(name)) {
               cookie = userCookie;
               return cookie;
            }
         }
      }
      return null;
   }
}