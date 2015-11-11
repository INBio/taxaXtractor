package org.inbio.core.gbifapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpeciesGbifAPI {

  public SpeciesGbifAPI() {

  }

  public HashMap<String, Object> Match(String rank, String name, boolean strict, boolean verbose, 
      Map<String, String> taxonomy) {
    String urlString = "http://api.gbif.org/v1/species/match?verbose=true";
    URL url;
    URLConnection conn;
    InputStream is;
    
    if(strict)
      urlString += "&strict=true";
//    urlString += "&verbose=" + (verbose? "true" : "false");
    if(taxonomy.containsKey("kingdom"))
      urlString += "&kingdom=" + taxonomy.get("kingdom");
    try {
      urlString += "&name=" + URLEncoder.encode(name, "UTF-8");
    } catch (UnsupportedEncodingException ex) {
      System.out.println(ex.getMessage());
    }
    
    try {
      url = new URL(urlString);
      conn = url.openConnection();
      is = conn.getInputStream();
      JsonFactory jsonFactory = new JsonFactory();
      ObjectMapper mapper = new ObjectMapper(jsonFactory);
      TypeReference<HashMap<String,Object>> typeRef 
      = new TypeReference<HashMap<String,Object>>() {};
      
      HashMap<String,Object> o = mapper.readValue(is, typeRef);
      
      return o;

    }
    catch (MalformedURLException ex) {
      System.out.println(ex.getMessage());
    }
    catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    
    return null;
    
  }
}
