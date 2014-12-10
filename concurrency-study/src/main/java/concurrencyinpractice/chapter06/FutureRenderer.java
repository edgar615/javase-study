package concurrencyinpractice.chapter06;

import concurrencyinpractice.chapter05.LaunderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureRenderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        new FutureRenderer().renderPage("source");
        System.out.println("time : " + (System.currentTimeMillis() - begin));
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<ImageData>();
                for (ImageInfo imageInfo : imageInfos)
                    result.add(imageInfo.downloadImage());
                return result;
            }
        };

        Future<List<ImageData>> future = executorService.submit(task);
        renderText(source);
        try {
            List<ImageData> imageDatas = future.get();
            for (ImageData imageData : imageDatas) {
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
           //由于不需要结果，取消任务
           future.cancel(true);
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