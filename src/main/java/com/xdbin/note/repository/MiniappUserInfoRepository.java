package com.xdbin.note.repository;

import com.xdbin.note.entity.MiniappUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:47 PM
 */
public interface MiniappUserInfoRepository extends JpaRepository<MiniappUserInfo, Long>, JpaSpecificationExecutor<MiniappUserInfo> {

    MiniappUserInfo findByOpenIdAndValid(String openId, Integer valid);

    MiniappUserInfo findByUserIdAndValid(String userId, Integer valid);
}
