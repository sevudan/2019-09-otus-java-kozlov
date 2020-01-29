package ru.otus.hw12.jetty.servlet;

import com.google.gson.Gson;
import ru.otus.hw12.hibernate.dbservice.DbServiceUser;
import ru.otus.hw12.model.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

public class UsersApiServlet extends HttpServlet {

  private static final int ID_PATH_PARAM_POSITION = 1;
  private final DbServiceUser dbServiceUser;
  private final Gson gson;

  public UsersApiServlet(DbServiceUser dbServiceUser, Gson gson) {
    this.dbServiceUser = dbServiceUser;
    this.gson = gson;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream out = response.getOutputStream();

    List<User> userList = new ArrayList<>();
    dbServiceUser.getUsers().forEach(obj -> {
            User user = new User();
            user.setId(obj.getId());
            user.setUsername(obj.getUsername());
            user.setLogin(obj.getLogin());
            user.setPassword(obj.getPassword());
            userList.add(user);
      }
    );
    out.print(gson.toJson(userList));
    response.setStatus(SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String jsonObj = request.getReader().readLine();
    User newUser = gson.fromJson(jsonObj, User.class);
    dbServiceUser.saveUser(newUser);
    response.setStatus(SC_OK);
  }
}
