package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.dao.ZzGroupFileDao;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import org.springframework.stereotype.Service;

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
    public ZzGroupFile insert(ZzGroupFile zzGroupFile) {
        this.zzGroupFileDao.insert(zzGroupFile);
        return zzGroupFile;
    }

    /**
     * 修改数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    @Override
    public ZzGroupFile update(ZzGroupFile zzGroupFile) {
        this.zzGroupFileDao.update(zzGroupFile);
        return this.queryById(zzGroupFile.getFileId());
    }

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String fileId) {
        return this.zzGroupFileDao.deleteById(fileId) > 0;
    }

    @Override
    public List<String> groupFileList() throws Exception {
        return null;
    }
}