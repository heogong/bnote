package com.itm.bnote.server.api.repository;

import com.itm.bnote.server.api.model.AppVersionNoticeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppVersionNoticeRepository extends JpaRepository<AppVersionNoticeModel, Integer> {
}
