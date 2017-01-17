package com.ade.fun.model.persistent;

import com.ade.fun.model.persistent.auto._Character;
import org.apache.cayenne.Cayenne;

public class Character extends _Character {

   public int getId() {
       return Cayenne.intPKForObject(this);
   }

}
