package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("newUserName");
                    User user = users.get(name);
                    HashMap m = new HashMap<>();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");

                    } else {

                        m.put("name", user.name);
//                        m.put("password", user.password);
                        m.put("message", user.posts);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/createUser",
                ((request, response) -> {
                    String name = request.queryParams("newUserName");
                    String password = request.queryParams("enterPassword");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        users.put(name, user);
                    }

                    if (password.equals(user.password)) {
                        Session session = request.session();
                        session.attribute("newUserName", name);

                    }

                    //   Session session = request.session();
                    //   session.attribute("userName", name);

                    response.redirect("/");
                    return "";

                })
        );

        Spark.post(
                "/createMessages",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("newUserName");
                    User user = users.get(name);

                    String newMessage = request.queryParams("newMessage");
                    Messages message =new Messages(newMessage);
                    user.posts.add(message);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/editMessage",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("newUserName");
                    User user = users.get(name);
                    String updateMessage = request.queryParams("updateMessage");
                    int n = Integer.parseInt(updateMessage);
                    user.posts.get(n -1);
                    user.posts.remove(n -1);

                    String editMessage = request.queryParams("editMessage");
                    Messages message = new Messages(editMessage);
                    user.posts.add(message);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/delete",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("newUserName");
                    User user = users.get(name);
                    String deleteMessage = request.queryParams("deleteMessage");
                    int n = Integer.parseInt(deleteMessage);
                    user.posts.remove(n -1);
                    response.redirect("/");
                    return "";


                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );


    }
}


