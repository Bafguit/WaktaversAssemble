package com.fastcat.assemble.handlers;

import com.fastcat.assemble.abstracts.AbstractSynergy;
import com.fastcat.assemble.synergies.Badass;
import com.fastcat.assemble.synergies.Cat;
import com.fastcat.assemble.synergies.Competitor;
import com.fastcat.assemble.synergies.Crazy;
import com.fastcat.assemble.synergies.Cutey;
import com.fastcat.assemble.synergies.Doormat;
import com.fastcat.assemble.synergies.Expert;
import com.fastcat.assemble.synergies.Guardian;
import com.fastcat.assemble.synergies.Isedol;
import com.fastcat.assemble.synergies.Kiddo;
import com.fastcat.assemble.synergies.Machinary;
import com.fastcat.assemble.synergies.Magician;
import com.fastcat.assemble.synergies.MainVocal;
import com.fastcat.assemble.synergies.MindMaster;
import com.fastcat.assemble.synergies.Nobless;
import com.fastcat.assemble.synergies.Nunna;
import com.fastcat.assemble.synergies.OldMan;
import com.fastcat.assemble.synergies.Timid;
import com.fastcat.assemble.synergies.Villain;

public final class SynergyHandler {
    
    public static AbstractSynergy getSynergyInstance(String id) {
        switch (id) {
            case "Badass":
                return Badass.getInstance();
            case "Cat":
                return Cat.getInstance();
            case "Competitor":
                return Competitor.getInstance();
            case "Crazy":
                return Crazy.getInstance();
            case "Cutey":
                return Cutey.getInstance();
            case "Doormat":
                return Doormat.getInstance();
            case "Expert":
                return Expert.getInstance();
            case "Guardian":
                return Guardian.getInstance();
            case "Isedol":
                return Isedol.getInstance();
            case "Machinary":
                return Machinary.getInstance();
            case "Magician":
                return Magician.getInstance();
            case "MainVocal":
                return MainVocal.getInstance();
            case "MindMaster":
                return MindMaster.getInstance();
            case "Nobless":
                return Nobless.getInstance();
            case "Nunna":
                return Nunna.getInstance();
            case "OldMan":
                return OldMan.getInstance();
            case "Timid":
                return Timid.getInstance();
            case "Villain":
                return Villain.getInstance();
            default:
                return Kiddo.getInstance();
        }
    }
}
