import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;

/**
 * Created by Administrator on 2015/5/27.
 */
public class MockFtp {

    private FakeFtpServer fakeFtpServer;

    private static final String HOME_DIR = "/";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pwd";

    public static void main(String[] args) {
        FakeFtpServer fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "pwd", "c:\\data"));

        FileSystem fileSystem = new WindowsFakeFileSystem();
        fileSystem.add(new DirectoryEntry("c:\\data"));
        fileSystem.add(new FileEntry("c:\\data\\file1.txt", "abcdef 1234567890"));
        fileSystem.add(new FileEntry("c:\\data\\run.exe"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(21);
        fakeFtpServer.start();
    }

//    @Before
//    public void setUp() {
//        fakeFtpServer = new FakeFtpServer();
//        fakeFtpServer.setServerControlPort(0);  // use any free port
//
//        FileSystem fileSystem = new WindowsFakeFileSystem();
//        fileSystem.add(new DirectoryEntry("f:/vfs"));
//        fakeFtpServer.setFileSystem(fileSystem);
//
//        UserAccount userAccount = new UserAccount(USERNAME, PASSWORD, HOME_DIR);
//        fakeFtpServer.addUserAccount(userAccount);
//
//        fakeFtpServer.start();
//        int port = fakeFtpServer.getServerControlPort();
//    }
//
//    @After
//    public void tearDown() {
//        fakeFtpServer.stop();
//    }
//
//    @Test
//    public void test() {
//
//    }
}
