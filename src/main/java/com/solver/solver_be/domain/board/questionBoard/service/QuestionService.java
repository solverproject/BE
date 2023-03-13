package com.solver.solver_be.domain.board.questionBoard.service;

import com.solver.solver_be.domain.board.questionBoard.dto.QuestionRequestDto;
import com.solver.solver_be.domain.board.questionBoard.dto.QuestionResponseDto;
import com.solver.solver_be.domain.board.questionBoard.entity.QuestionBoard;
import com.solver.solver_be.domain.board.questionBoard.repository.QuestionBoardRepository;
import com.solver.solver_be.domain.hashtag.entity.HashTag;
import com.solver.solver_be.domain.hashtag.repository.HashTagRepository;
import com.solver.solver_be.domain.image.entity.Image;
import com.solver.solver_be.domain.image.repository.ImageRepository;
import com.solver.solver_be.domain.user.entity.User;
import com.solver.solver_be.global.exception.exceptionType.QuestionBoardException;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.response.ResponseCode;
import com.solver.solver_be.global.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionBoardRepository questionBoardRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final S3Service s3Service;

    // 질문게시물 등록
    public ResponseEntity<GlobalResponseDto> createBoard(QuestionRequestDto questionRequestDto, List<MultipartFile> multipartFilelist, User user) throws IOException {

        QuestionBoard questionBoard = questionBoardRepository.saveAndFlush(QuestionBoard.of(questionRequestDto.getTitle(),questionRequestDto.getContents(), user));
        List <String> hashTagList = questionRequestDto.getHashTagList();
        for(String hashTag : hashTagList)
        {
            hashTagRepository.saveAndFlush(HashTag.of(questionBoard,hashTag));
        }
        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", questionBoard, user);
        }
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_UPLOAD_SUCCESS, QuestionResponseDto.of(questionBoard,hashTagList)));
    }

    // 질문게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getBoards(User user) {

        List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByOrderByCreatedAtDesc();
        List<QuestionResponseDto> responseDtoList = new ArrayList<>();
        for (QuestionBoard questionBoard : questionBoardList) {
            List<String> imagePathList = ImagePathList(questionBoard);
            responseDtoList.add(QuestionResponseDto
                    .of(questionBoard, imagePathList));
        }
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, responseDtoList));

    }

    // 질문게시글 단일 조회
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getBoard(Long id, User user) {

        QuestionBoard questionBoard = getQuestionBoardById(id);
        List<String> imagePathList = ImagePathList(questionBoard);
        QuestionResponseDto questionResponseDto = QuestionResponseDto
                .of(questionBoard, imagePathList);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, questionResponseDto));

    }

    // 질문게시글 수정
    public ResponseEntity<GlobalResponseDto> updateBoard(Long id, QuestionRequestDto questionRequestDto, User user) {

        QuestionBoard questionBoard = getQuestionBoardById(id);

        if (!questionBoard.getUser().equals(user)) {
            throw new QuestionBoardException(ResponseCode.BOARD_UPDATE_FAILED);
        }

        questionBoard.update(questionRequestDto);
        List<String> imagePathList = ImagePathList(questionBoard);
        QuestionResponseDto questionResponseDto = QuestionResponseDto
                .of(questionBoard, imagePathList);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_UPDATE_SUCCESS,questionResponseDto));
    }

    // 질문게시글 삭제
    public ResponseEntity<GlobalResponseDto> deleteBoard(Long id, User user) {
        QuestionBoard questionBoard = getQuestionBoardById(id);

        if (!questionBoard.getUser().equals(user)) {
            throw new QuestionBoardException(ResponseCode.BOARD_UPDATE_FAILED);
        }

        List<Image> imagePathList = imageRepository.findAllByQuestionBoardId(questionBoard.getId());
        for (Image image : imagePathList) {
            String uploadPath = image.getUploadPath();
            String filename = uploadPath.substring(61);
            s3Service.deleteFile(filename);
        }

        imageRepository.deleteAllByQuestionBoardId(questionBoard.getId());
        questionBoardRepository.deleteById(questionBoard.getId());
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_DELETE_SUCCESS));
    }

    private List<String> ImagePathList(QuestionBoard questionBoard) {

        List<Image> imageList = imageRepository.findAllByQuestionBoardId(questionBoard.getId());
        List<String> imagePathList = new ArrayList<>();

        for (Image image : imageList) {
            imagePathList.add(image.getUploadPath());
        }
        return imagePathList;
    }

    private QuestionBoard getQuestionBoardById(Long id) {

        return questionBoardRepository.findById(id).orElseThrow(
                () -> new QuestionBoardException(ResponseCode.BOARD_NOT_FOUND)
        );

    }
}
