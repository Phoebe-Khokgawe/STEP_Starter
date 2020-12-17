// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

   private int numOfComments = 1;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Comments").addSort("time", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    ArrayList<String> toSendOver = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        System.out.println(entity);
        String title = (String) entity.getProperty("comment");
        toSendOver.add(title);
    }

    Gson gson = new Gson();

    response.setContentType("text/json");
    System.out.println(toSendOver.toString());
    response.getWriter().println(gson.toJson(toSendOver));
    
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "comment", "");
    String quantity = getParameter(request,"quantity","4");
    long time = System.currentTimeMillis();
    numOfComments = Integer.parseInt(quantity.equals("")?"1":quantity);
    
    boolean upperCase = Boolean.parseBoolean(getParameter(request, "upper-case", "false"));
    
    // Convert the text to upper case.
    if (upperCase) {
      text = text.toUpperCase();
    }

    //@TODO color still need to be implemented 

    // Respond with the result.
    // response.setContentType("text/html;");
    // response.getWriter().println("Thanks for your comment: \n\"" + text + "\"\n Hope you have a good day!");

    //entity is an object to add to datastore for storing the comments perminantly
    Entity commentsEntity = new Entity("Comments");
    commentsEntity.setProperty("comment", text);
    commentsEntity.setProperty("time", time);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentsEntity);
    response.sendRedirect("/postRequest.html");
  }

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    return value == null ? defaultValue : value;
  }

}

