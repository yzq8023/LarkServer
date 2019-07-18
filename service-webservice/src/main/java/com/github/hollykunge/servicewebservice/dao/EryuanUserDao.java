package com.github.hollykunge.servicewebservice.dao;

import com.github.hollykunge.servicewebservice.model.EryuanOrg;
import com.github.hollykunge.servicewebservice.model.EryuanUser;

public interface EryuanUserDao {

    /**
     * 二院用户
     * @param eryuanUser	:导入二院用户
     * @return
     */
    public void saveEryuanUser(EryuanUser eryuanUser);

    /**
     * 二院用户
     * @param idcard_no	:查询二院用户
     * @return
     */
    public int queryEryuanUserByNoCount(String idcard_no);

    /**
     * 更新二院用户信息
     * @param eryuanUser	：用户信息
     * @return
     */
    public int updateEryuanUserInfo(EryuanUser eryuanUser);


    /**
     * 二院部门
     * @param eryuanOrg	:导入二院部门
     * @return
     */
    public void saveEryuanOrg(EryuanOrg eryuanOrg);

    /**
     * 二院部门
     * @param CASIC_ORG_CODE	:查询二院用户
     * @return
     */
    public int queryEryuanOggByOrgidCount(String CASIC_ORG_CODE);

    /**
     * 更新二院部门
     * @param eryuanOrg	：二院部门
     * @return
     */
    public int updateEryuanOrgInfo(EryuanOrg eryuanOrg);



}
