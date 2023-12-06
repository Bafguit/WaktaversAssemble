package com.fastcat.assemble.handlers;

import com.fastcat.assemble.screens.battle.BattleScreen;
import com.fastcat.assemble.screens.mainmenu.MainMenuScreen;

public class ScreenHandler {
    private static ScreenHandler instance;

    public BattleScreen battleScreen;
    public MainMenuScreen mainMenuScreen;

    public static ScreenHandler getInstance() {
        if(instance == null) instance = new ScreenHandler();
        return instance;
    }
}
