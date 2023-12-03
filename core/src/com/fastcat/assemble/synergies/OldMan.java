package com.fastcat.assemble.synergies;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.utils.DamageInfo;

public class OldMan extends AbstractSynergy {

    private static OldMan instance;

    private OldMan() {
        super("OldMan");
    }

    public float damageMultiply(DamageInfo info) {
        if(!info.source.isPlayer)
            return 1 - ((float)((10 + 10 * grade) * 0.01f));
        return 1f;
    }

    public static OldMan getInstance() {
        if(instance == null) {
            instance = new OldMan();
        }
        return instance;
    }
}