package com.itm.bnote.server.coPhone.repository;

import com.itm.bnote.server.coPhone.model.CPExcutivesVersionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CPExcutivesVersionRepository extends JpaRepository<CPExcutivesVersionModel, Integer> {
    @Query(value = "SELECT TOP(1) * FROM tb_excutivesVersion WHERE appVersion = ?1 AND deviceOsType = ?2 ORDER BY appVersion DESC", nativeQuery=true)
    CPExcutivesVersionModel findByExcutivesVersion(double appVersion, String deviceOsType);

    @Query(value = "SELECT TOP(1) * FROM tb_excutivesVersion  ORDER BY seq DESC", nativeQuery=true)
    CPExcutivesVersionModel findByExcutivesMaxVersionModel();
}

