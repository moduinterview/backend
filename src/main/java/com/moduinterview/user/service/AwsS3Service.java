package com.moduinterview.user.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.moduinterview.common.model.ServiceResult;
import com.moduinterview.user.dto.AwsS3Response;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

  @Value("${cloud.aws.s3.bucket}")
  private  String bucket;
  @Value("${cloud.aws.s3.encrypt-key}")
  private  String ENCRYPT_KEY;
  @Value("${cloud.aws.s3.limitation.max-file-size}")
  private int MAX_FILE_SIZE;
  @Value("${cloud.aws.s3.limitation.max-image-width}")
  private int MAX_IMAGE_WIDTH;
  @Value("${cloud.aws.s3.limitation.max-image-height}")
  private int MAX_IMAGE_HEIGHT;


  private final AmazonS3 amazonS3;

  private String generateImageName(Long interviewId) {
    return UUID.fromString(ENCRYPT_KEY + interviewId.toString()).toString();
  }

  public ServiceResult uploadImage(MultipartFile multipartFile, Long interviewId)
      throws IOException {

    if (multipartFile == null) {
      return ServiceResult.fail("파일이 존재하지 않습니다.");
    }

    //파일 사이즈 확인
    if (multipartFile.getSize() > MAX_FILE_SIZE) {
      return ServiceResult.fail("3MB 이하의 파일만 업로드 가능합니다.");
    }

    String ext = Objects.requireNonNull(multipartFile.getOriginalFilename())
        .substring(multipartFile.getOriginalFilename().lastIndexOf("."));

    //ext(확장자)의 검증
    if (!(ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png"))) {
      return ServiceResult.fail("jpg,jpeg, png 파일만 업로드 가능합니다.");
    }

    //파일 존재여부 확인
    String imageName = generateImageName(interviewId) + ext;
    if (doesFileExist(imageName)) {
      ServiceResult serviceResult = deleteImage(imageName);
      if (serviceResult.isFail()) {
        serviceResult.setMessage("기존 이미지가 존재하나, 삭제에 실패하였습니다.");
        return serviceResult;
      }
    }

    BufferedImage image;
    InputStream inputStream = multipartFile.getInputStream();
    image = ImageIO.read(inputStream);
    inputStream.close();
    if (image == null) {
      return ServiceResult.fail("이미지 업로드에 실패하였습니다.");
    }
    int width = image.getWidth();
    int height = image.getHeight();
    if (width > MAX_IMAGE_WIDTH || height > MAX_IMAGE_HEIGHT) {
      return ServiceResult.fail("이미지의 최대 크기는 1920x1080 입니다.");
    }

    //MetaData 설정
    String contentType = "";
    switch (ext) {
      case ".jpeg":
        contentType = "image/jpeg";
        break;
      case ".png":
        contentType = "image/png";
        break;
      case ".jpg":
        contentType = "image/jpg";
        break;
    }

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(contentType);
    objectMetadata.setContentLength(multipartFile.getSize());


    //존재한다면 파일 삭제 후 업로드
    amazonS3.putObject(bucket, imageName, multipartFile.getInputStream(), objectMetadata);
    multipartFile.getInputStream().close();

    String url = amazonS3.getUrl(bucket, imageName).toString();
    AwsS3Response awsS3Response = new AwsS3Response(url);

    return ServiceResult.success("이미지가 성공적으로 저장되었습니다.", awsS3Response);
  }

  private boolean doesFileExist(String fileName) {
    try {

      return amazonS3.doesObjectExist(bucket, fileName);
    } catch (AmazonServiceException e) {
      if (e.getStatusCode() == 404) {
        return false;
      }
      throw e; // 다른 종류의 오류가 발생한 경우 예외 던지기
    }
  }

  public ServiceResult deleteImage(String imgName) {
    try {
      amazonS3.deleteObject(bucket, imgName);
      return ServiceResult.success("이미지가 성공적으로 삭제되었습니다.");
    } catch (AmazonServiceException e) {
      return ServiceResult.fail("이미지 삭제에 실패하였습니다.");
    }
  }

  public ServiceResult getImage(Long userId){
    String imageName = generateImageName(userId);
    if(!doesFileExist(imageName)){
      return ServiceResult.fail("이미지가 존재하지 않습니다.");
    }
    String url = amazonS3.getUrl(bucket, imageName).toString();
    AwsS3Response awsS3Response = new AwsS3Response(url);
    return ServiceResult.success("이미지가 성공적으로 조회되었습니다.", awsS3Response);
  }

}
