package com.fastcat.assemble.interfaces;

import com.fastcat.assemble.abstracts.AbstractStatus;

public interface OnStatusUpdated {
    public void onStatusApplied(AbstractStatus status);
    public void onStatusRemoved(AbstractStatus status);
}
