package concurrencyinpractice.chapter06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SingleThreadRenderer {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        new SingleThreadRenderer().renderPage("source");
        System.out.println("time : " + (System.currentTimeMillis() - begin));
    }

    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();
        for (ImageInfo imageInfo : scanForImageInfo(source))
            imageData.add(imageInfo.downloadImage());
        for (ImageData data : imageData)
            renderImage(data);
    }

    class ImageData {
    }

    class ImageInfo {
        ImageData downloadImage() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ImageData();
        }
    }

    void renderText(CharSequence s) {
    }
    List<ImageInfo> scanForImageInfo(CharSequence s){
        List<ImageInfo> infos = new ArrayList<ImageInfo>();
        infos.add(new ImageInfo());
        infos.add(new ImageInfo());
        infos.add(new ImageInfo());
        return infos;
    }

    void renderImage(ImageData i) {

    }
}