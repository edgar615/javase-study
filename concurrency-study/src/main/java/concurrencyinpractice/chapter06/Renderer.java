package concurrencyinpractice.chapter06;

import concurrencyinpractice.chapter05.LaunderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Renderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        new Renderer().renderPage("source");
        System.out.println("time : " + (System.currentTimeMillis() - begin));
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executorService);
        for (final ImageInfo imageInfo : imageInfos) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);

        try {
            for (int t = 0, n = imageInfos.size(); t < n; t ++) {
                Future<ImageData> future = completionService.take();
                ImageData imageData = future.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            LaunderThrowable.launderThrowable(e.getCause());
        } finally {
            executorService.shutdown();
        }

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

    ImageData renderImage(ImageData i) {
        return new ImageData();
    }
}