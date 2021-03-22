package com.itm.bnote.server.coPhone.repository;

import com.itm.bnote.server.coPhone.model.CPAppVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CPAppVersionRepository extends JpaRepository<CPAppVersionModel, Integer> {

    List<CPAppVersionModel> findByDeviceOsTypeAndAppVersionGreaterThanOrderByIdxDesc(String deviceOsType, Double appVersion);
}
