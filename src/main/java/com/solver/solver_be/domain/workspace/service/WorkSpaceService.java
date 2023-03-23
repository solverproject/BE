package com.solver.solver_be.domain.workspace.service;

import com.solver.solver_be.domain.hashtag.entity.HashTag;
import com.solver.solver_be.domain.hashtag.repository.HashTagRepository;
import com.solver.solver_be.domain.mindmap.dto.MindMapResponseDto;
import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.mindmap.repository.MindMapRepository;
import com.solver.solver_be.domain.questionBoard.dto.QuestionBoardResponseDto;
import com.solver.solver_be.domain.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.workspace.dto.WorkSpaceRequestDto;
import com.solver.solver_be.domain.workspace.dto.WorkSpaceResponseDto;
import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import com.solver.solver_be.domain.workspace.repository.WorkSpaceRepository;
import com.solver.solver_be.global.exception.exceptionType.QuestionBoardException;
import com.solver.solver_be.global.exception.exceptionType.WorkSpaceException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkSpaceService {

    private final WorkSpaceRepository workSpaceRepository;
    private final MindMapRepository mindMapRepository;
    private final QuestionBoardRepository questionBoardRepository;

    private final HashTagRepository hashTagRepository;

    // 1. 새로운 워크 스페이스를 생성할때.
    @Transactional
    public ResponseEntity<GlobalResponseDto> createWorkSpace(WorkSpaceRequestDto workSpaceRequestDto, User user) {

        WorkSpace workSpace = workSpaceRepository.saveAndFlush(WorkSpace.of(workSpaceRequestDto, user));

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.WORKSPACE_UPLOAD_SUCCESS, WorkSpaceResponseDto.of(workSpace)));
    }

    // 2. 내가 가지고 있는 다른 워크 스페이스를 들어갈때.
    @Transactional
    public ResponseEntity<GlobalResponseDto> getWorkSpace(Long id, User user) {

        WorkSpace workSpace = workSpaceRepository.findById(id).orElseThrow(
                () -> new WorkSpaceException(ResponseCode.BOARD_NOT_FOUND)
        );

        List<MindMap> mindMapList = mindMapRepository.findAllByWorkSpaceId(id);
        List<MindMapResponseDto> mindMapResponseDtoList = new ArrayList<>();

        for (MindMap mindMap : mindMapList) {
            List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByMindMapId(mindMap.getId());
            List<QuestionBoardResponseDto> questionBoardResponseDtoList = new ArrayList<>();
            for (QuestionBoard questionBoard : questionBoardList) {
                List<String> titleList = getTitleList(questionBoard);
                questionBoardResponseDtoList.add(QuestionBoardResponseDto.of(questionBoard,titleList));
            }
            mindMapResponseDtoList.add(MindMapResponseDto.of(mindMap, questionBoardResponseDtoList));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, WorkSpaceResponseDto.of(workSpace, mindMapResponseDtoList)));
    }


    // 3. 다른 사람들이 가지고 있는 워크 스페이스를 들어가고 싶을때.
    // 그 사람이 가지고 있는 워크 스페이스에 들어가고 싶다면 그 사람이 가지고 있는 워크스페이스의 코드를 입력하면 될 것 같다.
    // 하지만 이 워크스페이스 암호가 공유가 된다면 안정성이 보장 될 수 없다고 생각해서 노션에서 진행되고 있는 알고리즘을 참고하면 다음과 같다.
    // 1) 먼저 워크스페이스 오너가 이 사람의 이메일을 추가한다. (이메일로 추천 코드 혹은 워크 스페이스에 참가하라고 메일을 보낸다.)
    // 2) 그렇게 된다면 워크스페이스 참가자가 이 메일을 클릭하면 자연스럽게 참가가 가능할 것이라고 생각이 든다.
    @Transactional
    public ResponseEntity<GlobalResponseDto> participateWorkSpace(Long id, User user) {
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS));
    }


    // ================================ METHOD =================================== //
    private List<QuestionResponseDto> getQuestionResponseDtoList(MindMap mindMap) {

        List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByMindMapId(mindMap.getId());
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        for (QuestionBoard questionBoard : questionBoardList) {
            questionResponseDtoList.add(QuestionResponseDto.of(questionBoard));
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
