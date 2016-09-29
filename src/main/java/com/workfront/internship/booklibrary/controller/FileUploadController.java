package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.dao.BookDAOImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ${Sona} on 9/29/2016.
 */
@Controller
public class FileUploadController {
    private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
//    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private MultipartResolver multipartResolver;

    private void initMultipartResolver(ApplicationContext context)
    {
        try
        {
            this.multipartResolver = ((MultipartResolver)context.getBean("multipartResolver", MultipartResolver.class));
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("Using MultipartResolver [" + this.multipartResolver + "]");
            }
        }
        catch (NoSuchBeanDefinitionException ex)
        {
            this.multipartResolver = null;
            if (this.LOGGER.isDebugEnabled())
                this.LOGGER.debug("Unable to locate MultipartResolver with name 'multipartResolver': no multipart request handling provided");
        }
    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) { //@RequestParam("name") String fileName,

        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    /**
     * Upload multiple file using Spring Controller
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) { //@RequestParam("name") String[] fnames,

        String[] names = new String[files.length];
        for(int i = 0; i < names.length; i++){
            names[i] = files[i].getOriginalFilename();
        }

        if (files.length != names.length) {
            return "Mandatory information missing";
        }

        String message = "";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = names[i];
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home"); //"catalina.base";
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

                message = message + "You successfully uploaded file=" + name + " ";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        return message;
    }

    @RequestMapping("/uploadFile")
    public String goToFileUpload(){
        return "FileUpload";
    }

    @RequestMapping("/uploadMultipleFile")
    public String goToMultipleFileUpload(){
        return "MultipleFileUpload";
    }
}
