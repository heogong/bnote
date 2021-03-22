package com.itm.bnote.server.organization.repository;

import com.itm.bnote.server.organization.model.OrgEmpInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgEmpInfoRepository extends JpaRepository<OrgEmpInfoModel, Integer> {

    List<OrgEmpInfoModel> findByEmpIdNotNull();
}