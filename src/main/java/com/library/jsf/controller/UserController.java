package com.library.jsf.controller;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.ResourceBundle;

@Component
@ManagedBean
@ViewScoped
@Getter @Setter
public class UserController {
   private String username;
   private String password;

   public User getCurrentUser() {
      return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   }

   /**
    * Проверка роли пользователя
    *
    * @param role роль пользователя в системе
    * @return boolean
    */
   public boolean role(String role) {
      return getCurrentUser().getAuthorities().stream().filter(x -> x.getAuthority().equals("ROLE_" + role)).count() > 0;
   }

   /**
    * Сообщение о верности/не верности ввода логина или пароля
    *
    * @return String, сообщение при не верной авторизации
    */
   public String getLoginFailedMessage() {
      Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginFailed");
      if (object == null) return "";
      var context = FacesContext.getCurrentInstance();
      ResourceBundle bundle = context.getApplication().getResourceBundle(context, "lang");
      var message = bundle.getString("login_failed");
      FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginFailed");
      if (Strings.isNullOrEmpty(message)) {
         return "";
      } else {
         return message;
      }
   }
}
