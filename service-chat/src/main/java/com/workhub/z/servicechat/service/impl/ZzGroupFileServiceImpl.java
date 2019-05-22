package com.workhub.z.servicechat.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupInfoVO;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.dao.ZzGroupFileDao;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 群文件(ZzGroupFile)表服务实现类
 *
 * @author 忠
 * @since 2019-05-13 10:59:08
 */
@Service("zzGroupFileService")
public class ZzGroupFileServiceImpl implements ZzGroupFileService {
    @Resource
    private ZzGroupFileDao zzGroupFileDao;

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    @Override
    public ZzGroupFile queryById(String fileId) {
        return this.zzGroupFileDao.queryById(fileId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzGroupFile> queryAllByLimit(int offset, int limit) {
        return this.zzGroupFileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer insert(ZzGroupFile zzGroupFile) {
        int insert = this.zzGroupFileDao.insert(zzGroupFile);
        return insert;
    }

    /**
     * 修改数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer update(ZzGroupFile zzGroupFile) {
        int update = this.zzGroupFileDao.update(zzGroupFile);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(String fileId) {
        return this.zzGroupFileDao.deleteById(fileId) > 0;
    }

    /**
     * 查询群内文件信息
     * @param id
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<GroupInfoVO> groupFileList(String id, int page, int size) throws Exception {
        if (StringUtil.isEmpty(id)) throw new NullPointerException("id is null");
        Page<Object> pageMassage = PageHelper.startPage(page, size);
        pageMassage.setTotal(this.zzGroupFileDao.groupFileListTotal(id));
        int startRow = pageMassage.getStartRow();
        int endRow = pageMassage.getEndRow();
        PageInfo<GroupInfoVO> pageInfoGroupInfo = new PageInfo<GroupInfoVO>();
        System.out.println(this.zzGroupFileDao.groupFileList(id,startRow,endRow));
        pageInfoGroupInfo.setList(this.zzGroupFileDao.groupFileList(id,startRow,endRow));
        pageInfoGroupInfo.setTotal(pageMassage.getTotal());
        pageInfoGroupInfo.setStartRow(startRow);
        pageInfoGroupInfo.setEndRow(endRow);
        pageInfoGroupInfo.setPages(pageMassage.getPages());
        pageInfoGroupInfo.setPageNum(page);
        pageInfoGroupInfo.setPageSize(size);
        return pageInfoGroupInfo;
    }

    @Override
    public Long groupFileListTotal(String id) throws Exception {
        return this.zzGroupFileDao.groupFileListTotal(id);
    }
}