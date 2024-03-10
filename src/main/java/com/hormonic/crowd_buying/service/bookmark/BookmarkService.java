package com.hormonic.crowd_buying.service.bookmark;

import com.hormonic.crowd_buying.domain.dto.request.bookmark.CreateBookmarkRequest;
import com.hormonic.crowd_buying.domain.dto.request.bookmark.DeleteBookmarkRequest;
import com.hormonic.crowd_buying.domain.entity.Bookmark;
import com.hormonic.crowd_buying.repository.BookmarkRepository;
import com.hormonic.crowd_buying.repository.RecruitRepository;
import com.hormonic.crowd_buying.util.specification.JpaSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final RecruitRepository recruitRepository;

    // 사용자가 북마크한 리크루트 목록 조회
    public List<Bookmark> getBookmarkListByUserId(String userId) {
        Specification<Bookmark> spec = JpaSpecification.bookmarkUserIdLike(userId)
                .and( JpaSpecification.notEndedBookmark() );
        Sort sort = Sort.by( Sort.Order.desc("bookmarkNo") );

        return bookmarkRepository.findAll(spec, sort);
    }

    // 특정 리크루트를 북마크한 사용자들 아이디만 리스트로 출력
    public List<String> getBookmarkUserOfRecruit(UUID recruitUuid) {
        return bookmarkRepository.findAllByRecruitUuid(recruitUuid).stream()
                .map(i -> i.getUserId())
                .collect(Collectors.toList());
    }

    // 한 사용자가 특정 리크루트를 북마크했는지 여부 반환
    public Boolean isBookmarked(String userId, UUID recruitUuid) {
        return bookmarkRepository.existsByUserIdAndRecruitUuid(userId, recruitUuid);
    }

    // 북마크 생성
    @Transactional
    public void createBookmark(CreateBookmarkRequest createBookmarkRequest) {
        Bookmark newBookmark = new Bookmark(
                createBookmarkRequest.getUserId(),
                createBookmarkRequest.getRecruitUuid(),
                createBookmarkRequest.getRecruitTitle(),
                createBookmarkRequest.getRecruitImagePath(),
                createBookmarkRequest.getRecruitMemberParticipated(),
                createBookmarkRequest.getRecruitMemberTotal());

        bookmarkRepository.save(newBookmark);
        recruitRepository.plusRecruitBookmarked(newBookmark.getRecruitUuid());
    }

    // 북마크 삭제
    @Transactional
    public void deleteBookmark(DeleteBookmarkRequest deleteBookmarkRequest) {
        bookmarkRepository.deleteByUserIdAndRecruitUuid(deleteBookmarkRequest.getUserId(),
                                                        deleteBookmarkRequest.getRecruitUuid());
        recruitRepository.minusRecruitBookmarked(deleteBookmarkRequest.getRecruitUuid());
    }

}
