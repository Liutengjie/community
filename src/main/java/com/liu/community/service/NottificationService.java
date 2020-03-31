package com.liu.community.service;

import com.liu.community.dto.NottificationDTO;
import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.enums.NotificationStatusEnm;
import com.liu.community.enums.NotificationTypeEnum;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.NottificationMapper;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;
import com.liu.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NottificationService {

    @Autowired
    private NottificationMapper nottificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    public Pagination list(Long userid, Integer page, Integer size) {
        Pagination<NottificationDTO> pagination=new Pagination<>();
        NottificationExample nottificationExample=new NottificationExample();
        nottificationExample.createCriteria()
                .andReceiverEqualTo(userid);
        Integer count = (int)nottificationMapper.countByExample(nottificationExample);//总条数
        //计算总页数
        //计算总页数
        if(count%size==0){
            count/=size;
        }else{
            count=count/size+1;
        }

        //计算页码是否超标
        if(page>count){
            page=count;
        }
        else if(page<1){
            page=1;
        }

        pagination.setPagination(count,page);
        //起始坐标
        Integer offset=size*(page-1);
        //查询文章内容
        NottificationExample questionExample1 = new NottificationExample();
        questionExample1.createCriteria().andReceiverEqualTo(userid);
        questionExample1.setOrderByClause("gmtcreate desc");
        List<Nottification> nottifications = nottificationMapper.selectByExampleWithRowbounds(questionExample1, new RowBounds(offset, size));//分页
       if (nottifications.size()==0){
           return pagination;
       }

        List<NottificationDTO> nottificationDTOS=new ArrayList<>();
        for (Nottification nottification : nottifications) {
            NottificationDTO nottificationDTO = new NottificationDTO();
            BeanUtils.copyProperties(nottification,nottificationDTO);
            nottificationDTO.setTypeName(NotificationTypeEnum.nameOfType(nottification.getType()));
            nottificationDTOS.add(nottificationDTO);
        }

        pagination.setData(nottificationDTOS);
        return  pagination;
    }
    //更新操作
    public Long unreadCount(Long id) {
        NottificationExample nottificationExample = new NottificationExample();
        nottificationExample.createCriteria().andReceiverEqualTo(id)
        .andStatusEqualTo(NotificationStatusEnm.UNREAD.getStatus());
        return nottificationMapper.countByExample(nottificationExample);
    }

    public NottificationDTO read(Long id, User user) {

        Nottification nottification = nottificationMapper.selectByPrimaryKey(id);
        if (nottification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(nottification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        //更新状态
        nottification.setStatus(NotificationStatusEnm.READ.getStatus());
        nottificationMapper.updateByPrimaryKey(nottification);

        NottificationDTO nottificationDTO = new NottificationDTO();
        BeanUtils.copyProperties(nottification,nottificationDTO);
        nottificationDTO.setTypeName(NotificationTypeEnum.nameOfType(nottification.getType()));
   return  nottificationDTO;
    }
}
