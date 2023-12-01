package com.fastcat.assemble.abstracts;

public abstract class AbstractStatus {

    public String id;
    public int amount;
    public boolean hasAmount, canGoNegative;

    public AbstractStatus() {

    }

    public void apply(int amount) {}

    public void onRemove() {}

    public void onInitial() {}
}
