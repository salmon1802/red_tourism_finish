package com.redtourism.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @date 2021-1-15 - 18:59
 * Created by Salmon
 */
public interface IFileService {


    String upload(MultipartFile file, String path);


}
