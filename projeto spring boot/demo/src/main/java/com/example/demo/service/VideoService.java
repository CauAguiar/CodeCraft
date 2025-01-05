package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Video;
import com.example.demo.repository.VideoRepository;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getVideosByModuloId(Long moduloId) {
        return videoRepository.findByModuloId(moduloId);
    }
}
