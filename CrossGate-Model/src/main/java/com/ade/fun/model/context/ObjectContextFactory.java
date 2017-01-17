package com.ade.fun.model.context;

import org.apache.cayenne.DataChannel;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class ObjectContextFactory {

    private static ObjectContextFactory instance = new ObjectContextFactory();

    private ServerRuntime cayenneRuntime;

    private ObjectContextFactory() {
        this.cayenneRuntime = new ServerRuntime("cayenne-CrossGate.xml");
    }

    public static ObjectContextFactory getInstance() {
        return instance;
    }

    public ObjectContext getObjectContext() {
        return this.cayenneRuntime.getContext();
    }

    public ObjectContext getChildObjectContext(ObjectContext context) {
        return this.cayenneRuntime.getContext((DataChannel) context);
    }

}
