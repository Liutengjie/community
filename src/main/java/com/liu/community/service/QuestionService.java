package com.liu.community.service;

import com.liu.community.dto.Pagination;
import com.liu.community.dto.QuestionDTO;
import com.liu.community.dto.QuestionQuerDTO;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import com.liu.community.mapper.QuestionExtMapper;
import com.liu.community.mapper.QuestionMapper;
import com.liu.community.mapper.UserMapper;

import com.liu.community.model.Question;
import com.liu.community.model.QuestionExample;
import com.liu.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

        @Autowired
        private QuestionMapper quesstionMapper;

        @Autowired
        private UserMapper usermapper;

        @Autowired
        private QuestionExtMapper questionExtMapper;

    public Pagination list(String search,Integer page, Integer size) {
        if (StringUtils.isNoneBlank(search)){
            String[] tags=StringUtils.split(search," ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        Pagination pagination=new Pagination();
        QuestionQuerDTO questionQuerDTO = new QuestionQuerDTO();
        questionQuerDTO.setSearch(search);
        Integer count =questionExtMapper.countBsySearch(questionQuerDTO);//总条数
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
        QuestionExample questionExample=new QuestionExample();
        questionExample.setOrderByClause("gmtcreate desc");
        questionQuerDTO.setSize(size);
        questionQuerDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQuerDTO);//分页

        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=usermapper.selectByPrimaryKey(question.getCreator()); //根据Creator查ID
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//属性赋值
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagination.setData(questionDTOList);//文章信息


        return  pagination;

    }
    //通过id查总条数，我的问题
    public Pagination list(Long userid, Integer page, Integer size) {
        Pagination pagination=new Pagination();
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userid);
        Integer count = (int)quesstionMapper.countByExample(questionExample);//总条数
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
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCreatorEqualTo(userid);
        questionExample1.setOrderByClause("gmtcreate desc");
        List<Question> questions = quesstionMapper.selectByExampleWithRowbounds(questionExample1, new RowBounds(offset, size));//分页
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        for (Question question : questions) {
            User user=usermapper.selectByPrimaryKey(question.getCreator()); //根据Creator查ID
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//属性赋值
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagination.setData(questionDTOList);//文章信息
        return pagination;
    }


    //查看文章逻辑
    public QuestionDTO getByid(Long id) {
    Question question=quesstionMapper.selectByPrimaryKey(id); //通过id查文章
        if (question==null){//如果查不到文章
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    QuestionDTO questionDTO=new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = usermapper.selectByPrimaryKey(question.getCreator());
    questionDTO.setUser(user);

        return questionDTO;
    }


    //发布逻辑
    public void createOrUpdate(Question question){
        //更新
        if(question.getId()!=null){
            Question question1 = new Question();
            question1.setGmtmodified(System.currentTimeMillis());
            question.setGmtcreate(System.currentTimeMillis());
            question1.setTitle(question.getTitle());
            question1.setDescription(question.getDescription());
            question1.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int update = quesstionMapper.updateByExampleSelective(question1, questionExample);//更新，通过id
            if(update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }else{//创建
            question.setGmtcreate(System.currentTimeMillis());
            question.setGmtmodified(question.getGmtcreate());
            question.setViewcont(0);
            question.setCommentCont(0);
            quesstionMapper.insert(question);
        }
    }

    //回复数加1

    public void inView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewcont(1);
        questionExtMapper.incView(question);

    }

    public List<QuestionDTO> selectRelatec(QuestionDTO questions) {
      if (StringUtils.isBlank(questions.getTag())){
          return new ArrayList<>();
      }
        String[] tags = StringUtils.split(questions.getTag(), ",");

        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questions.getId());
        question.setTag(regexpTag);
        List<Question> questions1 = questionExtMapper.selectRelated(question);
        List<QuestionDTO> collect = questions1.stream().map(question1 -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question1,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return collect;
    }
}
