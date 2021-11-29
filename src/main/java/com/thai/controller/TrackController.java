package com.thai.controller;

import com.thai.model.Track;
import com.thai.model.TrackSave;
import com.thai.service.ITrackService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource("classpath:upload_file.properties")
@RequestMapping(value = {"/tracks", "/", ""})
public class TrackController {

    @Autowired
    ITrackService trackService;
    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("")
    public String listForm(Model model) {
        List<Track> tracks = trackService.findAll();
        model.addAttribute("tracks", tracks);
        return "/list";
    }

    @GetMapping("/create-track")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("track", new Track());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveTrack(@ModelAttribute TrackSave trackSave) {
        MultipartFile multipartFile = trackSave.getLink();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(trackSave.getLink().getBytes(), new File(fileUpload + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Track track = new Track(trackSave.getName(), fileName);
        trackService.save(track);
        ModelAndView modelAndView = new ModelAndView("/list");
        List<Track> tracks = trackService.findAll();
        modelAndView.addObject("tracks", tracks);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView EditForm(@PathVariable Long id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("/edit");
        Track track = trackService.findById(id);
        TrackSave trackSave = new TrackSave();
        trackSave.setId(trackSave.getId());
        trackSave.setName(trackSave.getName());
        File file = new File(fileUpload + track.getLink());
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "audio", IOUtils.toByteArray(fileInputStream));
        modelAndView.addObject("trackSave", trackSave);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute TrackSave trackSave) throws IOException {
        MultipartFile multipartFile = trackSave.getLink();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(trackSave.getLink().getBytes(),new File(fileUpload+fileName));
        Track track = new Track(trackSave.getId(),trackSave.getName(),fileName);
        trackService.update(track);
        ModelAndView modelAndView = new ModelAndView("/list");
        List<Track>tracks = trackService.findAll();
        modelAndView.addObject("tracks",tracks);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleleTrack(@PathVariable Long id){
        trackService.remove(id);
        ModelAndView modelAndView = new ModelAndView("/list");
        List<Track>tracks= trackService.findAll();
        modelAndView.addObject("tracks",tracks);
        return modelAndView;
    }
}
