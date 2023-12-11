package com.fastcat.assemble.interfaces;

import com.fastcat.assemble.abstracts.AbstractStatus;

public interface OnStatusUpdated {
    public void onStatusInitial(AbstractStatus status);
    public void onStatusRemoved(AbstractStatus status);
}
