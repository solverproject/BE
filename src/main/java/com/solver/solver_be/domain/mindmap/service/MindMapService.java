package com.solver.solver_be.domain.mindmap.service;

import com.solver.solver_be.domain.hashtag.entity.HashTag;
import com.solver.solver_be.domain.hashtag.repository.HashTagRepository;
import com.solver.solver_be.domain.mindmap.dto.MindMapRequestDto;
import com.solver.solver_be.domain.mindmap.dto.MindMapResponseDto;
import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.mindmap.repository.MindMapRepository;
import com.solver.solver_be.domain.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import com.solver.solver_be.domain.workspace.repository.WorkSpaceRepository;
import com.solver.solver_be.global.exception.exceptionType.MindMapException;
import com.solver.solver_be.global.exception.exceptionType.QuestionBoardException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MindMapService {

    private final MindMapRepository mindMapRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final QuestionBoardRepository questionBoardRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createMindMap(Long id, MindMapRequestDto mindMapRequestDto, User user){

        WorkSpace workSpace = getWorkSpaceById(id);
        MindMap mindMap = mindMapRepository.save(MindMap.of(mindMapRequestDto, workSpace, user));
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_UPLODAD_SUCCESS, MindMapResponseDto.of(mindMap)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto>  changeMindMap(Long id, User user){

        MindMap mindMap = getMindMapById(id);
        List<QuestionResponseDto> questionResponseDtoList = getQuestionResponseDtoList(mindMap);
        MindMapResponseDto mindMapResponseDto = MindMapResponseDto.of(mindMap, questionResponseDtoList);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_CHANGE_SUCCESS, mindMapResponseDto));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto>  updateMindMap(Long id, MindMapRequestDto mindMapRequestDto, User user){

        MindMap mindMap = getMindMapById(id);

        if (! mindMap.getUser().equals(user)){
            throw new MindMapException(ResponseCode.MINDMAP_UPDATE_FAILED);
        }
        mindMap.updateMindMap(mindMapRequestDto);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_UPDATE_SUCCESS, MindMapResponseDto.of(mindMap)));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteMindMap(Long id, User user){

        MindMap mindMap = getMindMapById(id);

        if (! mindMap.getUser().equals(user)){
            throw new MindMapException(ResponseCode.MINDMAP_UPDATE_FAILED);
        }


        mindMapRepository.deleteById(mindMap.getId());
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_DELETE_SUCCESS));
    }

    // ==================================== METHOD =======================================//

    private WorkSpace getWorkSpaceById(Long id) {
        return workSpaceRepository.findById(id).orElseThrow(
                () -> new QuestionBoardException(ResponseCode.WORKSPACE_NOT_FOUND)
        );
    }

    private MindMap getMindMapById(Long id) {
        return mindMapRepository.findById(id).orElseThrow(
                () -> new QuestionBoardException(ResponseCode.MINDMAP_NOT_FOUND)
        );
    }

    private List<QuestionResponseDto> getQuestionResponseDtoList(MindMap mindMap) {

        List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByMindMapId(mindMap.getId());
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        for (QuestionBoard questionBoard : questionBoardList) {
            List<String> titleList = getTitleList(questionBoard);
            questionResponseDtoList.add(QuestionResponseDto.of(questionBoard, titleList));
        }
        return questionResponseDtoList;
    }


    private List<String> getTitleList(QuestionBoard questionBoard) {
        List<HashTag> hashTagList = hashTagRepository.findByQuestionBoardId(questionBoard.getId());
        List<String> titleList = new ArrayList<>();
        for (HashTag hashTag : hashTagList) {
            titleList.add(hashTag.getTitle());
        }
        return titleList;
    }
}
