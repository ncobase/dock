package com.ncobase.framework.core.service;

import com.ncobase.framework.core.domain.dto.OssDTO;

import java.util.List;

/**
 * 通用 OSS 服务
 *
 * @author Lion Li
 */
public interface OssService {

    /**
     * 通过 ossId 查询对应的 url
     *
     * @param ossIds ossId 串逗号分隔
     * @return url 串逗号分隔
     */
    String selectUrlByIds(String ossIds);

    /**
     * 通过 ossId 查询列表
     *
     * @param ossIds ossId 串逗号分隔
     * @return 列表
     */
    List<OssDTO> selectByIds(String ossIds);
}
