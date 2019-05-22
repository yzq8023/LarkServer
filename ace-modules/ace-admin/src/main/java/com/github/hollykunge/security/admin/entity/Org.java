package com.github.hollykunge.security.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Org {

    @Column(name = "H_ID")
    private Long hId;

    @Column(name = "SYS_ID")
    private String sysId;

    @Column(name = "ORG_LEVEL")
    private Integer orgLevel;

    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "CASIC_ORG_SECRET")
    private String casicOrgSecret;

    @Column(name = "CASIC_ORG_CODE")
    private String casicOrgCode;

    @Column(name = "CASIC_PORG_CODE")
    private String casicPorgCode;

    @Column(name = "EXTERNAL_NAME")
    private String externalName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "DISP_TIME")
    private Date dispTime;

    @Column(name = "SYNC_TYPE")
    private String syncType;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REFA")
    private String refa;

    @Column(name = "REFB")
    private String refb;

    @Column(name = "ORG_CODE")
    private String orgCode;

    @Column(name = "ORG_CODES")
    private String orgCodes;

    @Column(name = "ORG_NAMES")
    private String orgNames;

    @Column(name = "MILI_CODE")
    private String miliCode;

    @Column(name = "ORGNATION_CODE")
    private String orgnationCode;

    @Column(name = "CREDIT_CODE")
    private String creditCode;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA_COUNTY")
    private String areaCounty;

    @Column(name = "LANGUAGES")
    private String languages;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "INFOR_LEVEL")
    private String inforLevel;

    @Column(name = "ORG_NATURE")
    private String orgNature;

    @Column(name = "IMMEUP_ORG")
    private String immeupOrg;

    @Column(name = "ORG_MAN_LEVEL")
    private String orgManLevel;

    @Column(name = "IS_LI_COMPANY")
    private String isLiCompany;

    @Column(name = "FILL_TWO_ORG")
    private String fillTwoOrg;

    @Column(name = "FILL_THR_ORG")
    private String fillThrOrg;

    @Column(name = "ERP_COM_CODE")
    private String erpComCode;

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "INDUCLASS")
    private String induclass;

    @Column(name = "INNER_ID")
    private String innerId;

    @Column(name = "FC_FLAG")
    private String fcFlag;

    @Column(name = "XH")
    private String xh;

    @Column(name = "MD_CODE")
    private String mdCode;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "ORG_TYPE")
    private String orgType;

    @Column(name = "DESCRIPTION")
    private String description;
}