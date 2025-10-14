package com.youtube_clone.watchHistory.mapper;

import com.youtube_clone.watchHistory.dtos.WatchHistoryDTO;
import com.youtube_clone.watchHistory.entity.WatchHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WatchHistoryMapper {
    public static WatchHistoryDTO toDTO(WatchHistory watchHistory){
        if(watchHistory  == null){
            return null;
        }

        return WatchHistoryDTO.builder()
                .id(String.valueOf(watchHistory.getId()))
                .userId(String.valueOf(watchHistory.getUserId()))
                .videoId(String.valueOf(watchHistory.getVideoId()))
                .sessionStartTime(watchHistory.getSessionStartTime())
                .sessionEndTime(watchHistory.getSessionEndTime())
                .duration(watchHistory.getDuration())
                .build();
    }


    public static List<WatchHistoryDTO> toDTOList(List<WatchHistory> watchHistories) {
        if (watchHistories == null) {
            return null;
        }
        return watchHistories.stream()
                .map(WatchHistoryMapper::toDTO)
                .toList();
    }

    public static WatchHistory toEntity(WatchHistoryDTO request) {
        if (request == null) {
            return null;
        }

        return WatchHistory.builder()
                .userId(UUID.fromString(request.getUserId()))
                .videoId(UUID.fromString(request.getVideoId()))
                .sessionStartTime(request.getSessionStartTime())
                .sessionEndTime(request.getSessionEndTime())
                .duration(request.getDuration())
                .counted(request.isCounted())
                .ownerViewCounted(request.isOwnerViewCounted())
                .build();
    }
}
