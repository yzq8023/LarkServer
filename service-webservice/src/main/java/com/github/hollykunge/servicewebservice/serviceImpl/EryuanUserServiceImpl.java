package com.github.hollykunge.servicewebservice.serviceImpl;

import com.github.hollykunge.servicewebservice.dao.EryuanUserDao;
import com.github.hollykunge.servicewebservice.model.EryuanOrg;
import com.github.hollykunge.servicewebservice.model.EryuanUser;
import com.github.hollykunge.servicewebservice.service.EryuanUserService;


import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.util.XmlUtil;
import org.dom4j.Document;
import org.xmlunit.XMLUnitException;

import javax.xml.rpc.ServiceException;

@Service
@Transactional

public class EryuanUserServiceImpl implements EryuanUserService {

    @Autowired
    private EryuanUserDao eryuanUserDao;
    @Value("${spring.address}")
    private String address;
    @Value("${spring.userxmladdress}")
    private String userxmladdress;
    @Value("${spring.userreadxmladdress}")
    private String userreadxmladdress;

    /**
     * 获取二院组织和人员信息
     *
     * @author guxq
     */
    @Override
    public void saveEryuanUser() {


       // String templatefilePath = "D:\\organizationuser.xml";
        String rs = "";
        String rsxt = "";
        String logxmladdress = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatdisptime = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("配置地址adress" + address);
      //  File templateFile = new File(templatefilePath);
      //  org.w3c.dom.Document docResult = XmlUtil.readXML(templateFile);
      userInfoBizService infoBizService = new userInfoBizService();
        try {
            rsxt = infoBizService.getXmlUserInfo(address);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        rs = XmlUtil.format(rsxt);
        rs = rs.toLowerCase();
        org.w3c.dom.Document docxml = (org.w3c.dom.Document) XmlUtil.parseXml(rs);

        logxmladdress = userxmladdress + "\\" + formatdisptime.format(new Date()) + ".xml";
        //   System.out.println("logxmladdress"+logxmladdress);
        XmlUtil.toFile(docxml, logxmladdress);
        //       System.out.printf(formatdisptime.format(new Date()) + "接收到XML文件，文件内容：" + rs);
        HashMap<String, Object> rs_map = new HashMap<String, Object>();
        HashMap<String, Object> m_map = new HashMap<String, Object>();
        EryuanOrg eryuanOrg = new EryuanOrg();
        EryuanUser eryuanUser = new EryuanUser();
        try {
            rs_map = (HashMap<String, Object>) resolveXML(rs).get("data");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List l = (List) rs_map.get("list");
        //  System.out.println("SDFSDFS" + l);
        List user_list = new ArrayList();
        List org_list = new ArrayList();

        for (int j = 0; j < l.size(); j++) {
            if (!"".equals(l.get(j))) {
                m_map = (HashMap<String, Object>) l.get(j);
                String omname = "";
                user_list = null;
                org_list = null;
                Object om = null;
                HashMap<String, Object> userone_map = null;
                HashMap<String, Object> orgone_map = null;
                if (m_map.containsKey("uumoperatordisp")) {
                    om = m_map.get("uumoperatordisp");
                }
                if (m_map.containsKey("uumorganizationdisp")) {
                    om = m_map.get("uumorganizationdisp");
                }
                omname = om.getClass().getName();
                //   System.out.println("omanme=" + omname);

                if (omname.equals("java.util.ArrayList")) {
                    user_list = (List) m_map.get("uumoperatordisp");
                    //         System.out.println("userlist" + user_list);
                    org_list = (List) m_map.get("uumorganizationdisp");
                }
                if (omname.equals("java.util.HashMap")) {
                    orgone_map = (HashMap<String, Object>) m_map.get("uumorganizationdisp");
                    userone_map = (HashMap<String, Object>) m_map.get("uumoperatordisp");

                }


                if (!"".equals(user_list) && user_list != null) {
                    HashMap<String, Object> user_map = null;
                    for (int u = 0; u < user_list.size(); u++) {
                        user_map = (HashMap<String, Object>) user_list.get(u);
                        String P_ID = (String) user_map.get("pid");
                        if (P_ID != null && !"".equals(P_ID)) {

                            //     String USER_PASSWORD = SecureUtil.md5(USER_PASSWORD.getBytes("UTF-8").toString());//.md5(USER_PASSWORD.getBytes("UTF-8"));
                            String H_ID = user_map.get("hid").toString().trim();
                            String SYS_ID = user_map.get("sysid").toString().trim();
                            String CASIC_ORG_CODE = user_map.get("casicorgcode").toString().trim();
                            String OPERATOR_NAME = user_map.get("operatorname").toString().replaceAll(" ", "");
                            // String user_card =user_map.get("pid").toString().trim();
                            String EMP_CODE = user_map.get("empcode").toString().trim();
                            String SECRET_LEVEL = user_map.get("secretlevel").toString().trim();
                            String BIRTH_DATE = user_map.get("birthdate").toString().trim();
                            String GENDER = user_map.get("gender").toString().trim();
                            String O_TEL = user_map.get("otel").toString().trim();
                            String O_EMAIL = user_map.get("oemail").toString().trim();
                            String WORK_POST = user_map.get("workpost").toString().trim();
                            String TEC_POST = user_map.get("tecpost").toString().trim();
                            String ORDER_ID = user_map.get("orderid").toString().trim();
                            String SYNC_TYPE = user_map.get("synctype").toString().trim();
                            String DISP_TIME = user_map.get("disptime").toString().trim();
                            String STATUS = user_map.get("status").toString().trim();
                            //       System.out.println("Status" + STATUS);
                            String REMARK = user_map.get("remark").toString().trim();
                            String REFA = user_map.get("refa").toString().trim();
                            String REFB = user_map.get("refb").toString().trim();
                            String ORG_NAME = user_map.get("orgname").toString().trim();
                            Date userbirthDateed = null;
                            Date userdispTimed = null;
                            try {
                               if(!"".equals(BIRTH_DATE)) {
                                   userbirthDateed = format.parse(BIRTH_DATE);
                               }
                                if (!"".equals(DISP_TIME)) {
                                    userdispTimed = formatdisptime.parse(DISP_TIME);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            //       System.out.println("useranme" + OPERATOR_NAME);
                            eryuanUser.setH_ID(H_ID);
                            eryuanUser.setSYS_ID(SYS_ID);
                            eryuanUser.setP_ID(P_ID);
                            eryuanUser.setCASIC_ORG_CODE(CASIC_ORG_CODE);
                            eryuanUser.setOPERATOR_NAME(OPERATOR_NAME);
                            eryuanUser.setEMP_CODE(EMP_CODE);
                            eryuanUser.setSECRET_LEVEL(SECRET_LEVEL);
                            eryuanUser.setBIRTH_DATE(userbirthDateed);
                            eryuanUser.setGENDER(GENDER);
                            eryuanUser.setO_TEL(O_TEL);
                            eryuanUser.setO_EMAIL(O_EMAIL);
                            eryuanUser.setWORK_POST(WORK_POST);
                            eryuanUser.setTEC_POST(TEC_POST);
                            eryuanUser.setORDER_ID(ORDER_ID);
                            eryuanUser.setSYNC_TYPE(SYNC_TYPE);
                            eryuanUser.setDISP_TIME(userdispTimed);
                            eryuanUser.setSTATUS(STATUS);
                            eryuanUser.setREMARK(REMARK);
                            eryuanUser.setREFA(REFA);
                            eryuanUser.setREFB(REFB);
                            eryuanUser.setORG_NAME(ORG_NAME);
                            int countuser = queryEryuanUserByNoCount(P_ID);
                            if (countuser > 0) {
                                int updatesum = updateEryuanUserInfo(eryuanUser);
                                System.out.println("用户更新----->" + P_ID + ":" + OPERATOR_NAME);
                            } else {
                                String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                eryuanUser.setID(ID);
                                eryuanUserDao.saveEryuanUser(eryuanUser);
                                System.out.println("++++++++新增用户----->" + P_ID + ":" + OPERATOR_NAME);
                            }
                              /*  @Autowired
                                private UserMapper userMapper;*/
                            //        UserMapper userMapper =
                            //     UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                            //   userMapper.saveuserInfo(userInfo);
                            //System.out.println("sdfdfd"+saveinfo);
                            //   ControllerService ss=new ControllerService();
                            // ss.saveuserInfo();
                            //    String user_id = SERVER_ID + "." + PinYinUtil.getPinYin(user_name).replaceAll(" ", "") + "_" + idcard_no.substring(idcard_no.length() - 4);//避免姓名同音所有加身份证号后4位
                            //    String org_id = user_map.get("casicorgcode").toString().trim();
                            //     String user_level = user_map.get("secretlevel").toString().trim();
                            //   String user_order = user_map.get("orderid").toString().trim();
                        }
                    }
                }
                if (!"".equals(org_list) && org_list != null) {
                    HashMap<String, Object> org_map = null;
                    for (int o = 0; o < org_list.size(); o++) {
                        org_map = (HashMap<String, Object>) org_list.get(o);
                        String CASICORGCODE = (String) org_map.get("casicorgcode").toString();
                        //      System.out.println("CASICORGCODE" + CASICORGCODE);
                        if (CASICORGCODE != null && !"".equals(CASICORGCODE)) {
                            String HID = org_map.get("hid").toString();
                            String SYSID = org_map.get("sysid").toString();
                            String ORGLEVEL = org_map.get("orglevel").toString();
                            String ORGNAME = org_map.get("orgname").toString();
                            String CASICORGSECRET = org_map.get("casicorgsecret").toString();
                            String CASICPORGCODE = org_map.get("casicporgcode").toString();
                            String EXTERNALNAME = org_map.get("externalname").toString();
                            String REMARK = org_map.get("remark").toString();
                            String ORDERID = org_map.get("orderid").toString();
                            String DISPTIME = org_map.get("disptime").toString();
                            String SYNCTYPE = org_map.get("synctype").toString();
                            String STATUS = org_map.get("status").toString();
                            String REFA = org_map.get("refa").toString();
                            String REFB = org_map.get("refb").toString();
                            String ORGCODE = org_map.get("orgcode").toString();
                            String ORGCODES = org_map.get("orgcodes").toString();
                            String ORGNAMES = org_map.get("orgnames").toString();
                            String MILICODE = org_map.get("miltcode").toString();
                            String ORGNATIONCODE = org_map.get("orgnationcode").toString();
                            String CREDITCODE = org_map.get("creditcode").toString();
                            String COUNTRY = org_map.get("country").toString();
                            String PROVINCE = org_map.get("province").toString();
                            String CITY = org_map.get("city").toString();
                            String AREACOUNTY = org_map.get("areacounty").toString();
                            //   String LANGUAGES = orgone_map.get("languages").toString();
                            String CURRENCY = org_map.get("currency").toString();
                            String INFORLEVEL = org_map.get("inforlevel").toString();
                            String ORGNATURE = org_map.get("orgnature").toString();
                            String IMMEUPORG = org_map.get("immeuporg").toString();
                            String ORGMANLEVEL = org_map.get("orgmanlevel").toString();
                            String ISLICOMPANY = org_map.get("isltcompany").toString();
                            String FILLTWOORG = org_map.get("filltwoorg").toString();
                            String FILLTHRORG = org_map.get("fillthrorg").toString();
                            String ERPCOMCODE = org_map.get("erpcomcode").toString();
                            String ORDERNUMBER = org_map.get("ordernumber").toString();
                            String INDUCLASS = org_map.get("induclass").toString();

                            //        System.out.println("orgdipTIM" + DISPTIME);
                            Date orgdispTimed = null;
                            try {
                                if(!"".equals(DISPTIME)) {
                                    orgdispTimed = (Date) formatdisptime.parse(DISPTIME);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            eryuanOrg.setH_ID(HID);
                            eryuanOrg.setSYS_ID(SYSID);
                            eryuanOrg.setORG_LEVEL(ORGLEVEL);
                            eryuanOrg.setORG_NAME(ORGNAME);
                            eryuanOrg.setCASIC_ORG_SECRET(CASICORGSECRET);
                            eryuanOrg.setCASIC_ORG_CODE(CASICORGCODE);
                            eryuanOrg.setCASIC_PORG_CODE(CASICPORGCODE);
                            eryuanOrg.setEXTERNAL_NAME(EXTERNALNAME);
                            eryuanOrg.setREMARK(REMARK);
                            eryuanOrg.setORDER_ID(ORDERID);
                            eryuanOrg.setDISP_TIME(orgdispTimed);
                            eryuanOrg.setSYNC_TYPE(SYNCTYPE);
                            eryuanOrg.setSTATUS(STATUS);
                            eryuanOrg.setREFA(REFA);
                            eryuanOrg.setREFB(REFB);
                            eryuanOrg.setORG_CODE(ORGCODE);
                            eryuanOrg.setORG_CODES(ORGCODES);
                            eryuanOrg.setORG_NAMES(ORGNAMES);
                            eryuanOrg.setMILI_CODE(MILICODE);
                            eryuanOrg.setORGNATION_CODE(ORGNATIONCODE);
                            eryuanOrg.setCREDIT_CODE(CREDITCODE);
                            eryuanOrg.setCOUNTRY(COUNTRY);
                            eryuanOrg.setPROVINCE(PROVINCE);
                            eryuanOrg.setCITY(CITY);
                            eryuanOrg.setAREA_COUNTY(AREACOUNTY);
                            eryuanOrg.setCURRENCY(CURRENCY);
                            eryuanOrg.setINFOR_LEVEL(INFORLEVEL);
                            eryuanOrg.setORG_NATURE(ORGNATURE);
                            eryuanOrg.setIMMEUP_ORG(IMMEUPORG);
                            eryuanOrg.setORG_MAN_LEVEL(ORGMANLEVEL);
                            eryuanOrg.setIS_LI_COMPANY(ISLICOMPANY);
                            eryuanOrg.setFILL_TWO_ORG(FILLTWOORG);
                            eryuanOrg.setFILL_THR_ORG(FILLTHRORG);
                            eryuanOrg.setERP_COM_CODE(ERPCOMCODE);
                            eryuanOrg.setORDER_NUMBER(ORDERNUMBER);
                            eryuanOrg.setINDUCLASS(INDUCLASS);
                            //String serverid = SERVER_ID.toLowerCase();
                            //  String orgtype = "0";//因为统一用户下发数据只有到单位、所级，没有部门  所有默认0
                            //    String is_use = "1";
                            int countorg = eryuanUserDao.queryEryuanOggByOrgidCount(CASICORGCODE);
                            if (countorg > 0) {
                                int updateorgsum = eryuanUserDao.updateEryuanOrgInfo(eryuanOrg);
                                System.out.println("部门更新----->" + CASICORGCODE + ":" + ORGNAME);
                            } else {
                                String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                eryuanOrg.setID(ID);
                                eryuanUserDao.saveEryuanOrg(eryuanOrg);
                                System.out.println("++++++++新增部门----->" + CASICORGCODE + ":" + ORGNAME);
                            }
                        }
                    }


                }
                if (!"".equals(orgone_map) && orgone_map != null) {

                    String CASICORGCODE = (String) orgone_map.get("casicorgcode").toString();
                    //     System.out.println("CASICORGCODE" + CASICORGCODE);
                    if (CASICORGCODE != null && !"".equals(CASICORGCODE)) {
                        String HID = orgone_map.get("hid").toString();
                        String SYSID = orgone_map.get("sysid").toString();
                        String ORGLEVEL = orgone_map.get("orglevel").toString();
                        String ORGNAME = orgone_map.get("orgname").toString();
                        String CASICORGSECRET = orgone_map.get("casicorgsecret").toString();
                        String CASICPORGCODE = orgone_map.get("casicporgcode").toString();
                        String EXTERNALNAME = orgone_map.get("externalname").toString();
                        String REMARK = orgone_map.get("remark").toString();
                        String ORDERID = orgone_map.get("orderid").toString();
                        String DISPTIME = orgone_map.get("disptime").toString();
                        String SYNCTYPE = orgone_map.get("synctype").toString();
                        String STATUS = orgone_map.get("status").toString();
                        String REFA = orgone_map.get("refa").toString();
                        String REFB = orgone_map.get("refb").toString();
                        String ORGCODE = orgone_map.get("orgcode").toString();
                        String ORGCODES = orgone_map.get("orgcodes").toString();
                        String ORGNAMES = orgone_map.get("orgnames").toString();
                        String MILICODE = orgone_map.get("miltcode").toString();
                        String ORGNATIONCODE = orgone_map.get("orgnationcode").toString();
                        String CREDITCODE = orgone_map.get("creditcode").toString();
                        String COUNTRY = orgone_map.get("country").toString();
                        String PROVINCE = orgone_map.get("province").toString();
                        String CITY = orgone_map.get("city").toString();
                        String AREACOUNTY = orgone_map.get("areacounty").toString();
                        //   String LANGUAGES = orgone_map.get("languages").toString();
                        String CURRENCY = orgone_map.get("currency").toString();
                        String INFORLEVEL = orgone_map.get("inforlevel").toString();
                        String ORGNATURE = orgone_map.get("orgnature").toString();
                        String IMMEUPORG = orgone_map.get("immeuporg").toString();
                        String ORGMANLEVEL = orgone_map.get("orgmanlevel").toString();
                        String ISLICOMPANY = orgone_map.get("isltcompany").toString();
                        String FILLTWOORG = orgone_map.get("filltwoorg").toString();
                        String FILLTHRORG = orgone_map.get("fillthrorg").toString();
                        String ERPCOMCODE = orgone_map.get("erpcomcode").toString();
                        String ORDERNUMBER = orgone_map.get("ordernumber").toString();
                        String INDUCLASS = orgone_map.get("induclass").toString();

                        //      System.out.println("orgdipTIM" + DISPTIME);
                        Date orgdispTimed = null;
                        try {
                            if(!"".equals(DISPTIME)) {
                                orgdispTimed = (Date) formatdisptime.parse(DISPTIME);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        eryuanOrg.setH_ID(HID);
                        eryuanOrg.setSYS_ID(SYSID);
                        eryuanOrg.setORG_LEVEL(ORGLEVEL);
                        eryuanOrg.setORG_NAME(ORGNAME);
                        eryuanOrg.setCASIC_ORG_SECRET(CASICORGSECRET);
                        eryuanOrg.setCASIC_ORG_CODE(CASICORGCODE);
                        eryuanOrg.setCASIC_PORG_CODE(CASICPORGCODE);
                        eryuanOrg.setEXTERNAL_NAME(EXTERNALNAME);
                        eryuanOrg.setREMARK(REMARK);
                        eryuanOrg.setORDER_ID(ORDERID);
                        eryuanOrg.setDISP_TIME(orgdispTimed);
                        eryuanOrg.setSYNC_TYPE(SYNCTYPE);
                        eryuanOrg.setSTATUS(STATUS);
                        eryuanOrg.setREFA(REFA);
                        eryuanOrg.setREFB(REFB);
                        eryuanOrg.setORG_CODE(ORGCODE);
                        eryuanOrg.setORG_CODES(ORGCODES);
                        eryuanOrg.setORG_NAMES(ORGNAMES);
                        eryuanOrg.setMILI_CODE(MILICODE);
                        eryuanOrg.setORGNATION_CODE(ORGNATIONCODE);
                        eryuanOrg.setCREDIT_CODE(CREDITCODE);
                        eryuanOrg.setCOUNTRY(COUNTRY);
                        eryuanOrg.setPROVINCE(PROVINCE);
                        eryuanOrg.setCITY(CITY);
                        eryuanOrg.setAREA_COUNTY(AREACOUNTY);
                        eryuanOrg.setCURRENCY(CURRENCY);
                        eryuanOrg.setINFOR_LEVEL(INFORLEVEL);
                        eryuanOrg.setORG_NATURE(ORGNATURE);
                        eryuanOrg.setIMMEUP_ORG(IMMEUPORG);
                        eryuanOrg.setORG_MAN_LEVEL(ORGMANLEVEL);
                        eryuanOrg.setIS_LI_COMPANY(ISLICOMPANY);
                        eryuanOrg.setFILL_TWO_ORG(FILLTWOORG);
                        eryuanOrg.setFILL_THR_ORG(FILLTHRORG);
                        eryuanOrg.setERP_COM_CODE(ERPCOMCODE);
                        eryuanOrg.setORDER_NUMBER(ORDERNUMBER);
                        eryuanOrg.setINDUCLASS(INDUCLASS);

                        //String serverid = SERVER_ID.toLowerCase();
                        //  String orgtype = "0";//因为统一用户下发数据只有到单位、所级，没有部门  所有默认0
                        //    String is_use = "1";
                        int countorg = eryuanUserDao.queryEryuanOggByOrgidCount(CASICORGCODE);
                        if (countorg > 0) {
                            int updateorgsum = eryuanUserDao.updateEryuanOrgInfo(eryuanOrg);
                            System.out.println("部门更新----->" + CASICORGCODE + ":" + ORGNAME);
                        } else {
                            String ID = UUID.randomUUID().toString().replaceAll("-", "");
                            eryuanOrg.setID(ID);
                            eryuanUserDao.saveEryuanOrg(eryuanOrg);
                            System.out.println("++++++++新增部门----->" + CASICORGCODE + ":" + ORGNAME);
                        }
                    }

                }
                if (!"".equals(userone_map) && userone_map != null) {

                    String P_ID = (String) userone_map.get("pid");
                    if (P_ID != null && !"".equals(P_ID)) {
                        //     String USER_PASSWORD = SecureUtil.md5(USER_PASSWORD.getBytes("UTF-8").toString());//.md5(USER_PASSWORD.getBytes("UTF-8"));
                        String H_ID = userone_map.get("hid").toString().trim();
                        String SYS_ID = userone_map.get("sysid").toString().trim();
                        String CASIC_ORG_CODE = userone_map.get("casicorgcode").toString().trim();
                        String OPERATOR_NAME = userone_map.get("operatorname").toString().replaceAll(" ", "");
                        // String user_card =user_map.get("pid").toString().trim();
                        String EMP_CODE = userone_map.get("empcode").toString().trim();
                        String SECRET_LEVEL = userone_map.get("secretlevel").toString().trim();
                        String BIRTH_DATE = userone_map.get("birthdate").toString().trim();
                        String GENDER = userone_map.get("gender").toString().trim();
                        String O_TEL = userone_map.get("otel").toString().trim();
                        String O_EMAIL = userone_map.get("oemail").toString().trim();
                        String WORK_POST = userone_map.get("workpost").toString().trim();
                        String TEC_POST = userone_map.get("tecpost").toString().trim();
                        String ORDER_ID = userone_map.get("orderid").toString().trim();
                        String SYNC_TYPE = userone_map.get("synctype").toString().trim();
                        String DISP_TIME = userone_map.get("disptime").toString().trim();
                        String STATUS = userone_map.get("status").toString().trim();
                        //     System.out.println("Status" + STATUS);
                        String REMARK = userone_map.get("remark").toString().trim();
                        String REFA = userone_map.get("refa").toString().trim();
                        String REFB = userone_map.get("refb").toString().trim();
                        String ORG_NAME = userone_map.get("orgname").toString().trim();
                        Date userbirthDateed = null;
                        Date userdispTimed = null;
                        try {
                            if(!"".equals(BIRTH_DATE)) {
                                userbirthDateed = format.parse(BIRTH_DATE);
                            }
                            if (!"".equals(DISP_TIME)) {
                                userdispTimed = formatdisptime.parse(DISP_TIME);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        //      System.out.println("useranme" + OPERATOR_NAME);
                        eryuanUser.setH_ID(H_ID);
                        eryuanUser.setSYS_ID(SYS_ID);
                        eryuanUser.setP_ID(P_ID);
                        eryuanUser.setCASIC_ORG_CODE(CASIC_ORG_CODE);
                        eryuanUser.setOPERATOR_NAME(OPERATOR_NAME);
                        eryuanUser.setEMP_CODE(EMP_CODE);
                        eryuanUser.setSECRET_LEVEL(SECRET_LEVEL);
                        eryuanUser.setBIRTH_DATE(userbirthDateed);
                        eryuanUser.setGENDER(GENDER);
                        eryuanUser.setO_TEL(O_TEL);
                        eryuanUser.setO_EMAIL(O_EMAIL);
                        eryuanUser.setWORK_POST(WORK_POST);
                        eryuanUser.setTEC_POST(TEC_POST);
                        eryuanUser.setORDER_ID(ORDER_ID);
                        eryuanUser.setSYNC_TYPE(SYNC_TYPE);
                        eryuanUser.setDISP_TIME(userdispTimed);
                        eryuanUser.setSTATUS(STATUS);
                        eryuanUser.setREMARK(REMARK);
                        eryuanUser.setREFA(REFA);
                        eryuanUser.setREFB(REFB);
                        eryuanUser.setORG_NAME(ORG_NAME);
                        int countuser = queryEryuanUserByNoCount(P_ID);
                        if (countuser > 0) {
                            int updatesum = updateEryuanUserInfo(eryuanUser);
                            System.out.println("用户更新----->" + P_ID + ":" + OPERATOR_NAME);
                        } else {
                            String ID = UUID.randomUUID().toString().replaceAll("-", "");
                            eryuanUser.setID(ID);
                            eryuanUserDao.saveEryuanUser(eryuanUser);
                            System.out.println("++++++++新增用户----->" + P_ID + ":" + OPERATOR_NAME);
                        }
                              /*  @Autowired
                                private UserMapper userMapper;*/
                        //        UserMapper userMapper =
                        //     UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                        //   userMapper.saveuserInfo(userInfo);
                        //System.out.println("sdfdfd"+saveinfo);
                        //   ControllerService ss=new ControllerService();
                        // ss.saveuserInfo();
                        //    String user_id = SERVER_ID + "." + PinYinUtil.getPinYin(user_name).replaceAll(" ", "") + "_" + idcard_no.substring(idcard_no.length() - 4);//避免姓名同音所有加身份证号后4位
                        //    String org_id = user_map.get("casicorgcode").toString().trim();
                        //     String user_level = user_map.get("secretlevel").toString().trim();
                        //   String user_order = user_map.get("orderid").toString().trim();
                    }

                }

            }

        }
    }

    /**
     * 获取二院组织和人员信息
     *
     * @author guxq
     */
    @Override
    public void saveXmlFileEryuanUser() {


        String templatefilePath = "D:\\organizationuser.xml";
        String rs = "";
        String rsxt = "";
        String logxmladdress = "";

        String[] readxmllist = getAllXmlFileList(userreadxmladdress);
        if(readxmllist.length<1){
            System.out.println("手动通过配置XML文件导入用户数据，请在指定目录至少放入一个XML文件");
            return;
        }
        for (String ifilename : readxmllist) {

            if (readxmllist.length > 1 && ifilename.endsWith(".xml")) {
                System.out.println("手动导入用户数据，现已放置"+readxmllist.length+"个文件");
                templatefilePath = userreadxmladdress +"\\"+ ifilename;


                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatdisptime = new SimpleDateFormat("yyyyMMddHHmmss");
                System.out.println("配置地址adress" + templatefilePath);
                File templateFile = new File(templatefilePath);
                org.w3c.dom.Document docResult = XmlUtil.readXML(templateFile);
     /*   userInfoBizService infoBizService = new userInfoBizService();
        try {
            rsxt = infoBizService.getXmlUserInfo(address);
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/

                rs = XmlUtil.format(docResult);
                rs = rs.toLowerCase();
                org.w3c.dom.Document docxml = (org.w3c.dom.Document) XmlUtil.parseXml(rs);

                logxmladdress = userxmladdress + "\\" + formatdisptime.format(new Date()) + ".xml";
                //   System.out.println("logxmladdress"+logxmladdress);
                XmlUtil.toFile(docxml, logxmladdress);
                //       System.out.printf(formatdisptime.format(new Date()) + "接收到XML文件，文件内容：" + rs);
                HashMap<String, Object> rs_map = new HashMap<String, Object>();
                HashMap<String, Object> m_map = new HashMap<String, Object>();
                EryuanOrg eryuanOrg = new EryuanOrg();
                EryuanUser eryuanUser = new EryuanUser();
                try {
                    rs_map = (HashMap<String, Object>) resolveXML(rs).get("data");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                List l = (List) rs_map.get("list");
                //  System.out.println("SDFSDFS" + l);
                List user_list = new ArrayList();
                List org_list = new ArrayList();

                for (int j = 0; j < l.size(); j++) {
                    if (!"".equals(l.get(j))) {
                        m_map = (HashMap<String, Object>) l.get(j);
                        String omname = "";
                        user_list = null;
                        org_list = null;
                        Object om = null;
                        HashMap<String, Object> userone_map = null;
                        HashMap<String, Object> orgone_map = null;
                        if (m_map.containsKey("uumoperatordisp")) {
                            om = m_map.get("uumoperatordisp");
                        }
                        if (m_map.containsKey("uumorganizationdisp")) {
                            om = m_map.get("uumorganizationdisp");
                        }
                        omname = om.getClass().getName();
                        //   System.out.println("omanme=" + omname);

                        if (omname.equals("java.util.ArrayList")) {
                            user_list = (List) m_map.get("uumoperatordisp");
                            //         System.out.println("userlist" + user_list);
                             org_list = (List) m_map.get("uumorganizationdisp");
                        }
                        if (omname.equals("java.util.HashMap")) {
                            orgone_map = (HashMap<String, Object>) m_map.get("uumorganizationdisp");
                            userone_map = (HashMap<String, Object>) m_map.get("uumoperatordisp");

                        }


                        if (!"".equals(user_list) && user_list != null) {
                            HashMap<String, Object> user_map = null;
                            for (int u = 0; u < user_list.size(); u++) {
                                user_map = (HashMap<String, Object>) user_list.get(u);
                                String P_ID = (String) user_map.get("pid");
                                if (P_ID != null && !"".equals(P_ID)) {

                                    //     String USER_PASSWORD = SecureUtil.md5(USER_PASSWORD.getBytes("UTF-8").toString());//.md5(USER_PASSWORD.getBytes("UTF-8"));
                                    String H_ID = user_map.get("hid").toString().trim();
                                    String SYS_ID = user_map.get("sysid").toString().trim();
                                    String CASIC_ORG_CODE = user_map.get("casicorgcode").toString().trim();
                                    String OPERATOR_NAME = user_map.get("operatorname").toString().replaceAll(" ", "");
                                    // String user_card =user_map.get("pid").toString().trim();
                                    String EMP_CODE = user_map.get("empcode").toString().trim();
                                    String SECRET_LEVEL = user_map.get("secretlevel").toString().trim();
                                    String BIRTH_DATE = user_map.get("birthdate").toString().trim();
                                    String GENDER = user_map.get("gender").toString().trim();
                                    String O_TEL = user_map.get("otel").toString().trim();
                                    String O_EMAIL = user_map.get("oemail").toString().trim();
                                    String WORK_POST = user_map.get("workpost").toString().trim();
                                    String TEC_POST = user_map.get("tecpost").toString().trim();
                                    String ORDER_ID = user_map.get("orderid").toString().trim();
                                    String SYNC_TYPE = user_map.get("synctype").toString().trim();
                                    String DISP_TIME = user_map.get("disptime").toString().trim();
                                    String STATUS = user_map.get("status").toString().trim();
                                    //       System.out.println("Status" + STATUS);
                                    String REMARK = user_map.get("remark").toString().trim();
                                    String REFA = user_map.get("refa").toString().trim();
                                    String REFB = user_map.get("refb").toString().trim();
                                    String ORG_NAME = user_map.get("orgname").toString().trim();
                                    Date userbirthDateed = null;
                                    Date userdispTimed = null;
                                    try {
                                        if(!"".equals(BIRTH_DATE)) {
                                            userbirthDateed = format.parse(BIRTH_DATE);
                                        }
                                        if (!"".equals(DISP_TIME)) {
                                            userdispTimed = formatdisptime.parse(DISP_TIME);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    //       System.out.println("useranme" + OPERATOR_NAME);
                                    eryuanUser.setH_ID(H_ID);
                                    eryuanUser.setSYS_ID(SYS_ID);
                                    eryuanUser.setP_ID(P_ID);
                                    eryuanUser.setCASIC_ORG_CODE(CASIC_ORG_CODE);
                                    eryuanUser.setOPERATOR_NAME(OPERATOR_NAME);
                                    eryuanUser.setEMP_CODE(EMP_CODE);
                                    eryuanUser.setSECRET_LEVEL(SECRET_LEVEL);
                                    eryuanUser.setBIRTH_DATE(userbirthDateed);
                                    eryuanUser.setGENDER(GENDER);
                                    eryuanUser.setO_TEL(O_TEL);
                                    eryuanUser.setO_EMAIL(O_EMAIL);
                                    eryuanUser.setWORK_POST(WORK_POST);
                                    eryuanUser.setTEC_POST(TEC_POST);
                                    eryuanUser.setORDER_ID(ORDER_ID);
                                    eryuanUser.setSYNC_TYPE(SYNC_TYPE);
                                    eryuanUser.setDISP_TIME(userdispTimed);
                                    eryuanUser.setSTATUS(STATUS);
                                    eryuanUser.setREMARK(REMARK);
                                    eryuanUser.setREFA(REFA);
                                    eryuanUser.setREFB(REFB);
                                    eryuanUser.setORG_NAME(ORG_NAME);
                                    int countuser = queryEryuanUserByNoCount(P_ID);
                                    if (countuser > 0) {
                                        int updatesum = updateEryuanUserInfo(eryuanUser);
                                        System.out.println("用户更新----->" + P_ID + ":" + OPERATOR_NAME);
                                    } else {
                                        String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                        eryuanUser.setID(ID);
                                        eryuanUserDao.saveEryuanUser(eryuanUser);
                                        System.out.println("++++++++新增用户----->" + P_ID + ":" + OPERATOR_NAME);
                                    }
                              /*  @Autowired
                                private UserMapper userMapper;*/
                                    //        UserMapper userMapper =
                                    //     UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                                    //   userMapper.saveuserInfo(userInfo);
                                    //System.out.println("sdfdfd"+saveinfo);
                                    //   ControllerService ss=new ControllerService();
                                    // ss.saveuserInfo();
                                    //    String user_id = SERVER_ID + "." + PinYinUtil.getPinYin(user_name).replaceAll(" ", "") + "_" + idcard_no.substring(idcard_no.length() - 4);//避免姓名同音所有加身份证号后4位
                                    //    String org_id = user_map.get("casicorgcode").toString().trim();
                                    //     String user_level = user_map.get("secretlevel").toString().trim();
                                    //   String user_order = user_map.get("orderid").toString().trim();
                                }
                            }
                        }
                        if (!"".equals(org_list) && org_list != null) {
                            HashMap<String, Object> org_map = null;
                            for (int o = 0; o < org_list.size(); o++) {
                                org_map = (HashMap<String, Object>) org_list.get(o);
                                String CASICORGCODE = (String) org_map.get("casicorgcode").toString();
                                //      System.out.println("CASICORGCODE" + CASICORGCODE);
                                if (CASICORGCODE != null && !"".equals(CASICORGCODE)) {
                                    String HID = org_map.get("hid").toString();
                                    String SYSID = org_map.get("sysid").toString();
                                    String ORGLEVEL = org_map.get("orglevel").toString();
                                    String ORGNAME = org_map.get("orgname").toString();
                                    String CASICORGSECRET = org_map.get("casicorgsecret").toString();
                                    String CASICPORGCODE = org_map.get("casicporgcode").toString();
                                    String EXTERNALNAME = org_map.get("externalname").toString();
                                    String REMARK = org_map.get("remark").toString();
                                    String ORDERID = org_map.get("orderid").toString();
                                    String DISPTIME = org_map.get("disptime").toString();
                                    String SYNCTYPE = org_map.get("synctype").toString();
                                    String STATUS = org_map.get("status").toString();
                                    String REFA = org_map.get("refa").toString();
                                    String REFB = org_map.get("refb").toString();
                                    String ORGCODE = org_map.get("orgcode").toString();
                                    String ORGCODES = org_map.get("orgcodes").toString();
                                    String ORGNAMES = org_map.get("orgnames").toString();
                                    String MILICODE = org_map.get("miltcode").toString();
                                    String ORGNATIONCODE = org_map.get("orgnationcode").toString();
                                    String CREDITCODE = org_map.get("creditcode").toString();
                                    String COUNTRY = org_map.get("country").toString();
                                    String PROVINCE = org_map.get("province").toString();
                                    String CITY = org_map.get("city").toString();
                                    String AREACOUNTY = org_map.get("areacounty").toString();
                                    //   String LANGUAGES = orgone_map.get("languages").toString();
                                    String CURRENCY = org_map.get("currency").toString();
                                    String INFORLEVEL = org_map.get("inforlevel").toString();
                                    String ORGNATURE = org_map.get("orgnature").toString();
                                    String IMMEUPORG = org_map.get("immeuporg").toString();
                                    String ORGMANLEVEL = org_map.get("orgmanlevel").toString();
                                    String ISLICOMPANY = org_map.get("isltcompany").toString();
                                    String FILLTWOORG = org_map.get("filltwoorg").toString();
                                    String FILLTHRORG = org_map.get("fillthrorg").toString();
                                    String ERPCOMCODE = org_map.get("erpcomcode").toString();
                                    String ORDERNUMBER = org_map.get("ordernumber").toString();
                                    String INDUCLASS = org_map.get("induclass").toString();

                                    //        System.out.println("orgdipTIM" + DISPTIME);
                                    Date orgdispTimed = null;
                                    try {
                                        if(!"".equals(DISPTIME)) {
                                            orgdispTimed = (Date) formatdisptime.parse(DISPTIME);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    eryuanOrg.setH_ID(HID);
                                    eryuanOrg.setSYS_ID(SYSID);
                                    eryuanOrg.setORG_LEVEL(ORGLEVEL);
                                    eryuanOrg.setORG_NAME(ORGNAME);
                                    eryuanOrg.setCASIC_ORG_SECRET(CASICORGSECRET);
                                    eryuanOrg.setCASIC_ORG_CODE(CASICORGCODE);
                                    eryuanOrg.setCASIC_PORG_CODE(CASICPORGCODE);
                                    eryuanOrg.setEXTERNAL_NAME(EXTERNALNAME);
                                    eryuanOrg.setREMARK(REMARK);
                                    eryuanOrg.setORDER_ID(ORDERID);
                                    eryuanOrg.setDISP_TIME(orgdispTimed);
                                    eryuanOrg.setSYNC_TYPE(SYNCTYPE);
                                    eryuanOrg.setSTATUS(STATUS);
                                    eryuanOrg.setREFA(REFA);
                                    eryuanOrg.setREFB(REFB);
                                    eryuanOrg.setORG_CODE(ORGCODE);
                                    eryuanOrg.setORG_CODES(ORGCODES);
                                    eryuanOrg.setORG_NAMES(ORGNAMES);
                                    eryuanOrg.setMILI_CODE(MILICODE);
                                    eryuanOrg.setORGNATION_CODE(ORGNATIONCODE);
                                    eryuanOrg.setCREDIT_CODE(CREDITCODE);
                                    eryuanOrg.setCOUNTRY(COUNTRY);
                                    eryuanOrg.setPROVINCE(PROVINCE);
                                    eryuanOrg.setCITY(CITY);
                                    eryuanOrg.setAREA_COUNTY(AREACOUNTY);
                                    eryuanOrg.setCURRENCY(CURRENCY);
                                    eryuanOrg.setINFOR_LEVEL(INFORLEVEL);
                                    eryuanOrg.setORG_NATURE(ORGNATURE);
                                    eryuanOrg.setIMMEUP_ORG(IMMEUPORG);
                                    eryuanOrg.setORG_MAN_LEVEL(ORGMANLEVEL);
                                    eryuanOrg.setIS_LI_COMPANY(ISLICOMPANY);
                                    eryuanOrg.setFILL_TWO_ORG(FILLTWOORG);
                                    eryuanOrg.setFILL_THR_ORG(FILLTHRORG);
                                    eryuanOrg.setERP_COM_CODE(ERPCOMCODE);
                                    eryuanOrg.setORDER_NUMBER(ORDERNUMBER);
                                    eryuanOrg.setINDUCLASS(INDUCLASS);
                                    //String serverid = SERVER_ID.toLowerCase();
                                    //  String orgtype = "0";//因为统一用户下发数据只有到单位、所级，没有部门  所有默认0
                                    //    String is_use = "1";
                                    int countorg = eryuanUserDao.queryEryuanOggByOrgidCount(CASICORGCODE);
                                    if (countorg > 0) {
                                        int updateorgsum = eryuanUserDao.updateEryuanOrgInfo(eryuanOrg);
                                        System.out.println("部门更新----->" + CASICORGCODE + ":" + ORGNAME);
                                    } else {
                                        String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                        eryuanOrg.setID(ID);
                                        eryuanUserDao.saveEryuanOrg(eryuanOrg);
                                        System.out.println("++++++++新增部门----->" + CASICORGCODE + ":" + ORGNAME);
                                    }
                                }
                            }


                        }
                        if (!"".equals(orgone_map) && orgone_map != null) {

                            String CASICORGCODE = (String) orgone_map.get("casicorgcode").toString();
                            //     System.out.println("CASICORGCODE" + CASICORGCODE);
                            if (CASICORGCODE != null && !"".equals(CASICORGCODE)) {
                                String HID = orgone_map.get("hid").toString();
                                String SYSID = orgone_map.get("sysid").toString();
                                String ORGLEVEL = orgone_map.get("orglevel").toString();
                                String ORGNAME = orgone_map.get("orgname").toString();
                                String CASICORGSECRET = orgone_map.get("casicorgsecret").toString();
                                String CASICPORGCODE = orgone_map.get("casicporgcode").toString();
                                String EXTERNALNAME = orgone_map.get("externalname").toString();
                                String REMARK = orgone_map.get("remark").toString();
                                String ORDERID = orgone_map.get("orderid").toString();
                                String DISPTIME = orgone_map.get("disptime").toString();
                                String SYNCTYPE = orgone_map.get("synctype").toString();
                                String STATUS = orgone_map.get("status").toString();
                                String REFA = orgone_map.get("refa").toString();
                                String REFB = orgone_map.get("refb").toString();
                                String ORGCODE = orgone_map.get("orgcode").toString();
                                String ORGCODES = orgone_map.get("orgcodes").toString();
                                String ORGNAMES = orgone_map.get("orgnames").toString();
                                String MILICODE = orgone_map.get("miltcode").toString();
                                String ORGNATIONCODE = orgone_map.get("orgnationcode").toString();
                                String CREDITCODE = orgone_map.get("creditcode").toString();
                                String COUNTRY = orgone_map.get("country").toString();
                                String PROVINCE = orgone_map.get("province").toString();
                                String CITY = orgone_map.get("city").toString();
                                String AREACOUNTY = orgone_map.get("areacounty").toString();
                                //   String LANGUAGES = orgone_map.get("languages").toString();
                                String CURRENCY = orgone_map.get("currency").toString();
                                String INFORLEVEL = orgone_map.get("inforlevel").toString();
                                String ORGNATURE = orgone_map.get("orgnature").toString();
                                String IMMEUPORG = orgone_map.get("immeuporg").toString();
                                String ORGMANLEVEL = orgone_map.get("orgmanlevel").toString();
                                String ISLICOMPANY = orgone_map.get("isltcompany").toString();
                                String FILLTWOORG = orgone_map.get("filltwoorg").toString();
                                String FILLTHRORG = orgone_map.get("fillthrorg").toString();
                                String ERPCOMCODE = orgone_map.get("erpcomcode").toString();
                                String ORDERNUMBER = orgone_map.get("ordernumber").toString();
                                String INDUCLASS = orgone_map.get("induclass").toString();

                                //      System.out.println("orgdipTIM" + DISPTIME);
                                Date orgdispTimed = null;
                                try {
                                    if(!"".equals(DISPTIME)) {
                                        orgdispTimed = (Date) formatdisptime.parse(DISPTIME);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                eryuanOrg.setH_ID(HID);
                                eryuanOrg.setSYS_ID(SYSID);
                                eryuanOrg.setORG_LEVEL(ORGLEVEL);
                                eryuanOrg.setORG_NAME(ORGNAME);
                                eryuanOrg.setCASIC_ORG_SECRET(CASICORGSECRET);
                                eryuanOrg.setCASIC_ORG_CODE(CASICORGCODE);
                                eryuanOrg.setCASIC_PORG_CODE(CASICPORGCODE);
                                eryuanOrg.setEXTERNAL_NAME(EXTERNALNAME);
                                eryuanOrg.setREMARK(REMARK);
                                eryuanOrg.setORDER_ID(ORDERID);
                                eryuanOrg.setDISP_TIME(orgdispTimed);
                                eryuanOrg.setSYNC_TYPE(SYNCTYPE);
                                eryuanOrg.setSTATUS(STATUS);
                                eryuanOrg.setREFA(REFA);
                                eryuanOrg.setREFB(REFB);
                                eryuanOrg.setORG_CODE(ORGCODE);
                                eryuanOrg.setORG_CODES(ORGCODES);
                                eryuanOrg.setORG_NAMES(ORGNAMES);
                                eryuanOrg.setMILI_CODE(MILICODE);
                                eryuanOrg.setORGNATION_CODE(ORGNATIONCODE);
                                eryuanOrg.setCREDIT_CODE(CREDITCODE);
                                eryuanOrg.setCOUNTRY(COUNTRY);
                                eryuanOrg.setPROVINCE(PROVINCE);
                                eryuanOrg.setCITY(CITY);
                                eryuanOrg.setAREA_COUNTY(AREACOUNTY);
                                eryuanOrg.setCURRENCY(CURRENCY);
                                eryuanOrg.setINFOR_LEVEL(INFORLEVEL);
                                eryuanOrg.setORG_NATURE(ORGNATURE);
                                eryuanOrg.setIMMEUP_ORG(IMMEUPORG);
                                eryuanOrg.setORG_MAN_LEVEL(ORGMANLEVEL);
                                eryuanOrg.setIS_LI_COMPANY(ISLICOMPANY);
                                eryuanOrg.setFILL_TWO_ORG(FILLTWOORG);
                                eryuanOrg.setFILL_THR_ORG(FILLTHRORG);
                                eryuanOrg.setERP_COM_CODE(ERPCOMCODE);
                                eryuanOrg.setORDER_NUMBER(ORDERNUMBER);
                                eryuanOrg.setINDUCLASS(INDUCLASS);

                                //String serverid = SERVER_ID.toLowerCase();
                                //  String orgtype = "0";//因为统一用户下发数据只有到单位、所级，没有部门  所有默认0
                                //    String is_use = "1";
                                int countorg = eryuanUserDao.queryEryuanOggByOrgidCount(CASICORGCODE);
                                if (countorg > 0) {
                                    int updateorgsum = eryuanUserDao.updateEryuanOrgInfo(eryuanOrg);
                                    System.out.println("部门更新----->" + CASICORGCODE + ":" + ORGNAME);
                                } else {
                                    String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                    eryuanOrg.setID(ID);
                                    eryuanUserDao.saveEryuanOrg(eryuanOrg);
                                    System.out.println("++++++++新增部门----->" + CASICORGCODE + ":" + ORGNAME);
                                }
                            }

                        }
                        if (!"".equals(userone_map) && userone_map != null) {

                            String P_ID = (String) userone_map.get("pid");
                            if (P_ID != null && !"".equals(P_ID)) {
                                //     String USER_PASSWORD = SecureUtil.md5(USER_PASSWORD.getBytes("UTF-8").toString());//.md5(USER_PASSWORD.getBytes("UTF-8"));
                                String H_ID = userone_map.get("hid").toString().trim();
                                String SYS_ID = userone_map.get("sysid").toString().trim();
                                String CASIC_ORG_CODE = userone_map.get("casicorgcode").toString().trim();
                                String OPERATOR_NAME = userone_map.get("operatorname").toString().replaceAll(" ", "");
                                // String user_card =user_map.get("pid").toString().trim();
                                String EMP_CODE = userone_map.get("empcode").toString().trim();
                                String SECRET_LEVEL = userone_map.get("secretlevel").toString().trim();
                                String BIRTH_DATE = userone_map.get("birthdate").toString().trim();
                                String GENDER = userone_map.get("gender").toString().trim();
                                String O_TEL = userone_map.get("otel").toString().trim();
                                String O_EMAIL = userone_map.get("oemail").toString().trim();
                                String WORK_POST = userone_map.get("workpost").toString().trim();
                                String TEC_POST = userone_map.get("tecpost").toString().trim();
                                String ORDER_ID = userone_map.get("orderid").toString().trim();
                                String SYNC_TYPE = userone_map.get("synctype").toString().trim();
                                String DISP_TIME = userone_map.get("disptime").toString().trim();
                                String STATUS = userone_map.get("status").toString().trim();
                                //     System.out.println("Status" + STATUS);
                                String REMARK = userone_map.get("remark").toString().trim();
                                String REFA = userone_map.get("refa").toString().trim();
                                String REFB = userone_map.get("refb").toString().trim();
                                String ORG_NAME = userone_map.get("orgname").toString().trim();
                                Date userbirthDateed = null;
                                Date userdispTimed = null;
                                try {
                                    if(!"".equals(BIRTH_DATE)) {
                                        userbirthDateed = format.parse(BIRTH_DATE);
                                    }
                                    if (!"".equals(DISP_TIME)) {
                                        userdispTimed = formatdisptime.parse(DISP_TIME);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                //      System.out.println("useranme" + OPERATOR_NAME);
                                eryuanUser.setH_ID(H_ID);
                                eryuanUser.setSYS_ID(SYS_ID);
                                eryuanUser.setP_ID(P_ID);
                                eryuanUser.setCASIC_ORG_CODE(CASIC_ORG_CODE);
                                eryuanUser.setOPERATOR_NAME(OPERATOR_NAME);
                                eryuanUser.setEMP_CODE(EMP_CODE);
                                eryuanUser.setSECRET_LEVEL(SECRET_LEVEL);
                                eryuanUser.setBIRTH_DATE(userbirthDateed);
                                eryuanUser.setGENDER(GENDER);
                                eryuanUser.setO_TEL(O_TEL);
                                eryuanUser.setO_EMAIL(O_EMAIL);
                                eryuanUser.setWORK_POST(WORK_POST);
                                eryuanUser.setTEC_POST(TEC_POST);
                                eryuanUser.setORDER_ID(ORDER_ID);
                                eryuanUser.setSYNC_TYPE(SYNC_TYPE);
                                eryuanUser.setDISP_TIME(userdispTimed);
                                eryuanUser.setSTATUS(STATUS);
                                eryuanUser.setREMARK(REMARK);
                                eryuanUser.setREFA(REFA);
                                eryuanUser.setREFB(REFB);
                                eryuanUser.setORG_NAME(ORG_NAME);
                                int countuser = queryEryuanUserByNoCount(P_ID);
                                if (countuser > 0) {
                                    int updatesum = updateEryuanUserInfo(eryuanUser);
                                    System.out.println("用户更新----->" + P_ID + ":" + OPERATOR_NAME);
                                } else {
                                    String ID = UUID.randomUUID().toString().replaceAll("-", "");
                                    eryuanUser.setID(ID);
                                    eryuanUserDao.saveEryuanUser(eryuanUser);
                                    System.out.println("++++++++新增用户----->" + P_ID + ":" + OPERATOR_NAME);
                                }
                              /*  @Autowired
                                private UserMapper userMapper;*/
                                //        UserMapper userMapper =
                                //     UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                                //   userMapper.saveuserInfo(userInfo);
                                //System.out.println("sdfdfd"+saveinfo);
                                //   ControllerService ss=new ControllerService();
                                // ss.saveuserInfo();
                                //    String user_id = SERVER_ID + "." + PinYinUtil.getPinYin(user_name).replaceAll(" ", "") + "_" + idcard_no.substring(idcard_no.length() - 4);//避免姓名同音所有加身份证号后4位
                                //    String org_id = user_map.get("casicorgcode").toString().trim();
                                //     String user_level = user_map.get("secretlevel").toString().trim();
                                //   String user_order = user_map.get("orderid").toString().trim();
                            }

                        }
                    }
                }
            }

        }
    }

    @Override
    public int queryEryuanUserByNoCount(String idcard_no) {

        int sumuser = eryuanUserDao.queryEryuanUserByNoCount(idcard_no);
        return sumuser;
    }

    @Override
    public int updateEryuanUserInfo(EryuanUser eryuanUser) {
        int updateuser = eryuanUserDao.updateEryuanUserInfo(eryuanUser);
        return updateuser;
    }

    private static Map<String, Object> resolveXML(String xml) throws UnsupportedEncodingException, DocumentException, DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        document = saxReader.read(new ByteArrayInputStream(xml.getBytes("GB2312")));
        Map<String, Object> map = Dom2Map(document);
        return map;
    }

    private static Map<String, Object> Dom2Map(Document doc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doc == null)
            return map;
        Element root = (Element) doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            //    System.out.println("e.getName=" + list);
            if (list.size() > 0) {
                map.put(e.getName(), Dom2Map1(e));
            } else
                map.put(e.getName(), e.getText());
        }
        return map;
    }


    /**
     * 将Element对象转为Map（String→Document→Element→Map）
     *
     * @param
     * @return
     */

    private static Map Dom2Map1(Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map m = Dom2Map1(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        String Objname = obj.getClass().getName();
                        //         System.out.println("Obj" + obj.getClass().getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());//公共map resultCode=0
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    private static String[] getAllXmlFileList(String filereadxmlpath) {

        File readfilelist = new File(filereadxmlpath).getAbsoluteFile();
        String[] dir = readfilelist.list();
        return dir;
    }
}

