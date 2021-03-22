package com.itm.bnote.server.api.repository;

import com.itm.bnote.server.api.model.AppLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppLogRepository extends JpaRepository<AppLogModel, Integer> {

}
