package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Pending;
import java.util.List;

public interface PendingDAO {
    int add(Pending pending);

    Pending getPendingByID(int id);

    List<Pending> getAllPendingsByBookID(int id);

    List<Pending> getAllPendingsByUserID(int id);

    //void updatePending(Pending pending);

    void deletePending(int id);

    void deleteAllPendings();
}
