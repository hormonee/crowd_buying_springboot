package com.hormonic.crowd_buying.service.recruit;

import com.hormonic.crowd_buying.domain.dto.request.recruit.GetRecruitRequest;
import com.hormonic.crowd_buying.domain.entity.recruit.Recruit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface RecruitMapper {
    public ArrayList<Recruit> getRecruitListByCriterion(String recruitTitle, String orderBy);

    public ArrayList<Recruit> getRecruitList(GetRecruitRequest getRecruitRequest);
}
