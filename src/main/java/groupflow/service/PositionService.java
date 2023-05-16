package groupflow.service;

import groupflow.domain.department.DepartmentDto;
import groupflow.domain.department.DepartmentEntity;
import groupflow.domain.department.DepartmentRepository;
import groupflow.domain.position.PositionDto;
import groupflow.domain.position.PositionEntity;
import groupflow.domain.position.PositionEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PositionService {

    @Autowired
    private PositionEntityRepository positionEntityRepository;
    @GetMapping("/all")
    public List<PositionDto> allPositions() {
        List<PositionDto> positionDtoList = new ArrayList<>();

        List<PositionEntity> positionEntityList = positionEntityRepository.findAll();
        if ( positionEntityList.size() > 0 ) {
            positionEntityList.forEach( positionEntity->{
                positionDtoList.add( positionEntity.toDto() );//positionEntityList에 positionEntity를하나씩 꺼내가지고 toDto변환해서 넣어준다.
            });
        }
        return positionDtoList;
    }

}
