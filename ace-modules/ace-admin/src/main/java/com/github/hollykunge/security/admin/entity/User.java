package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ADMIN_USER")
public class User extends BaseEntity {

    @Column(name = "H_ID")
    private Long hId;

    @Column(name = "SYS_ID")
    private String sysId;

    @Column(name = "CASIC_ORG_CODE")
    private String casicOrgCode;

    @Column(name = "OPERATOR_NAME")
    private String operatorName;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "P_ID")
    private String pId;

    @Column(name = "SECRET_LEVEL")
    private String secretLevel;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "O_TEL")
    private String oTel;

    @Column(name = "O_EMAIL")
    private String oEmail;

    @Column(name = "WORK_POST")
    private String workPost;

    @Column(name = "TEC_POST")
    private String tecPost;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "SYNC_TYPE")
    private String syncType;

    @Column(name = "DISP_TIME")
    private Date dispTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "REFA")
    private String refa;

    @Column(name = "REFB")
    private String refb;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "XH")
    private String xh;

    @Column(name = "TECH_POST_NAME")
    private String techPostName;

    @Column(name = "TECH_POST_CODE")
    private String techPostCode;

    @Column(name = "TECHNICAL_POST_NAME")
    private String technicalPostName;

    @Column(name = "TECHNICAL_POST_CODE")
    private String technicalPostCode;

    @Column(name = "TECH_LEVEL_NAME")
    private String techLevelName;

    @Column(name = "TECH_LEVEL_CODE")
    private String techLevelCode;

    @Column(name = "TECHNICAL_MC_NAME")
    private String technicalMcName;

    @Column(name = "TECHNICAL_MC_TCODE")
    private String technicalMcTcode;

    @Column(name = "HIGHEST_INDUSTRIAL")
    private String highestIndustrial;

    @Column(name = "INNER_ID")
    private String innerId;

    @Column(name = "OFFICE_ADDR")
    private String officeAddr;

    @Column(name = "XQAH")
    private String xqah;

    @Column(name = "POLICITAL_NAME")
    private String policitalName;

    @Column(name = "POLICITAL_CODE")
    private String policitalCode;

    @Column(name = "PSN_SECRET_LEVEL")
    private String psnSecretLevel;

    @Column(name = "DEPT_TYYH_ORG_CODE")
    private String deptTyyhOrgCode;

    @Column(name = "DEPT_CODE")
    private String deptCode;

    @Column(name = "IN_BU_DATE")
    private String inBuDate;

    @Column(name = "IN_PARTY_DATE")
    private String inPartyDate;

    @Column(name = "MARRY_NAME")
    private String marryName;

    @Column(name = "MARRY_CODE")
    private String marryCode;

    @Column(name = "IDENTITY_TYPE")
    private String identityType;

    @Column(name = "HOME_TOWN")
    private String homeTown;

    @Column(name = "HR_PSN_CODE")
    private String hrPsnCode;

    @Column(name = "INFO_SECRET_LEVEL_NAME")
    private String infoSecretLevelName;

    @Column(name = "INFO_SECRET_LEVEL_CODE")
    private String infoSecretLevelCode;

    @Column(name = "END_FLAG")
    private String endFlag;

    @Column(name = "CODE")
    private String code;

    @Column(name = "HIGHEST_EDU")
    private String highestEdu;

    @Column(name = "DYCN")
    private String dycn;

    @Column(name = "FC_FLAG")
    private String fcFlag;

    @Column(name = "NATIONALITY_NAME")
    private String nationalityName;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "MOBILE")
    private String mobile;


}