package by.training.controller.rest;

import static by.training.constants.UploadConstants.PHOTO_UPLOAD_PATH;
import static by.training.constants.MessageConstants.UPLOAD_FILE_ERROR;
import static by.training.constants.UrlConstants.Rest.UPLOAD_URL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import by.training.bean.ErrorMessage;
import by.training.exception.ValidationException;

@Controller
@MultipartConfig
@RequestMapping(UPLOAD_URL)
public class UploadRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = "/photo" + JSON_EXT, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> uploadFile(
            @RequestParam(value = "file") final MultipartFile file) {
        try {
            String path = PHOTO_UPLOAD_PATH + "/" + file.getOriginalFilename();
            upload(file, path);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    private void upload(final MultipartFile file, final String path) throws ValidationException {
        try (OutputStream out = new FileOutputStream(new File(path));
                InputStream filecontent = file.getInputStream()) {
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e) {
            throw new ValidationException(UPLOAD_FILE_ERROR);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
