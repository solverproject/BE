package com.solver.solver_be.domain.bookmark.controller;

import com.solver.solver_be.domain.bookmark.service.BookMarkService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @PutMapping("/board/bookmarks/{id}")
    public ResponseEntity<GlobalResponseDto> createPostLike(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookMarkService.createFavorite(id, userDetails.getUser());
    }

}
