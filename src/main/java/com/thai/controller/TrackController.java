package com.thai.controller;

import com.thai.model.Track;
import com.thai.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PropertySource("classpath:upload_file.properties")
@RequestMapping(value = {"/tracks", "/", ""})
public class TrackController {
    @Autowired
    ITrackService trackService;
    @Value("${file-upload}")
    private String upload;

    @GetMapping("")
    public String listForm(Model model){
        List<Track> tracks = trackService.findAll();
        model.addAttribute("tracks",tracks);
        return "/list";
    }
}
