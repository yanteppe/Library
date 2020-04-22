package com.library.jsf.converter;

import com.google.common.base.Strings;
import com.library.domain.Publisher;
import com.library.repository.dao.PublisherDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Component
@FacesConverter(forClass = Publisher.class)
public class PublisherConverter implements Converter {
   @Autowired
   private PublisherDAO publisherDAO;

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (Strings.isNullOrEmpty(value)) {
         return null;
      }
      return publisherDAO.get(Integer.valueOf(value));
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (value == null) {
         return null;
      }
      return ((Publisher) value).getId().toString();
   }
}
