package com.solver.solver_be.domain.mindmap.service;

import com.solver.solver_be.domain.answerBoard.repository.AnswerBoardRepository;
import com.solver.solver_be.domain.hashtag.entity.HashTag;
import com.solver.solver_be.domain.hashtag.repository.HashTagRepository;
import com.solver.solver_be.domain.image.entity.Image;
import com.solver.solver_be.domain.image.repository.ImageRepository;
import com.solver.solver_be.domain.mindmap.dto.MindMapRequestDto;
import com.solver.solver_be.domain.mindmap.dto.MindMapResponseDto;
import com.solver.solver_be.domain.mindmap.entity.MindMap;
import com.solver.solver_be.domain.mindmap.repository.MindMapRepository;
import com.solver.solver_be.domain.questionBoard.dto.QuestionBoardResponseDto;
import com.solver.solver_be.domain.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.domain.workspace.entity.WorkSpace;
import com.solver.solver_be.domain.workspace.repository.WorkSpaceRepository;
import com.solver.solver_be.global.exception.exceptionType.MindMapException;
import com.solver.solver_be.global.exception.exceptionType.QuestionBoardException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import com.solver.solver_be.global.util.s3.S3Service;
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
    private final AnswerBoardRepository answerBoardRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public ResponseEntity<GlobalResponseDto> createMindMap(MindMapRequestDto mindMapRequestDto, User user) {

        MindMap mindMap = mindMapRepository.save(MindMap.of(mindMapRequestDto, user));
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_UPLODAD_SUCCESS, MindMapResponseDto.of(mindMap)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> changeMindMap(Long id, User user) {

        MindMap mindMap = getMindMapById(id);
        List<QuestionBoardResponseDto> questionBoardResponseDtoList = getQuestionResponseDtoList(mindMap);
        MindMapResponseDto mindMapResponseDto = MindMapResponseDto.of(mindMap, questionBoardResponseDtoList);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_CHANGE_SUCCESS, mindMapResponseDto));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> updateMindMap(Long id, MindMapRequestDto mindMapRequestDto, User user) {

        MindMap mindMap = getMindMapById(id);

        if (!mindMap.getUser().equals(user)) {
            throw new MindMapException(ResponseCode.MINDMAP_UPDATE_FAILED);
        }
        mindMap.updateMindMap(mindMapRequestDto);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.MINDMAP_UPDATE_SUCCESS, MindMapResponseDto.of(mindMap)));
    }

    @Transactional
    public ResponseEntity<GlobalResponseDto> deleteMindMap(Long id, User user) {

        MindMap mindMap = getMindMapById(id);

        if (!mindMap.getUser().equals(user)) {
            throw new MindMapException(ResponseCode.MINDMAP_UPDATE_FAILED);
        }

        List<QuestionBoard> questionBoardList = getQuestionBoardByMindMapId(id);
        for (QuestionBoard questionBoard : questionBoardList) {
            List<Image> imagePathList = imageRepository.findAllByQuestionBoardId(questionBoard.getId());
            for (Image image : imagePathList) {
                String uploadPath = image.getUploadPath();
                String filename = uploadPath.substring(61);
                s3Service.deleteFile(filename);
            }
            answerBoardRepository.deleteAllByQuestionBoardId(questionBoard.getId());
            imageRepository.deleteAllByQuestionBoardId(questionBoard.getId());
            hashTagRepository.deleteAllByQuestionBoardId(questionBoard.getId());
            questionBoardRepository.deleteById(questionBoard.getId());
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

    private List<QuestionBoardResponseDto> getQuestionResponseDtoList(MindMap mindMap) {

        List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByMindMapId(mindMap.getId());
        List<QuestionBoardResponseDto> questionBoardResponseDtoList = new ArrayList<>();
        for (QuestionBoard questionBoard : questionBoardList) {
            List<String> titleList = getTitleList(questionBoard);
            questionBoardResponseDtoList.add(QuestionBoardResponseDto.of(questionBoard, titleList));
        }
        return questionBoardResponseDtoList;
    }

    private List<String> getTitleList(QuestionBoard questionBoard) {
        List<HashTag> hashTagList = hashTagRepository.findByQuestionBoardId(questionBoard.getId());
        List<String> titleList = new ArrayList<>();
        for (HashTag hashTag : hashTagList) {
            titleList.add(hashTag.getTitle());
        }
        return titleList;
    }

    private List<QuestionBoard> getQuestionBoardByMindMapId(Long id) {
        return questionBoardRepository.findAllByMindMapId(id);
    }
}
