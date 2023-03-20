package com.solver.solver_be.domain.favorite.controller;

import com.solver.solver_be.domain.favorite.service.FavoriteService;
import com.solver.solver_be.global.response.GlobalResponseDto;
import com.solver.solver_be.global.security.webSecurity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PutMapping("/board/favorites/{id}")
    public ResponseEntity<GlobalResponseDto> createPostLike(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return favoriteService.createFavorite(id, userDetails.getUser());
    }

}
