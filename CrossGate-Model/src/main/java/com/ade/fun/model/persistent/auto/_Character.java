package com.ade.fun.model.persistent.auto;

import org.apache.cayenne.CayenneDataObject;

import com.ade.fun.model.enumeration.Position;
import com.ade.fun.model.enumeration.Rank;
import com.ade.fun.model.persistent.Account;
import com.ade.fun.model.persistent.Occupation;

/**
 * Class _Character was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Character extends CayenneDataObject {

    public static final String CHARACTER_LEVEL_PROPERTY = "characterLevel";
    public static final String CHARACTER_POSITION_PROPERTY = "characterPosition";
    public static final String CHARACTER_RANK_PROPERTY = "characterRank";
    public static final String ACCOUNT_PROPERTY = "account";
    public static final String OCCUPATION_PROPERTY = "occupation";

    public static final String CHARACTER_ID_PK_COLUMN = "CHARACTER_ID";

    public void setCharacterLevel(Integer characterLevel) {
        writeProperty(CHARACTER_LEVEL_PROPERTY, characterLevel);
    }
    public Integer getCharacterLevel() {
        return (Integer)readProperty(CHARACTER_LEVEL_PROPERTY);
    }

    public void setCharacterPosition(Position characterPosition) {
        writeProperty(CHARACTER_POSITION_PROPERTY, characterPosition);
    }
    public Position getCharacterPosition() {
        return (Position)readProperty(CHARACTER_POSITION_PROPERTY);
    }

    public void setCharacterRank(Rank characterRank) {
        writeProperty(CHARACTER_RANK_PROPERTY, characterRank);
    }
    public Rank getCharacterRank() {
        return (Rank)readProperty(CHARACTER_RANK_PROPERTY);
    }

    public void setAccount(Account account) {
        setToOneTarget(ACCOUNT_PROPERTY, account, true);
    }

    public Account getAccount() {
        return (Account)readProperty(ACCOUNT_PROPERTY);
    }


    public void setOccupation(Occupation occupation) {
        setToOneTarget(OCCUPATION_PROPERTY, occupation, true);
    }

    public Occupation getOccupation() {
        return (Occupation)readProperty(OCCUPATION_PROPERTY);
    }


}
