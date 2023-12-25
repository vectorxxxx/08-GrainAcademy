package xyz.funnyboy.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * OSS测试类
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 20:30:56
 */
public class OSSTest
{
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-shanghai.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static final String ACCESS_KEY_ID = "LTAI5tLJnjE7YF12eK3oSAz3";
    private static final String ACCESS_KEY_SECRET = "Ci1D7zZ8nCdvP5ZATe2eHwL8xeHxTH";
    private static final String BUCKET_NAME = "guli-edu-vectorx-test";

    private OSS ossClient;

    @Before
    public void testBuild() {
        ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    @After
    public void testShutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    @Test
    public void testCreateBucket() {
        ossClient.createBucket(BUCKET_NAME);
    }

    @Test
    public void testExist() {
        boolean exists = ossClient.doesBucketExist(BUCKET_NAME);
        System.out.println(exists);
    }

    @Test
    public void testAccessControl() {
        ossClient.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
    }

    @Test
    public void testDelete() {
        ossClient.deleteBucket(BUCKET_NAME);
    }
}

