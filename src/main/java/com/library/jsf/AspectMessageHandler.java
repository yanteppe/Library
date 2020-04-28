package com.library.jsf;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.logging.Level;

@Log
@Aspect
@Component
public class AspectMessageHandler {
   // @Around - выполняеть до и после метода указанного в ("execution..")
   @Around("execution(* com.library.jsf.controller.*.delete(..))")
   public void deleteConstraint(ProceedingJoinPoint proceedingJoinPoint) {
      FacesContext context = FacesContext.getCurrentInstance();
      ResourceBundle bundle = context.getApplication().getResourceBundle(context, "lang");
      try {
         proceedingJoinPoint.proceed(); // выполнить метод
         context.addMessage(null, new FacesMessage(null, bundle.getString("deleted")));
         RequestContext.getCurrentInstance().update("info");
      } catch (Throwable throwable) {
         if (throwable instanceof UnexpectedRollbackException) {// если транзакция откатилась
            log.log(Level.WARNING, throwable.getMessage());
            throwable.printStackTrace();
            // Если ошибка constraint при удалении
            // if (((UnexpectedRollbackException) throwable).getMostSpecificCause() instanceof  com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
            if (((UnexpectedRollbackException) throwable).getMostSpecificCause() instanceof SQLIntegrityConstraintViolationException) {
               // Отобразить сообщение
               context.addMessage(null, new FacesMessage(null, bundle.getString("constraint_delete_record")));
            }
         }
      }
      // Обновление компонента для отображения сообщения
      RequestContext.getCurrentInstance().update("info");
   }

   @Around("execution(* com.library.repository.dao.*.save(..))")
   public void addNewSprValue(ProceedingJoinPoint proceedingJoinPoint) {
      try {
         Object obj = proceedingJoinPoint.proceed();
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle bundle = context.getApplication().getResourceBundle(context, "lang");
         // Отобразить сообщение пользователю на странице
         context.addMessage(null, new FacesMessage(null, bundle.getString("added") + ": \"" + obj.toString() + "\""));
         RequestContext.getCurrentInstance().update("info");
      } catch (Throwable throwable) {
         log.log(Level.WARNING, throwable.getMessage());
         throwable.printStackTrace();
      }
   }
}
