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
import java.util.Optional;

@Service
@Slf4j
public class PositionService {

    @Autowired
    private PositionEntityRepository positionEntityRepository;

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

    // 입력한 eno로 연차개수 반환
    public PositionDto getYearno ( int eno ){
        List<PositionEntity> positionEntityList = positionEntityRepository.findByYearno( eno );
        if ( positionEntityList.size() > 0 ) {
            return positionEntityList.get(0).toDto();
        }
        return null;
    }

}
