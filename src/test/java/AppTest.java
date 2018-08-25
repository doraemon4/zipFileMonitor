import com.stephen.learning.zip.FileCompressUtil;
import com.stephen.learning.zip.MyListener;

/**
 * @Auther: jack
 * @Date: 2018/8/25 12:09
 * @Description:
 */
public class AppTest {
    public static void main(String[] args) {
        try {
            //FileCompressUtil.encryptZip("/tmp/file/73518190-a76f-11e8-80ce-005056a948dd", "/tmp/file", "123456");
            FileCompressUtil.encryptUnZipWithProgress("/tmp/file/73518190-a76f-11e8-80ce-005056a948dd.zip",
                    "/tmp/file", "123456", new MyListener(), false);
        } catch (Exception e) {

        }
    }
}
