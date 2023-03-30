package com.fastcat.assemble.handlers;

import com.fastcat.assemble.strings.SkillString;
import com.fastcat.assemble.strings.StatusString;

public class StringHandler {

    public static SkillString skillString;
    public static StatusString statusString;

    public static void initialize() {
        skillString = new SkillString();
        statusString = new StatusString();
    }
}
