package com.itm.bnote.server.api.repository;

import com.itm.bnote.server.api.model.ExcutivesVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcutivesVersionRepository extends JpaRepository<ExcutivesVersionModel, Integer> {
    @Query(value = "SELECT TOP(1) * FROM TB_EXCUTIVES_VERSION WHERE appVersion = ?1 AND deviceOsType = ?2 ORDER BY appVersion DESC", nativeQuery=true)
    ExcutivesVersionModel findByExcutivesVersion(double appVersion, String deviceOsType);

    List<ExcutivesVersionModel> findByOrderBySeqDesc();

    List<ExcutivesVersionModel> findByCompOrderBySeqDesc(String company);

    List<ExcutivesVersionModel> findByCompAndExcutivesMinorVersionGreaterThanOrderBySeqDesc(String comp, double excutivesVersion);

}

