package com.github.hollykunge.security.admin.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Org {
    @Id
    @Column(name = "ID")
    private String id;

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

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER")
    private String crtUser;

    @Column(name = "CRT_NAME")
    private String crtName;

    @Column(name = "CRT_HOST")
    private String crtHost;

    @Column(name = "UPD_TIME")
    private Date updTime;

    @Column(name = "UPD_USER")
    private String updUser;

    @Column(name = "UPD_NAME")
    private String updName;

    @Column(name = "UPD_HOST")
    private String updHost;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "ATTR5")
    private String attr5;

    @Column(name = "ATTR6")
    private String attr6;

    @Column(name = "ATTR7")
    private String attr7;

    @Column(name = "ATTR8")
    private String attr8;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return H_ID
     */
    public Long gethId() {
        return hId;
    }

    /**
     * @param hId
     */
    public void sethId(Long hId) {
        this.hId = hId;
    }

    /**
     * @return SYS_ID
     */
    public String getSysId() {
        return sysId;
    }

    /**
     * @param sysId
     */
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    /**
     * @return ORG_LEVEL
     */
    public Integer getOrgLevel() {
        return orgLevel;
    }

    /**
     * @param orgLevel
     */
    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    /**
     * @return ORG_NAME
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return CASIC_ORG_SECRET
     */
    public String getCasicOrgSecret() {
        return casicOrgSecret;
    }

    /**
     * @param casicOrgSecret
     */
    public void setCasicOrgSecret(String casicOrgSecret) {
        this.casicOrgSecret = casicOrgSecret;
    }

    /**
     * @return CASIC_ORG_CODE
     */
    public String getCasicOrgCode() {
        return casicOrgCode;
    }

    /**
     * @param casicOrgCode
     */
    public void setCasicOrgCode(String casicOrgCode) {
        this.casicOrgCode = casicOrgCode;
    }

    /**
     * @return CASIC_PORG_CODE
     */
    public String getCasicPorgCode() {
        return casicPorgCode;
    }

    /**
     * @param casicPorgCode
     */
    public void setCasicPorgCode(String casicPorgCode) {
        this.casicPorgCode = casicPorgCode;
    }

    /**
     * @return EXTERNAL_NAME
     */
    public String getExternalName() {
        return externalName;
    }

    /**
     * @param externalName
     */
    public void setExternalName(String externalName) {
        this.externalName = externalName;
    }

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return ORDER_ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return DISP_TIME
     */
    public Date getDispTime() {
        return dispTime;
    }

    /**
     * @param dispTime
     */
    public void setDispTime(Date dispTime) {
        this.dispTime = dispTime;
    }

    /**
     * @return SYNC_TYPE
     */
    public String getSyncType() {
        return syncType;
    }

    /**
     * @param syncType
     */
    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    /**
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return REFA
     */
    public String getRefa() {
        return refa;
    }

    /**
     * @param refa
     */
    public void setRefa(String refa) {
        this.refa = refa;
    }

    /**
     * @return REFB
     */
    public String getRefb() {
        return refb;
    }

    /**
     * @param refb
     */
    public void setRefb(String refb) {
        this.refb = refb;
    }

    /**
     * @return ORG_CODE
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return ORG_CODES
     */
    public String getOrgCodes() {
        return orgCodes;
    }

    /**
     * @param orgCodes
     */
    public void setOrgCodes(String orgCodes) {
        this.orgCodes = orgCodes;
    }

    /**
     * @return ORG_NAMES
     */
    public String getOrgNames() {
        return orgNames;
    }

    /**
     * @param orgNames
     */
    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    /**
     * @return MILI_CODE
     */
    public String getMiliCode() {
        return miliCode;
    }

    /**
     * @param miliCode
     */
    public void setMiliCode(String miliCode) {
        this.miliCode = miliCode;
    }

    /**
     * @return ORGNATION_CODE
     */
    public String getOrgnationCode() {
        return orgnationCode;
    }

    /**
     * @param orgnationCode
     */
    public void setOrgnationCode(String orgnationCode) {
        this.orgnationCode = orgnationCode;
    }

    /**
     * @return CREDIT_CODE
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * @param creditCode
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    /**
     * @return COUNTRY
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return PROVINCE
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return CITY
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return AREA_COUNTY
     */
    public String getAreaCounty() {
        return areaCounty;
    }

    /**
     * @param areaCounty
     */
    public void setAreaCounty(String areaCounty) {
        this.areaCounty = areaCounty;
    }

    /**
     * @return LANGUAGES
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * @param languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * @return CURRENCY
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return INFOR_LEVEL
     */
    public String getInforLevel() {
        return inforLevel;
    }

    /**
     * @param inforLevel
     */
    public void setInforLevel(String inforLevel) {
        this.inforLevel = inforLevel;
    }

    /**
     * @return ORG_NATURE
     */
    public String getOrgNature() {
        return orgNature;
    }

    /**
     * @param orgNature
     */
    public void setOrgNature(String orgNature) {
        this.orgNature = orgNature;
    }

    /**
     * @return IMMEUP_ORG
     */
    public String getImmeupOrg() {
        return immeupOrg;
    }

    /**
     * @param immeupOrg
     */
    public void setImmeupOrg(String immeupOrg) {
        this.immeupOrg = immeupOrg;
    }

    /**
     * @return ORG_MAN_LEVEL
     */
    public String getOrgManLevel() {
        return orgManLevel;
    }

    /**
     * @param orgManLevel
     */
    public void setOrgManLevel(String orgManLevel) {
        this.orgManLevel = orgManLevel;
    }

    /**
     * @return IS_LI_COMPANY
     */
    public String getIsLiCompany() {
        return isLiCompany;
    }

    /**
     * @param isLiCompany
     */
    public void setIsLiCompany(String isLiCompany) {
        this.isLiCompany = isLiCompany;
    }

    /**
     * @return FILL_TWO_ORG
     */
    public String getFillTwoOrg() {
        return fillTwoOrg;
    }

    /**
     * @param fillTwoOrg
     */
    public void setFillTwoOrg(String fillTwoOrg) {
        this.fillTwoOrg = fillTwoOrg;
    }

    /**
     * @return FILL_THR_ORG
     */
    public String getFillThrOrg() {
        return fillThrOrg;
    }

    /**
     * @param fillThrOrg
     */
    public void setFillThrOrg(String fillThrOrg) {
        this.fillThrOrg = fillThrOrg;
    }

    /**
     * @return ERP_COM_CODE
     */
    public String getErpComCode() {
        return erpComCode;
    }

    /**
     * @param erpComCode
     */
    public void setErpComCode(String erpComCode) {
        this.erpComCode = erpComCode;
    }

    /**
     * @return ORDER_NUMBER
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return INDUCLASS
     */
    public String getInduclass() {
        return induclass;
    }

    /**
     * @param induclass
     */
    public void setInduclass(String induclass) {
        this.induclass = induclass;
    }

    /**
     * @return INNER_ID
     */
    public String getInnerId() {
        return innerId;
    }

    /**
     * @param innerId
     */
    public void setInnerId(String innerId) {
        this.innerId = innerId;
    }

    /**
     * @return FC_FLAG
     */
    public String getFcFlag() {
        return fcFlag;
    }

    /**
     * @param fcFlag
     */
    public void setFcFlag(String fcFlag) {
        this.fcFlag = fcFlag;
    }

    /**
     * @return XH
     */
    public String getXh() {
        return xh;
    }

    /**
     * @param xh
     */
    public void setXh(String xh) {
        this.xh = xh;
    }

    /**
     * @return MD_CODE
     */
    public String getMdCode() {
        return mdCode;
    }

    /**
     * @param mdCode
     */
    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    /**
     * @return PARENT_ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return ORG_TYPE
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * @param orgType
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * @return DESCRIPTION
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return CRT_TIME
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * @return CRT_USER
     */
    public String getCrtUser() {
        return crtUser;
    }

    /**
     * @param crtUser
     */
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    /**
     * @return CRT_NAME
     */
    public String getCrtName() {
        return crtName;
    }

    /**
     * @param crtName
     */
    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    /**
     * @return CRT_HOST
     */
    public String getCrtHost() {
        return crtHost;
    }

    /**
     * @param crtHost
     */
    public void setCrtHost(String crtHost) {
        this.crtHost = crtHost;
    }

    /**
     * @return UPD_TIME
     */
    public Date getUpdTime() {
        return updTime;
    }

    /**
     * @param updTime
     */
    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    /**
     * @return UPD_USER
     */
    public String getUpdUser() {
        return updUser;
    }

    /**
     * @param updUser
     */
    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    /**
     * @return UPD_NAME
     */
    public String getUpdName() {
        return updName;
    }

    /**
     * @param updName
     */
    public void setUpdName(String updName) {
        this.updName = updName;
    }

    /**
     * @return UPD_HOST
     */
    public String getUpdHost() {
        return updHost;
    }

    /**
     * @param updHost
     */
    public void setUpdHost(String updHost) {
        this.updHost = updHost;
    }

    /**
     * @return ATTR1
     */
    public String getAttr1() {
        return attr1;
    }

    /**
     * @param attr1
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    /**
     * @return ATTR2
     */
    public String getAttr2() {
        return attr2;
    }

    /**
     * @param attr2
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    /**
     * @return ATTR3
     */
    public String getAttr3() {
        return attr3;
    }

    /**
     * @param attr3
     */
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    /**
     * @return ATTR4
     */
    public String getAttr4() {
        return attr4;
    }

    /**
     * @param attr4
     */
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    /**
     * @return ATTR5
     */
    public String getAttr5() {
        return attr5;
    }

    /**
     * @param attr5
     */
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * @return ATTR6
     */
    public String getAttr6() {
        return attr6;
    }

    /**
     * @param attr6
     */
    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    /**
     * @return ATTR7
     */
    public String getAttr7() {
        return attr7;
    }

    /**
     * @param attr7
     */
    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    /**
     * @return ATTR8
     */
    public String getAttr8() {
        return attr8;
    }

    /**
     * @param attr8
     */
    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }
}