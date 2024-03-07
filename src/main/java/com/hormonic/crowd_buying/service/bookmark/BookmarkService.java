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

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final RecruitRepository recruitRepository;

    public List<Bookmark> getBookmarkListByUserId(String userId) {
        Specification<Bookmark> spec = JpaSpecification.bookmarkUserIdLike(userId)
                .and( JpaSpecification.notEndedBookmark() );
        Sort sort = Sort.by( Sort.Order.desc("bookmarkNo") );

        return bookmarkRepository.findAll(spec, sort);
    }

    public Boolean isBookmarked(String userId, UUID recruitUuid) {
        return bookmarkRepository.existsByUserIdAndRecruitUuid(userId, recruitUuid);
    }

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

    @Transactional
    public void deleteBookmark(DeleteBookmarkRequest deleteBookmarkRequest) {
        bookmarkRepository.deleteByUserIdAndRecruitUuid(deleteBookmarkRequest.getUserId(),
                                                        deleteBookmarkRequest.getRecruitUuid());
        recruitRepository.minusRecruitBookmarked(deleteBookmarkRequest.getRecruitUuid());
    }

}
