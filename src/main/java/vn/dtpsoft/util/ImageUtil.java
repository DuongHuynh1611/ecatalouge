package vn.dtpsoft.util;

import java.util.Arrays;
import java.util.List;

public class ImageUtil {

    private ImageUtil(){}

    public static boolean isImageType(String contentType) {
        final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif", "image/webp");
        return contentTypes.contains(contentType);
    }
}
