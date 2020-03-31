package com.liu.community.controller;

import com.liu.community.dto.FileDTO;
import com.liu.community.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UCoudController {
    @Autowired
    private UcloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String upload = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(upload);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/jiaoliu.png");

        return fileDTO;
    }
}
