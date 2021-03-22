package com.itm.bnote.server.selfProgress.repository;

import com.itm.bnote.server.selfProgress.model.CodeInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeInfoRepository extends JpaRepository<CodeInfoModel, Integer> {
    @Query(value = "SELECT CODE_NM FROM TB_CODE_INFO WHERE CODE_GRP = ?1  GROUP BY CODE_NM", nativeQuery=true)
    List<?> findByPatternNameInfo(String codeGrp);

    @Query(value = "SELECT CODE_VALUE FROM TB_CODE_INFO WHERE CODE_GRP = ?1 AND CODE_NM = ?2", nativeQuery=true)
    List<?> findByPatternValueInfo(String codeGrp, String codeName);

    List<CodeInfoModel> findByCodeGrp(String codeGrp);

}