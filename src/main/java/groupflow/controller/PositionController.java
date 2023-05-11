package groupflow.controller;

import groupflow.domain.department.DepartmentDto;
import groupflow.domain.position.PositionDto;
import groupflow.service.DepartmentService;
import groupflow.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/all")
    public List<PositionDto> allPositions() {
        return positionService.allPositions();
    }
}
