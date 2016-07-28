package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.Pending;
import java.util.List;

/**
 * Created by ${Sona} on 7/21/2016.
 */
public interface PendingsManager {
    int add(Pending pending);

    Pending getPendingByID(int id);

    List<Pending> viewAllPendingByBook(int id);

    List<Pending> viewAllPendingByUser(int id);

    boolean delete(int id);
}
