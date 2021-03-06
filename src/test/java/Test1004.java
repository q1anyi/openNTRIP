import org.dav95s.openNTRIP.Tools.Decoders.RTCM_3X;
import org.dav95s.openNTRIP.Tools.MessagePack;
import org.dav95s.openNTRIP.Tools.RTCM.MSG1006;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;

public class Test1004 {
    String path = "src/test/resources/RTCM_32";

    @Test
    public void start() {

        File file = new File(path);

        try {
            InputStream input = new FileInputStream(file);
            ByteBuffer byteBuffer = ByteBuffer.wrap(input.readAllBytes());
            input.close();
            RTCM_3X decoder = new RTCM_3X();

            MessagePack messages = decoder.separate(byteBuffer);
            byte[] bytes = messages.getMessageByNmb(1006).getBytes();


            MSG1006 msg = new MSG1006(bytes);
            System.out.println(msg.toString());
            String raw = "";
            for (byte i : bytes) {
                raw += msg.toBinaryString(i) + " ";
            }
            System.out.println(raw);

            byte[] bytes2 = msg.write();
            String raw2 = "";
            for (byte i : bytes2) {
                raw2 += msg.toBinaryString(i) + " ";
            }
            System.out.println(raw2);


            MSG1006 msgnew = new MSG1006(bytes2);
            System.out.println(msgnew);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void benchmark() {
        File file = new File(path);

        try {
            InputStream input = new FileInputStream(file);
            ByteBuffer byteBuffer = ByteBuffer.wrap(input.readAllBytes());
            input.close();
            RTCM_3X decoder = new RTCM_3X();

            MessagePack messages = decoder.separate(byteBuffer);
            byte[] bytes = messages.getMessageByNmb(1005).getBytes();

            int counter = 0;
            while (counter < 100) {
                long fmark = System.currentTimeMillis();
                for (int i = 0; i < 10000; i++) {
                    MSG1006 msg = new MSG1006(bytes);
                }
                System.out.println(System.currentTimeMillis() - fmark);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
