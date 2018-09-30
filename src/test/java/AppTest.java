import com.stephen.learning.zip.FileCompressUtil;
import com.stephen.learning.zip.MyListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Auther: jack
 * @Date: 2018/8/25 12:09
 * @Description:
 */
@Slf4j
public class AppTest {
    @Test
    public void zip() throws Exception{
        FileCompressUtil.encryptZip("/tmp/file/71c1dd50-c1fc-11e8-9304-00163e00ce61", "/tmp/file", "123456");
    }

    @Test
    public void unzip() throws Exception{
        FileCompressUtil.encryptUnZip("/tmp/file/6925cd70-c1fa-11e8-9304-00163e00ce61.zip", "/tmp/file", "123456");
    }

    @Test
    public void unzipWithProgress() throws Exception{
        FileCompressUtil.encryptUnZipWithProgress("/tmp/file/6925cd70-c1fa-11e8-9304-00163e00ce61.zip",
                "/tmp/file", "123456", new MyListener());
    }

    @Test
    public void zipWithProgress()throws Exception{
        FileCompressUtil.encryptZipWithProgress("/tmp/file/6925cd70-c1fa-11e8-9304-00163e00ce61","/tmp/file","123456",new MyListener());
    }
}
