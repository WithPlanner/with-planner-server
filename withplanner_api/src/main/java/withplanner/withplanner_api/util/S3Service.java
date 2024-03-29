package withplanner.withplanner_api.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 s3Client;

    /**
     * aws s3 로 파일 업로드
     *
     * @param file
     * @return
     */
    public String uploadToAWS(MultipartFile file) {
//        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String key = UUID.randomUUID().toString();
        System.out.println(key);
        try {

            ObjectMetadata metadata = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            metadata.setContentLength(bytes.length);

            metadata.setContentType(file.getContentType());

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            PutObjectRequest request = new PutObjectRequest(bucketName, key, byteArrayInputStream, metadata);
            request.withCannedAcl(CannedAccessControlList.PublicRead); // 접근권한 체크
            PutObjectResult result = s3Client.putObject(request);

            return key;
        } catch (AmazonServiceException e) {
            log.error("1.uploadToAWS AmazonServiceException filePath={}, error={}",key, e.getMessage());
        } catch (SdkClientException e) {
            log.error("2.uploadToAWS SdkClientException filePath={}, error={}",key, e.getMessage());
        } catch (Exception e) {
            log.error("3.uploadToAWS SdkClientException filePath={}, error={}",key, e.getMessage());
        }

        return key;
    }

    public void delete(String fileKey) {
        s3Client.deleteObject(bucketName, fileKey);
    }

    public void rename(String sourceKey, String destinationKey){
        s3Client.copyObject(
                bucketName,
                sourceKey,
                bucketName,
                destinationKey
        );
        s3Client.deleteObject(bucketName, sourceKey);
    }
}
