package com.example.abbuilddream.utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<Integer> totalCount = new MutableLiveData<>(0);

    public LiveData<Integer> getTotalCount() {
        return totalCount;
    }

    public void updateTotalCount(int change) {
        Integer currentCount = totalCount.getValue();
        if (currentCount != null) {
            totalCount.setValue(currentCount + change);
        }
    }
}
