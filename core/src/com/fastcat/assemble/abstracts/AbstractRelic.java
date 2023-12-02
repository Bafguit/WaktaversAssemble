package com.fastcat.assemble.abstracts;

public abstract class AbstractRelic {

    public final RelicData data;
    public String id, name, desc, flavor;
    public int counter = -1;
    public Sprite img;
    public boolean isDone = false;
    public RelicRarity rarity;

    public AbstractRelic(String id) {
        this.id = id;
        data = DataHandler.relicData.get(id).cpy();
        name = data.name;
        desc = data.desc;
        flavor = data.flavor;
        img = new Sprite(data.img);
        rarity = data.rarity;
    }

    public int damageTake(DamageInfo info, boolean isPlayer) {
        return info.damage;
    }

    public float damageTakeMultiply(DamageInfo info, boolean isPlayer) {
        return 1f;
    }

    public void damageTaken(DamageInfo info, boolean isPlayer) {}
    
    public void onGain() {}

    public void onLose() {}

    public void endOfTurn(boolean isPlayer) {}

    public void endOfRound() {}

    public void startOfTurn(boolean isPlayer) {}

    public static class RelicData {
        public final String id, name, desc, flavor;
        public final Texture img;
        public final RelicRarity rarity;

        public RelicData(String id, JsonValue json) {
            this.id = id;
            name = json.getString("name");
            desc = json.getString("desc");
            flavor = json.getString("flavor");
            img = FileHandler.getTexture("relic/" + id);
            rarity = RelicRarity.valueOf(json.getString("rarity"));
        }
    }

    public enum RelicRarity {
        common, rare, legeno
    }
}
