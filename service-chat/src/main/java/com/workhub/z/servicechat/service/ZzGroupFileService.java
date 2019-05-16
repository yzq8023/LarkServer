package com.workhub.z.servicechat.service;

import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupInfo;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import java.util.List;

/**
 * 群文件(ZzGroupFile)表服务接口
 *
 * @author 忠
 * @since 2019-05-13 10:59:08
 */
public interface ZzGroupFileService {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    ZzGroupFile queryById(String fileId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzGroupFile> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    Integer insert(ZzGroupFile zzGroupFile);

    /**
     * 修改数据
     *
     * @param zzGroupFile 实例对象
     * @return 实例对象
     */
    Integer update(ZzGroupFile zzGroupFile);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    boolean deleteById(String fileId);

    /**
     * 查询群组的文件信息
     * @param id
     * @return
     * @throws Exception
     */
    PageInfo<GroupInfo> groupFileList(String id,int page,int size) throws Exception;

    /**
     * 查询群组的文件信息记录数
     * @param id
     * @return
     * @throws Exception
     */
    Long groupFileListTotal(String id) throws Exception;
}