package com.liu.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Pagination <T>{
    private List<T> data;
    private boolean showPrevisous; //是否有上一页按钮
    private boolean showFirsPage;  //首页按钮
    private boolean showNext; //下一页
    private boolean showEndPage; //尾页
    private Integer page; //当前页
    private List<Integer> pages=new ArrayList<>();
    private Integer count;//总页数
    //计算总页数
    public void setPagination(Integer count/*总条*/, Integer page/*页数*/) {



        this.count=count;
        this.page=page;
    //  显示页
    pages.add(page);
    for (int i=1;i<=3;i++){

        if(page-i>0) {
            pages.add(0,page - i);
        }
        if(page+i<=count){
            pages.add(page+i);
        }
    }


//是否展示上一页
    if(page==1){
        showPrevisous=false;

    }else{
        showPrevisous=true;
    }
        //是否展示下一页
    if(page==count){
        showNext=false;
    }else{
        showNext=true;
    }

    //是否展示第一页
        if(pages.contains(1)){
       showFirsPage=false;
        }else{
            showFirsPage=true;
        }
        //是否展示最后一页
        if(pages.contains(count)){
            showEndPage=false;
        }
        else{
            showEndPage=true;
        }

    }
}
