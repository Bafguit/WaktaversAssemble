package com.fastcat.assemble.handlers;

import com.fastcat.assemble.abstracts.AbstractEntity;

import java.util.HashMap;

public class DataHandler {

    public static final HashMap<String, AbstractEntity.EntityData> entityData = new HashMap<>();
    public static final HashMap<String, AbstractRelic.RelicData> relicData = new HashMap<>();
    public static final HashMap<String, AbstractChar.CharData> charData = new HashMap<>();
    public static final HashMap<String, AbstractSynergy.SynergyData> synergyData = new HashMap<>();
    public static final HashMap<String, AbstractStatus.StatusData> statusData = new HashMap<>();
    public static final HashMap<String, AbstractSkill.SkillData> skillData = new HashMap<>();

}
