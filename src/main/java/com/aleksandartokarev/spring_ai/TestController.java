package com.aleksandartokarev.spring_ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final ChatClient openAIChatClient;
    private final ChatClient anthropicChatClient;
    private final PdfUtilsService pdfUtilsService;

    @GetMapping("/ask")
    String askQuestion(@RequestParam(name = "question") String question) {
        return openAIChatClient.prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    @GetMapping("/convertPdf")
    void convertPdf(@RequestParam(name = "documentName") String documentName) {
        pdfUtilsService.convertPdf(documentName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/openai/pdf", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public LoadAIDTO uploadFile(@RequestParam("file") MultipartFile file) {
        List<byte[]> images = pdfUtilsService.convertPdfWithUploadedFile(convertMultiPartFileToFile(file));
        Media[] media = images.stream().map(imageStream -> new Media(MimeTypeUtils.IMAGE_JPEG, imageStream)).toArray(Media[]::new);
        LoadAIDTO loadAIDTO = null;
        try {
            loadAIDTO =  openAIChatClient.prompt()
                    .user(u -> u
                            .text("I am uploading you a Freight Rate Confirmation images. I want you to extract me rate of the load in a field called 'rate',and also a list of load stops in the following format:'type'" +
                                    "-whether it is pickup or delivery (it has to have those two values),'date'-in a format 'YYYY-MM-DD','time' - in a format 'HH:MM:SS','shipper' - name of the company of the stop,'address'-" +
                                    "the address of the company,'city'-the city of the company from the address" +
                                    "'state' - the state of the company from the address using the two letter abbreviation,'zip'-" +
                                    "the zip of the company from the address' - they should be in a json format. If you can't find something, return it as empty field").
                            media(media)
                    )
                    .call()
                    .entity(LoadAIDTO.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return loadAIDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/anthropic/pdf", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public LoadAIDTO uploadFileAnthropic(@RequestParam("file") MultipartFile file) {
        List<byte[]> images = pdfUtilsService.convertPdfWithUploadedFile(convertMultiPartFileToFile(file));
        Media[] media = images.stream().map(imageStream -> new Media(MimeTypeUtils.IMAGE_JPEG, imageStream)).toArray(Media[]::new);
        LoadAIDTO loadAIDTO = null;
        try {
            loadAIDTO =  anthropicChatClient.prompt()
                    .user(u -> u
                            .text("I am uploading you a Freight Rate Confirmation images. I want you to extract me rate of the load in a field called 'rate',and also a list of load stops in the following format:'type'" +
                                    "-whether it is pickup or delivery (it has to have those two values),'date'-in a format 'YYYY-MM-DD','time' - in a format 'HH:MM:SS','shipper' - name of the company of the stop,'address'-" +
                                    "the address of the company,'city'-the city of the company from the address" +
                                    "'state' - the state of the company from the address using the two letter abbreviation,'zip'-" +
                                    "the zip of the company from the address' - they should be in a json format. If you can't find something, return it as empty field").
                            media(media)
                    )
                    .call()
                    .entity(LoadAIDTO.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return loadAIDTO;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.error("Convert MultipartFile to File Failed");
        }
        return convertedFile;
    }

    @GetMapping("/image-to-text")
    public String image(@RequestParam(name = "documentName") String documentName) throws IOException {
        List<byte[]> images = pdfUtilsService.convertPdf(documentName);
        Media[] media = images.stream().map(imageStream -> new Media(MimeTypeUtils.IMAGE_JPEG, imageStream)).toArray(Media[]::new);
        return openAIChatClient.prompt()
                .user(u -> u
                .text("I am uploading you a Freight Rate Confirmation images. I want you to extract me rate of the load in a field called 'rate',and also a list of load stops in the following format:'type'" +
                    "-whether it is pickup or delivery (it has to have those two values),'date'-in a format 'YYYY-MM-DD','time' - in a format 'HH:MM:SS','shipper' - name of the company of the stop,'address'-" +
                        "the address of the company,'city'-the city of the company from the address" +
                        "'state' - the state of the company from the address using the two letter abbreviation,'zip'-" +
                        "the zip of the company from the address' - they should be in a json format. If you can't find something, return it as empty field").
                                media(media)
                )
                .call()
                .content();
    }

//    PDF Uploaded is not supported yet
//    error": {
//            "message": "Invalid MIME type. Only image types are supported.",
//            "type": "invalid_request_error",
//            "param": null,
//            "code": "invalid_image_format"
//    }
}
