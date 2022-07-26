package com.oembed.frontend;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/oembed")
    public ModelAndView result(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("result");
        
        String url = request.getParameter("url");
        JsonNode json = null;
        try {
            json = mapper.readTree(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            mv.addObject("status", "NOT_FOUND");
            mv.addObject("data", null);
        }

        System.out.println(json);
        //mv.addObject("status", json.get("status"));
        //mv.addObject("data", json.get("data"));
        return mv;
    }
}
