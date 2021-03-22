package com.itm.bnote.server.api.repository;

import com.itm.bnote.server.api.model.AppVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppVersionRepository extends JpaRepository<AppVersionModel, Integer> {

    List<AppVersionModel> findByDeviceOsTypeOrderByAppSeqDesc(String deviceOsType);

    List<AppVersionModel> findByDeviceOsTypeAndAppVersionGreaterThanOrderByAppSeqDesc(String deviceOsType, Double appVersion);
}
