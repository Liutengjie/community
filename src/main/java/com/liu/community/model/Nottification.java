package com.liu.community.model;

public class Nottification {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.id
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.notifier
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Long notifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.receiver
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Long receiver;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.outerId
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Long outerid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.notifer_name
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private String notiferName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.outer_title
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private String outerTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.type
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.gmtcreate
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Long gmtcreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nottification.status
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.id
     *
     * @return the value of nottification.id
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.id
     *
     * @param id the value for nottification.id
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.notifier
     *
     * @return the value of nottification.notifier
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Long getNotifier() {
        return notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.notifier
     *
     * @param notifier the value for nottification.notifier
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.receiver
     *
     * @return the value of nottification.receiver
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.receiver
     *
     * @param receiver the value for nottification.receiver
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.outerId
     *
     * @return the value of nottification.outerId
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Long getOuterid() {
        return outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.outerId
     *
     * @param outerid the value for nottification.outerId
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setOuterid(Long outerid) {
        this.outerid = outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.notifer_name
     *
     * @return the value of nottification.notifer_name
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public String getNotiferName() {
        return notiferName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.notifer_name
     *
     * @param notiferName the value for nottification.notifer_name
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setNotiferName(String notiferName) {
        this.notiferName = notiferName == null ? null : notiferName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.outer_title
     *
     * @return the value of nottification.outer_title
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public String getOuterTitle() {
        return outerTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.outer_title
     *
     * @param outerTitle the value for nottification.outer_title
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.type
     *
     * @return the value of nottification.type
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.type
     *
     * @param type the value for nottification.type
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.gmtcreate
     *
     * @return the value of nottification.gmtcreate
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Long getGmtcreate() {
        return gmtcreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.gmtcreate
     *
     * @param gmtcreate the value for nottification.gmtcreate
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setGmtcreate(Long gmtcreate) {
        this.gmtcreate = gmtcreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nottification.status
     *
     * @return the value of nottification.status
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nottification.status
     *
     * @param status the value for nottification.status
     *
     * @mbg.generated Wed Mar 18 14:35:37 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}