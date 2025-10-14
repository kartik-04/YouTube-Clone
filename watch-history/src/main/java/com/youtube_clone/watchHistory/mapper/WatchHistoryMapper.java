package com.youtube_clone.watchHistory.mapper;

import com.youtube_clone.watchHistory.dtos.WatchHistorDTO;
import com.youtube_clone.watchHistory.entity.WatchHistory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WatchHistoryMapper {
    public WatchHistorDTO toDTO(WatchHistory watchHistory){
        if(watchHistory  == null){
            return null;
        }

        return WatchHistorDTO.builder()
                .id(String.valueOf(watchHistory.getId()))
                .userId(String.valueOf(watchHistory.getUserId()))
                .videoId(String.valueOf(watchHistory.getVideoId()))
                .sessionStartTime(watchHistory.getSessionStartTime())
                .sessionEndTime(watchHistory.getSessionEndTime())
                .duration(watchHistory.getDuration())
                .build();
    }


    public WatchHistory toEntity(WatchHistorDTO request){
        if(request  == null){
            return null;
        }

        return WatchHistory.builder()
                .userId(UUID.fromString(request.getUserId()))
                .videoId(UUID.fromString(request.getVideoId()))
                .sessionStartTime(request.getSessionStartTime())
                .sessionEndTime(request.getSessionEndTime())
                .duration(request.getDuration())
                .build();
    }
}
