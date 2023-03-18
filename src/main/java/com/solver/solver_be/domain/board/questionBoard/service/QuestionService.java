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

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionBoardRepository questionBoardRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final S3Service s3Service;

    public ResponseEntity<GlobalResponseDto> createBoard(QuestionRequestDto questionRequestDto, List<MultipartFile> multipartFilelist, User user) throws IOException {
        QuestionBoard questionBoard = questionBoardRepository.saveAndFlush(QuestionBoard.of(questionRequestDto,user));

        // 2. Dto에서 hashTagList는 HashTagList DB 에 저장.
        List<String> hashTagList = questionRequestDto.getHashTagList();
        for (String hashTag : hashTagList) {
            hashTagRepository.saveAndFlush(HashTag.of(questionBoard, hashTag));
        }

        // 3. 이미지 저장.
        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", questionBoard, user);
        }

        // 4. Image 와 title(hashTagList 의 TitleList 값) 반환.
        List<String> imagePathList = ImagePathList(questionBoard);
        List<String> titleList = getTitleList(questionBoard);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_UPLOAD_SUCCESS, QuestionResponseDto.of(questionBoard, titleList, imagePathList)));
    }

    // GET 요청이 들어왔을떄, 이는 user 가 사용되지 않으므로, 이 부분도 제외를 해도 되는지에 대해서 이야기 해보면 좋을 것 같습니다.
    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getBoards(User user) {

        List<QuestionBoard> questionBoardList = questionBoardRepository.findAllByOrderByCreatedAtDesc();
        List<QuestionResponseDto> responseDtoList = new ArrayList<>();
        for (QuestionBoard questionBoard : questionBoardList) {
            List<String> imagePathList = ImagePathList(questionBoard);
            List<String> titleList = getTitleList(questionBoard);
            responseDtoList.add(QuestionResponseDto.of(questionBoard, titleList, imagePathList));
        }
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, responseDtoList));

    }

    @Transactional(readOnly = true)
    public ResponseEntity<GlobalResponseDto> getBoard(Long id, User user) {

        QuestionBoard questionBoard = getQuestionBoardById(id);
        List<String> imagePathList = ImagePathList(questionBoard);
        List<String> titleList = getTitleList(questionBoard);
        QuestionResponseDto questionResponseDto = QuestionResponseDto.of(questionBoard, titleList, imagePathList);
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_LIST_GET_SUCCESS, questionResponseDto));

    }

    public ResponseEntity<GlobalResponseDto> updateBoard(Long id, User user, QuestionRequestDto questionRequestDto) {

        QuestionBoard questionBoard = getQuestionBoardById(id);

        if (!questionBoard.getUser().equals(user)) {
            throw new QuestionBoardException(ResponseCode.BOARD_UPDATE_FAILED);
        }

        questionBoard.update(questionRequestDto);

        List<String> imagePathList = ImagePathList(questionBoard);
        List<String> titleList = getTitleList(questionBoard);
        QuestionResponseDto questionResponseDto = QuestionResponseDto.of(questionBoard, titleList, imagePathList);

        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_UPDATE_SUCCESS, questionResponseDto));
    }

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
        hashTagRepository.deleteAllByQuestionBoardId(questionBoard.getId());
        questionBoardRepository.deleteById(questionBoard.getId());
        return ResponseEntity.ok(GlobalResponseDto.of(ResponseCode.BOARD_DELETE_SUCCESS));
    }

    // 메서드명을 한번 다시 고민하는 것이 좋을 것 같습니다.
    private List<String> ImagePathList(QuestionBoard questionBoard) {

        List<Image> imageList = imageRepository.findAllByQuestionBoardId(questionBoard.getId());
        List<String> imagePathList = new ArrayList<>();

        for (Image image : imageList) {
            imagePathList.add(image.getUploadPath());
        }
        return imagePathList;
    }

    private List<String> getTitleList(QuestionBoard questionBoard) {
        List<HashTag> hashTagList = hashTagRepository.findByQuestionBoardId(questionBoard.getId());
        List<String> titleList = new ArrayList<>();
        for (HashTag hashTag : hashTagList) {
            titleList.add(hashTag.getTitle());
        }
        return titleList;
    }

    private QuestionBoard getQuestionBoardById(Long id) {
        return questionBoardRepository.findById(id).orElseThrow(() -> new QuestionBoardException(ResponseCode.BOARD_NOT_FOUND));
    }
}
