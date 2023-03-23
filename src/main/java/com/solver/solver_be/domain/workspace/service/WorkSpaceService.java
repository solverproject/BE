package com.solver.solver_be.domain.workspace.service;

import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.mindmap.repository.MindMapRepository;
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

    // 1. 새로운 워크 스페이스를 생성할때.
    // 워크 스페이스를 하나 생성하고, 이때 돌려줄때? 아마 MindMap 중에서 그냥 빈 노드 하나를 반환하게 끔 만들어 주는게 좋을 것 같다.
    // ## 해결 ## 그리고 이 워크스페이스를 처음 만드는 사람 이므로 ADMIN 권한(혹은 OWNER) 권한을 줘야 한다고 생각이 든다.
    @Transactional
    public ResponseEntity<GlobalResponseDto> createWorkSpace(WorkSpaceRequestDto workSpaceRequestDto, User user) {

        WorkSpace workSpace = workSpaceRepository.saveAndFlush(WorkSpace.of(workSpaceRequestDto, user));

        MindMap mindMap = MindMap.of(workSpace, user);

        QuestionBoard questionBoard = QuestionBoard.of(mindMap, user);

        WorkSpaceResponseDto workSpaceResponseDto = WorkSpaceResponseDto.of(workSpace, mindMap, questionBoard);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.ANSWER_UPLOAD_SUCCESS, workSpaceResponseDto));
    }

    // 2. 내가 가지고 있는 다른 워크 스페이스를 들어갈때.
    // 이때는 다른 워크스페이스에서 가지고 있는 마인드맵들을 전달해주면 될 것 같다
    // 일단 요청 자체를 전부를 주는 것이 아니라, 마인드맵을 준다면 나중에 새로 들어갈때, 그때마다 요청을 하면 되지 않을까? 프론트에서 할 양이 많아지려나?
    // 일단 워크스페이스에서 가지고 있는 마인드맵과 미리보기 (호버를 올릴때) 그때 올라가는 내용들을 (3가지 정도) 를 DTO 로 전달을 해주면 될 것 같다.
    @Transactional
    public ResponseEntity<GlobalResponseDto> getWorkSpace(Long id, User user) {
        WorkSpace workSpace = workSpaceRepository.findById(id).orElseThrow(
                () -> new WorkSpaceException(ResponseCode.BOARD_NOT_FOUND)
        );
        List<MindMap> mindMapList = mindMapRepository.findAllByOrderByCreatedAtDesc();

        List<WorkSpaceResponseDto> workSpaceResponseDtoList = new ArrayList<>();
        for (MindMap mindMap : mindMapList) {
            List<QuestionResponseDto> questionResponseDtoList = getQuestionResponseDtoList(mindMap,workSpace);
            workSpaceResponseDtoList.add(WorkSpaceResponseDto.of(questionResponseDtoList));
        }

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, workSpaceResponseDtoList));
    }


    // 3. 다른 사람들이 가지고 있는 워크 스페이스를 들어가고 싶을때.
    // 그 사람이 가지고 있는 워크 스페이스에 들어가고 싶다면 그 사람이 가지고 있는 워크스페이스의 코드를 입력하면 될 것 같다.
    // 하지만 이 워크스페이스 암호가 공유가 된다면 안정성이 보장 될 수 없다고 생각해서 노션에서 진행되고 있는 알고리즘을 참고하면 다음과 같다.
    // 1) 먼저 워크스페이스 오너가 이 사람의 이메일을 추가한다. (이메일로 추천 코드 혹은 워크 스페이스에 참가하라고 메일을 보낸다.)
    // 2) 그렇게 된다면 워크스페이스 참가자가 이 메일을 클릭하면 자연스럽게 참가가 가능할 것이라고 생각이 든다.
    @Transactional
    public ResponseEntity<GlobalResponseDto> participateWorkSpace(Long id, User user) {

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


}
