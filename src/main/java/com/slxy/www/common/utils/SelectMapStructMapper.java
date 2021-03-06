

package com.slxy.www.common.utils;


import com.slxy.www.model.SelectSubject;
import com.slxy.www.model.SelectTopic;
import com.slxy.www.model.SelectUserBase;
import com.slxy.www.model.dto.SelectSubjectDto;
import com.slxy.www.model.dto.SelectTopicDto;
import com.slxy.www.model.dto.SelectUserBaseDto;
import com.slxy.www.model.vo.SelectSubjectVo;
import com.slxy.www.model.vo.SelectTopicVo;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
@SuppressWarnings("all")
public interface SelectMapStructMapper {

    SelectMapStructMapper INSTANCE = Mappers.getMapper(SelectMapStructMapper.class);


    SelectUserBaseDto SelectUserBasePo2Dto(SelectUserBase user);

    List<SelectUserBaseDto> SelectUserBasesPo2Dto(List<SelectUserBase> userList);

    SelectSubjectDto SelectSubjectPoToDto(SelectSubject subject);

    List<SelectSubjectDto> SelectSubjectsPoToDto(List<SelectSubject> subjectList);

    SelectSubject SelectSubjectVoToPo(SelectSubjectVo vo);

    SelectTopicDto SelectTopicPoToDto(SelectTopic topic);

    SelectTopic SelectTopicVoToPo(SelectTopicVo vo);
}


	