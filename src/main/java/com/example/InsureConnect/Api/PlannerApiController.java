package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.UserDto;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlannerApiController {

    private final PlannerService plannerService;
    private final UserService userService;

    //설계사 전체 조회
    @GetMapping("/api/planners")
    public ResponseEntity<List<PlannerDto>> allPlanner() {

        List<PlannerDto> permittedPlanners = plannerService.findPermitPlanner();

        if (permittedPlanners.isEmpty()) {
            // 만약 특정 조건을 만족하는 플래너가 없으면 HTTP 상태 코드 204 (NO_CONTENT)를 반환
            return ResponseEntity.noContent().build();
        } else {
            // 특정 조건을 만족하는 플래너가 있으면 HTTP 상태 코드 200 (OK)와 함께 해당 플래너 목록을 반환
            return ResponseEntity.status(HttpStatus.OK).body(permittedPlanners);
        }
    }

    //설계사 가입 여부 확인
    @GetMapping("/api/planner/{userId}")
    public ResponseEntity<PlannerDto> checkPlanner(@PathVariable UUID userId) {
        PlannerDto dto = plannerService.findByUserId(userId);
        if (dto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else if (dto.getStatus().equals("enroll") || dto.getStatus().equals("temp"))
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(dto);
        else return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //설계사 내역 삭제
    @DeleteMapping("/api/planner/{id}")
    public ResponseEntity<PlannerDto> deletePlanner(@PathVariable Long id) {
        PlannerDto deleteDto = plannerService.delete(id);
        if (deleteDto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

    //설계사 관리
    @PatchMapping("/api/planner/{plannerId}/{status}")
    public ResponseEntity<PlannerDto> managePlanner(@PathVariable Long plannerId, @PathVariable String status) {
        boolean permit = status.equals("permit");
        UserDto userDto = userService.findById(plannerService.findUserById(plannerId).getId());
        if (userDto.getKakaoId() == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else {
            PlannerDto plannerDto = plannerService.managePlanner(plannerId, permit);
            return ResponseEntity.status(HttpStatus.OK).body(plannerDto);
        }
    }

    //설계사 삭제
    @PatchMapping("/api/planner/{userId}/del")
    public ResponseEntity<PlannerDto> deletedPlanner(@PathVariable UUID userId){
        PlannerDto plannerDto = plannerService.findByUserId(userId);
        PlannerDto deleted = plannerService.deletePlanner(plannerDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
