package bytes;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/19.
 */
public class DataInputStreamTest {

    @Test
    public void write() throws IOException {
        Member member = new Member();
        member.setAge(28);
        member.setName("Edgar");
        DataOutputStream out = new DataOutputStream(new FileOutputStream("data.log"));
        out.writeInt(member.getAge());
        out.writeUTF(member.getName());
        out.flush();
        out.close();
    }

    @Test
    public void read() throws IOException {
        Member member = new Member();
        DataInputStream in = new DataInputStream(new FileInputStream("data.log"));
        int age = in.readInt();
        String name = in.readUTF();
        in.close();
        System.out.println(name);
        System.out.println(age);
    }
}
