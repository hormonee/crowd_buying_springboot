package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.bookmark.CreateBookmarkRequest;
import com.hormonic.crowd_buying.domain.dto.request.bookmark.DeleteBookmarkRequest;
import com.hormonic.crowd_buying.domain.entity.Bookmark;
import com.hormonic.crowd_buying.service.bookmark.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Tag(name = "Bookmark", description = "Bookmark API")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/{id}")
    @Operation(summary = "사용자 북마크 목록 조회", description = "사용자 ID를 통해 해당 사용자 북마크 목록 조회")
    public ResponseEntity<List<Bookmark>> getBookmarkListOfUser(@PathVariable("id") String userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarkListByUserId(userId));
    }

    @GetMapping("/{id}/{uuid}")
    @Operation(summary = "북마크 여부 확인", description = "사용자 ID와 리크루트 UUID를 통해 해당 사용자가 리크루트를 북마크 했는지 확인")
    public ResponseEntity<Boolean> isBookmarked(@PathVariable("id") String userId,
                                                @PathVariable("uuid") UUID recruitUuid) {
        return ResponseEntity.ok(bookmarkService.isBookmarked(userId, recruitUuid));
    }

    @PostMapping
    @Operation(summary = "북마크 생성", description = "사용자 ID와 리크루트 정보를 받아 북마크 생성")
    public ResponseEntity<Bookmark> createBookmark(@RequestBody @Valid CreateBookmarkRequest createBookmarkRequest) {
        bookmarkService.createBookmark(createBookmarkRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    @Operation(summary = "북마크 삭제", description = "사용자 ID와 리크루트 UUID를 받아 북마크 삭제")
    public ResponseEntity<Bookmark> deleteBookmark(@RequestBody @Valid DeleteBookmarkRequest deleteBookmarkRequest) {
        bookmarkService.deleteBookmark(deleteBookmarkRequest);
        return ResponseEntity.ok().build();
    }

}
