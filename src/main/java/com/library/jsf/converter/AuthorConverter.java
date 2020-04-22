package com.library.jsf.converter;
import com.google.common.base.Strings;
import com.library.domain.Author;
import com.library.repository.dao.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/*
  Конверторы для преобразования выбранного значение из выпадающего списка в конкректный  объект.
  (В модальном окне создания/редактирования книги)
 */
@FacesConverter(forClass = Author.class)
@Component
public class AuthorConverter implements Converter {
   @Autowired
   private AuthorDAO authorDAO;

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (Strings.isNullOrEmpty(value)) {
         return null;
      }
      return authorDAO.get(Integer.valueOf(value));
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value == null) {
         return null;
      }
      return ((Author)value).getId().toString();
   }
}