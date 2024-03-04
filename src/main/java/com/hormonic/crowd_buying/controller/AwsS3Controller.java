package com.hormonic.crowd_buying.controller;

import com.hormonic.crowd_buying.domain.dto.AwsS3;
import com.hormonic.crowd_buying.service.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
    AWS S3 정상 작동 하는지!
 */
@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping
    public AwsS3 upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return awsS3Service.upload(multipartFile,"cb/recruit");
    }

    @DeleteMapping
    public void remove(AwsS3 awsS3) {
        awsS3Service.remove(awsS3);
    }
}
