package guru.qa.rococo.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImgUtils {

  public static String convertImageToBase64(String filePath) {
    try (InputStream is = new ClassPathResource(filePath).getInputStream()) {
      return "data:image/png;base64," + Base64.getEncoder().encodeToString(is.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}