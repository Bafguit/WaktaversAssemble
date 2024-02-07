package com.fastcat.assemble.utils;

import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WakTower;
import com.fastcat.assemble.abstracts.AbstractEntity;
import com.fastcat.assemble.abstracts.AbstractSynergy;

public enum TargetType {
    RANDOM {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            Array<AbstractEntity> entities = new Array<>();
            if(source != null && !source.isPlayer) {
                for(AbstractEntity e : WakTower.game.battle.members) {
                    if(e.isAlive()) entities.add(e);
                }
            } else {
                for(AbstractEntity e : WakTower.game.battle.enemies) {
                    if(e.isAlive()) entities.add(e);
                }
            }
            if(entities.size > 0) {
                int r = WakTower.game.battleRandom.random(entities.size - 1);
                targets.add(entities.get(r));
            }
            return targets;
        }
    }, 
    RANDOM_MEMBER {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            Array<AbstractEntity> entities = new Array<>();
            for(AbstractEntity e : WakTower.game.battle.members) {
                if(e.isAlive()) entities.add(e);
            }
            if(entities.size > 0) {
                int r = WakTower.game.battleRandom.random(entities.size - 1);
                targets.add(entities.get(r));
            }
            return targets;
        }
    }, 
    RANDOM_ENEMY {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            Array<AbstractEntity> entities = new Array<>();
            for(AbstractEntity e : WakTower.game.battle.enemies) {
                if(e.isAlive()) entities.add(e);
            }
            if(entities.size > 0) {
                int r = WakTower.game.battleRandom.random(entities.size - 1);
                targets.add(entities.get(r));
            }
            return targets;
        }
    }, 
    ALL {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            for(AbstractEntity e : WakTower.game.battle.members) {
                if(e.isAlive()) targets.add(e);
            }
            for(AbstractEntity e : WakTower.game.battle.enemies) {
                if(e.isAlive()) targets.add(e);
            }
            return targets;
        }
    },
    ALL_MEMBER {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            for(AbstractEntity e : WakTower.game.battle.members) {
                if(e.isAlive()) targets.add(e);
            }
            return targets;
        }
    },
    ALL_ENEMY {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            for(AbstractEntity e : WakTower.game.battle.enemies) {
                if(e.isAlive()) targets.add(e);
            }
            return targets;
        }
    },
    HP_HIGH {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            int h = 0;
            for(AbstractEntity e : ((source != null && !source.isPlayer) ? WakTower.game.battle.members : WakTower.game.battle.enemies)) {
                if(e.isAlive()) {
                    if(e.health > h) {
                        targets.clear();
                        targets.add(e);
                        h = e.health;
                    } else if(e.health == h) {
                        targets.add(e);
                    }
                }
            }
            return targets;
        }
    },
    HP_LOW {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            int h = 0;
            for(AbstractEntity e : ((source != null && !source.isPlayer) ? WakTower.game.battle.members : WakTower.game.battle.enemies)) {
                if(e.isAlive() && e.health > h) {
                    h = e.health;
                }
            }

            for(AbstractEntity e : ((source != null && !source.isPlayer) ? WakTower.game.battle.members : WakTower.game.battle.enemies)) {
                if(e.isAlive()) {
                    if(e.health < h) {
                        targets.clear();
                        targets.add(e);
                        h = e.health;
                    } else if(e.health == h) {
                        targets.add(e);
                    }
                }
            }
            return targets;
        }
    },
    SELF {
        @Override
        public Array<AbstractEntity> getTargets(AbstractEntity source) {
            Array<AbstractEntity> targets = new Array<>();
            if(source != null) targets.add(source);
            return targets;
        }
    },
    NONE;

    public Array<AbstractEntity> getTargets(AbstractEntity source) {
        return new Array<>();
    }
}
