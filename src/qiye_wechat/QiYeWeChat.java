package qiye_wechat;


import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/8/29 13:53
 */
public class QiYeWeChat {

    private  static ArrayBlockingQueue<String> arrayBlockingQueue = null;

    public static void main(String[] args) throws IOException, InterruptedException {

        for (int i=0;i<5000000;i++){
            sendMonitor("测试123");
        }

    }

    public static void sendMonitor(String content) {
        if (arrayBlockingQueue == null){
            synchronized (QiYeWeChat.class){
                if(arrayBlockingQueue == null){
                    arrayBlockingQueue = new ArrayBlockingQueue<>(1000);
                    new Thread(new MonitorThread()).start();
                }
            }
        }
        arrayBlockingQueue.offer(content);
    }

    static class MonitorThread implements Runnable{
        @Override
        public void run() {
            while (true){
                String content  = null;
                try {
                    content = arrayBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(content != null){
                    System.out.println("报警成功");

                }
                //配置的WebHook地址
                /*String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=43f08fdb-c89d-4bd2-a9ad-4b5bd67795f7";
                String reqBody ="{" +
                        "    \"msgtype\":\"text\"," +
                        "    \"agentid\":1," +
                        "    \"text\":{" +
                        "    \"content\":" + "\"" +content + "\"" +
                        "    \"safe\":1" +
                        "}";

                //组装客户端
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)// 设置连接超时时间
                        .readTimeout(20, TimeUnit.SECONDS)// 设置读取超时时间
                        .build();
                MediaType contentType = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(contentType, reqBody);
                Request request = new Request.Builder().url(url).post(body).addHeader("cache-control", "no-cache").build();
                Response response = null;
                try {
                    //发送
                    response = client.newCall(request).execute();

                    //得到返回结果
                    byte[] data = response.body().bytes();
                    String respMsg = new String(data);

                } catch (Exception e) {

                }*/
            }
        }
    }

}


