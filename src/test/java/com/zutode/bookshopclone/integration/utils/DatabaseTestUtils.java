package com.zutode.bookshopclone.integration.utils;

import com.zutode.bookshopclone.auth.domain.util.TestUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseTestUtils {

    private final EntityManager entityManager;
    private final TestUserUtils testUserUtils;

    @Transactional
    public void resetDatabase() {
        log.info("Reset database");
        disableDatabaseConstraints();
        List<String> tables = getDatabaseTablesNames();
        truncateTables(tables);
        enableDatabaseConstraints();
    }


    private void disableDatabaseConstraints() {
        constraintsEnabled(false);
    }

    private void enableDatabaseConstraints() {
        constraintsEnabled(true);
    }

    private void constraintsEnabled(boolean enable) {
        entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS=%s", enable ? 1 : 0)).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    private List<String> getDatabaseTablesNames() {
        return entityManager.createNativeQuery("show tables")
                .getResultList();
    }

    private void truncateTables(List<String> tables) {
        tables.forEach(table -> entityManager.createNativeQuery(
                String.format("truncate table %s", table)).executeUpdate());
    }

    @Transactional
    public void addTestUsers() {
        testUserUtils.addTestUsers();
    }



}
