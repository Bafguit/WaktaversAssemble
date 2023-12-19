package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;

public class Cat extends AbstractSynergy {

    private static Cat instance;

    private Cat() {
        super("Cat");
    }

    @Override
    public void reset() {
        memberCount = getActualMemberCount();
        grade = getGrade();
    }

    public static Cat getInstance() {
        if(instance == null) {
            instance = new Cat();
        }
        return instance;
    }
}