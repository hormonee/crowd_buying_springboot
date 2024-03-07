package com.hormonic.crowd_buying.util.specification;

import com.hormonic.crowd_buying.domain.entity.Bookmark;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class JpaSpecification {
    public static Specification<Recruit> recruitTitleLike(String recruitTitle) {
        return (root, query, cb) -> cb.like(root.get("recruitTitle"), "%" + recruitTitle + "%");
    }

    public static Specification<Recruit> recruitCategoryLike(int categoryId) {
        return (root, query, cb) -> cb.equal(root.get("categoryId"), categoryId);
    }

    public static Specification<Recruit> notEndedRecruit() {
        return (root, query, cb) -> cb.isNull(root.get("recruitEndDate"));
    }

    public static Specification<Bookmark> bookmarkUserIdLike(String userId) {
        return (root, query, cb) -> cb.equal(root.get("userId"), userId);
    }

    public static Specification<Bookmark> notEndedBookmark() {
        return (root, query, cb) -> cb.lessThan(root.get("recruitMemberParticipated"),
                                                                          root.get("recruitMemberTotal"));
    }
}
