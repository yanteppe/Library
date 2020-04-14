package com.library.jsf;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.library.jsf.controller.BookController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "PdfContent", urlPatterns = {"/PdfContent"})
public class PDFContentServlet extends HttpServlet {
   private ApplicationContext context;

   @Override
   public void init() throws ServletException {
      // Получение доступа к Spring контексту (контейнеру)
      context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
   }

   /**
    * Processes requests for both HTTP
    * <code>GET</code> and
    * <code>POST</code> methods.
    *
    * @param request  servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException      if an I/O error occurs
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OutputStream out = response.getOutputStream();
      try {
         // Считываем параметры
         long id = Long.valueOf(request.getParameter("id"));
         long viewCount = Long.valueOf(request.getParameter("viewCount"));
         // Получаем Spring бин
         BookController bookController = ((BookController) context.getBean("bookController"));
         // Получить контент по id
         byte[] content = bookController.getPDFContent(id);
         if (content == null) {
            response.sendRedirect(request.getContextPath() + "/error/error-pdf.html");
         } else {
            response.setContentType("application/pdf");
            // Увеличить кол-во просмотров книги на 1
            bookController.updateViewCount(viewCount, id);
            response.setContentLength(content.length);
            out.write(content);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         out.close();
      }
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      processRequest(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      processRequest(request, response);
   }

   @Override
   public String getServletInfo() {
      return "Short description";
   }
}
