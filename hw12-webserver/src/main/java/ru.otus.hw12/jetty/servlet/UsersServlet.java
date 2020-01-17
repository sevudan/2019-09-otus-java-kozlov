package ru.otus.hw12.jetty.servlet;

import ru.otus.hw12.jetty.services.TemplateProcessor;
import ru.otus.hw12.jetty.services.dbservice.DbServiceUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class UsersServlet extends HttpServlet {

  private static final String USERS_PAGE_TEMPLATE = "admin.html";
  private final DbServiceUser dbServiceUser;
  private final TemplateProcessor templateProcessor;

  public UsersServlet(TemplateProcessor templateProcessor, DbServiceUser dbServiceUser) {
    this.templateProcessor = templateProcessor;
    this.dbServiceUser = dbServiceUser;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, Collections.emptyMap()));
  }
}
