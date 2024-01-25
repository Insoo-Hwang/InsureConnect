package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.ChatRoomDto;
import com.example.InsureConnect.Dto.ConsultRequestDto;
import com.example.InsureConnect.Entity.ConsultRequest;
import com.example.InsureConnect.Repository.ConsultRequestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultRequestService {
    private final ConsultRequestRepository consultRequestRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ConsultRequestDto saveConsultRequest(UUID chatRoom, Long plannerId, String userNickname) {

        ConsultRequest consultRequest = ConsultRequest.builder()
                .chatRoomId(chatRoom)
                .plannerId(plannerId)
                .userNickname(userNickname)
                .status(0)
                .build();
        consultRequest.setRequestTime();
        consultRequestRepository.save(consultRequest);
        return modelMapper.map(consultRequest, ConsultRequestDto.class);
    }

    public List<ConsultRequestDto> findAllRequest(Long plannerId) {
        List<ConsultRequest> consultRequest = consultRequestRepository.findByPlannerIdOrderByRequestTimeDesc(plannerId);
        return consultRequest.stream().map(request -> modelMapper.map(request, ConsultRequestDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public ConsultRequestDto findByPlannerId(Long plannerId, UUID chatRoomId, Long requestId) {
        ConsultRequest consultRequest = findConsultRequest(plannerId, chatRoomId, requestId);
        updateConsultRequestStatus(consultRequest);

        return modelMapper.map(consultRequest, ConsultRequestDto.class);
    }

    private ConsultRequest findConsultRequest(Long plannerId, UUID chatRoomId, Long requestId) {
        return consultRequestRepository.findByPlannerIdAndChatRoomIdAndId(plannerId, chatRoomId, requestId)
                .orElseThrow(IllegalArgumentException::new);
    }

    private void updateConsultRequestStatus(ConsultRequest consultRequest) {
        ConsultRequest updatedRequest = ConsultRequest.builder()
                .id(consultRequest.getId())
                .plannerId(consultRequest.getPlannerId())
                .userNickname(consultRequest.getUserNickname())
                .chatRoomId(consultRequest.getChatRoomId())
                .status(1)
                .requestTime(consultRequest.getRequestTime())
                .build();

        consultRequestRepository.save(updatedRequest);
    }

}

