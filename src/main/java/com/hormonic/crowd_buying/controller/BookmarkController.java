package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.request.bookmark.CreateBookmarkRequest;
import com.hormonic.crowd_buying.domain.dto.request.bookmark.DeleteBookmarkRequest;
import com.hormonic.crowd_buying.domain.entity.Bookmark;
import com.hormonic.crowd_buying.service.bookmark.BookmarkService;
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
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Bookmark>> getBookmarkListOfUser(@PathVariable("id") String userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarkListByUserId(userId));
    }

    @GetMapping("/{id}/{uuid}")
    public ResponseEntity<Boolean> isBookmarked(@PathVariable("id") String userId,
                                                @PathVariable("uuid") UUID recruitUuid) {
        return ResponseEntity.ok(bookmarkService.isBookmarked(userId, recruitUuid));
    }

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(@RequestBody @Valid CreateBookmarkRequest createBookmarkRequest) {
        bookmarkService.createBookmark(createBookmarkRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Bookmark> deleteBookmark(@RequestBody @Valid DeleteBookmarkRequest deleteBookmarkRequest) {
        bookmarkService.deleteBookmark(deleteBookmarkRequest);
        return ResponseEntity.ok().build();
    }

}
