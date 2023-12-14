package com.fastcat.assemble.utils;

import com.fastcat.assemble.abstracts.AbstractUI.TempUI;
import com.fastcat.assemble.handlers.FileHandler;

public class TopBar {
    private TempUI bg;
    
    public TopBar() {
        bg = new TempUI(FileHandler.getTexture("ui/topBar"));
    }
}
