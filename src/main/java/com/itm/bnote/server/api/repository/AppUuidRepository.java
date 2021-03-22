package com.itm.bnote.server.api.repository;

import com.itm.bnote.server.api.model.AppUuidModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUuidRepository extends JpaRepository<AppUuidModel, Integer> {
    AppUuidModel findByDeviceUuid(String deviceUuid);
}
