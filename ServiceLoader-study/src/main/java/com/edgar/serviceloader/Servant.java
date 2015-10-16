package com.edgar.serviceloader;

import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;

/**
 * Created by Administrator on 2015/10/16.
 */
public class Servant {

    public static void main(String[] args) throws IOException {
        ServiceLoader<IPersonalServant> servantServiceLoader = ServiceLoader.load(IPersonalServant.class);

        IPersonalServant i = null;
        for (IPersonalServant ii : servantServiceLoader) {
            System.out.println(ii.getClass());
            if (ii.can("fetch tea")) {
                i = ii;
            }
        }
        if (i == null)
            throw new IllegalArgumentException("No suitable servant found");

        i.process(new File("test.log"));
    }
}
