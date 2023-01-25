package com.fastcat.assemble.abstrcts;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.utils.FastCatUtils;

public class AbstractBattle {

    public Array<AbstractEntity> chars;
    public Array<AbstractDice> dices;
    public Array<AbstractCard> deck;

    public Queue<AbstractCard> drawPile;
    public Array<AbstractCard> discardPile;
    public Array<AbstractCard> exhaustPile;
    public Array<AbstractCard> hand;

    public void prepareDeck() {
        deck = new Array<>();
        dices = new Array<>();
        chars = new Array<>();
        drawPile = new Queue<>();
        exhaustPile = new Array<>();
        discardPile = new Array<>();
        hand = new Array<>();
        for(AbstractCard c : WaktaAssemble.game.deck) {
            deck.add(c.clone());
        }
        FastCatUtils.staticShuffle(deck, WaktaAssemble.game.publicRandom, AbstractCard.class);
        for(AbstractCard c : deck) {
            drawPile.addLast(c);
        }
    }

    public void turnDraw(int amount) {
        if(drawPile.size >= amount) {
            for(int i = 0; i < amount; i++) {
                hand.add(drawPile.removeFirst());
            }
        } else {
            if(drawPile.size > 0) {
                int s = drawPile.size;
                for(int i = 0; i < s; i++) {
                    hand.add(drawPile.removeFirst());
                }
            } else {
                FastCatUtils.staticShuffle(discardPile, WaktaAssemble.game.battleRandom, AbstractCard.class);
                for(AbstractCard c : discardPile) {
                    drawPile.addLast(c);
                }
                discardPile.clear();
            }
        }
    }
}
