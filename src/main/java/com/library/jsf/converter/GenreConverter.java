package com.library.jsf.converter;

import com.google.common.base.Strings;
import com.library.domain.Genre;
import com.library.repository.dao.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Component
@FacesConverter(forClass = Genre.class)
public class GenreConverter implements Converter {
   @Autowired
   private GenreDAO genreDAO;

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (Strings.isNullOrEmpty(value)) {
         return null;
      }
      return genreDAO.get(Integer.valueOf(value));
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value == null) {
         return null;
      }
      return ((Genre) value).getId().toString();
   }
}
