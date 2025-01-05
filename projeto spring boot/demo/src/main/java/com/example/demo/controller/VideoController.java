package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Video;
import com.example.demo.service.VideoService;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/getByModuloId")
    public ResponseEntity<List<Video>> getVideos(@RequestParam("moduloId") Long moduloId) {
        List<Video> videos = videoService.getVideosByModuloId(moduloId);
        return ResponseEntity.ok(videos);
    }
}
