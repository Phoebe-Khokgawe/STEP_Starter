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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    ArrayList<String> strings = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    ArrayList<String> jsonString = new ArrayList<>();
    addToList();
    for(String obj : strings){
        jsonString.add(convertToJSON(obj));
    }

    response.setContentType("text/html;");
    int randomIndex = (int)Math.floor(Math.random() * ((int)jsonString.size()));
    System.out.print(jsonString.size() + " randome num: " + randomIndex);
    response.getWriter().println(jsonString.get(randomIndex).toString());


  }

  /**
   * Add normal strings to the list.
   */
  public void addToList(){
      strings.add("cat,orange");
      strings.add("dog,brown");
      strings.add("bird,green");
  }

  /**
   * Convert normal string to JSON type string.
   * @param string -- normal string.
   * @return JSON type string.
   */
  public String convertToJSON(String string){
        Gson gson = new Gson();
        String[] spliString = string.split(",");
        String jsonString = "{\"type\": \"" + spliString[0]  + "\", \"color\": \"" + spliString[1] + "\"}"; 
        Animal animal = gson.fromJson(jsonString, Animal.class);
        String json = gson.toJson(animal);
        return json;
  }
}

class Animal{
    private String type;
    private String color;
   public Animal(){} 

    public Animal fromJson(String jsonString, Class<Animal> class1) {
        return null;
    }
}
