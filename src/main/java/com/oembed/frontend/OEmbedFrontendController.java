package com.oembed.frontend;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class OEmbedFrontendController {
    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/oembed")
    public ModelAndView result(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("oembed");

        String api = "http://localhost:8080/oembed/";
        String url = request.getParameter("url");
        String request_url = api + url;
        //String request_url =  url;
        System.out.println(request_url);

        HttpEntity entity = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet httpget = new HttpGet(request_url);
			HttpResponse response = client.execute(httpget);
            entity = response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> data = null;
        JsonNode json = null;
        try {
            data = mapper.readValue(EntityUtils.toString(entity), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            mv.addObject("status", "NOT_FOUND");
            mv.addObject("data", null);
        }
        
        mv.addObject("status", data.get("status"));
        mv.addObject("data", data.get("data"));
        return mv;
    }
}
