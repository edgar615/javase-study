package bytes;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2014/11/19.
 */
public class ObjectStreamTest {

    @Test
    public void testWrite() throws IOException {
        Member member = new Member();
        member.setAge(28);
        member.setName("Edgar");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("object.log"));
        out.writeObject(member);
        out.flush();
        out.close();
    }

    @Test
    public void testRead() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("object.log"));
        Member member = (Member) in.readObject();
        in.close();
        System.out.println(member.getName());
        System.out.println(member.getAge());
    }
}
