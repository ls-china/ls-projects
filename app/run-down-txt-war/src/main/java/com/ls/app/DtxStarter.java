package com.ls.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by hx on 16-7-21.
 */
public class DtxStarter {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("java -jar dts.jar war path port");
            return;
        }
        Server server = new Server(Integer.parseInt(args[2]));
        WebAppContext context = new WebAppContext();
        context.setWar(args[0]);
        context.setContextPath(args[1]);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
