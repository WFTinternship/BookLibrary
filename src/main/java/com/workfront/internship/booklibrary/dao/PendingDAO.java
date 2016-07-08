package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.common.Pending;
import java.util.List;

public interface PendingDAO {
    void createPending(Pending pending);

    Pending getPendingByID(int id);

    List<Pending> getAllPendingsByBookID(int id);

    //void updatePending(Pending pending);

    void deletePending(int id);
}
