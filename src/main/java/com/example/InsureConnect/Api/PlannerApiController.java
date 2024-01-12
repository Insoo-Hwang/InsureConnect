package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Service.PlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlannerApiController {

    private final PlannerService plannerService;

    //설계사 가입 여부 확인
    @GetMapping("/api/planner/{userId}")
    public ResponseEntity<PlannerDto> checkPlanner(@PathVariable UUID userId){
        PlannerDto dto = plannerService.findByUserId(userId);
        if(dto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else if(dto.getStatus().equals("enroll") || dto.getStatus().equals("temp")) return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(dto);
        else return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //설계사 내역 삭제
    @DeleteMapping("/api/planner/{userId}")
    public ResponseEntity<PlannerDto> deletePlanner(@PathVariable UUID userId){
        PlannerDto plannerDto = plannerService.findByUserId(userId);
        PlannerDto deleteDto = plannerService.delete(plannerDto.getId());
        if(deleteDto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}
