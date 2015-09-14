package org.aliece.docker.Utils;

import java.io.IOException;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public interface CodecFactory {
    byte[] serialize(Object obj) throws IOException;

    Object deSerialize(byte[] in) throws IOException;
}
