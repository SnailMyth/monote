package com.myth.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Json 工具类
 * 
 * @author dib
 *
 */
public enum Jsons {
  i;
  final Gson gson = new GsonBuilder()
      //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
//       .registerTypeAdapter(Date.class, DateTypeAdapter.DATE)
//       .registerTypeAdapter(Timestamp.class, DateTypeAdapter.DATE)
//       .registerTypeAdapter(java.sql.Date.class, DateTypeAdapter.DATE)
//      .setExclusionStrategies(new ThrowableExclusionStrategy())
      .setPrettyPrinting().create();

  public Gson getGson(){
    return gson;
  }
  
  public String toJson(Object src) {
    return gson.toJson(src);
  }

  public void toJson(Object src, Appendable writer) {
    gson.toJson(src, writer);
  }

  public void toJson(Object src, Type typeOfSrc, Appendable writer) {
    gson.toJson(src, typeOfSrc, writer);
  }

  public <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }

  public <T> T fromJson(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }

  public <T> T fromJson(Reader reader, Type typeOfT) {
    return gson.fromJson(reader, typeOfT);
  }

  public <T> T fromJson(Reader reader, Class<T> classOfT) {
    return gson.fromJson(reader, classOfT);
  }
  
  public <T> T fromJson(JsonElement reader, Type typeOfT) {
    return gson.fromJson(reader, typeOfT);
  }

  public <T> T fromJson(JsonElement reader, Class<T> classOfT) {
    return gson.fromJson(reader, classOfT);
  }
  
  public static void main(String[] args) {
    System.out.println(Jsons.i.toJson(new Exception(new Exception("pp"))));
  }

}
