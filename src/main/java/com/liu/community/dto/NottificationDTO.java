package com.liu.community.dto;

import com.liu.community.model.User;
import lombok.Data;

@Data
public class NottificationDTO {

    private Long id;
    private Long gmtcreate;
    private Integer status;
   private Long notifier;
   private Long outerid;
   private String notiferName;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
