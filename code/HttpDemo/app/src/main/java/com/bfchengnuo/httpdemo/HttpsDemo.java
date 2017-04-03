import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by 冰封承諾Andy on 2017/3/22 0022.
 */
public class HttpsDemo {
    public static void main(String[] args) {
        doHTTPS();
    }

    private static void doHTTPS() {
        try {
            // 忽略证书
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new MyTrustManager() },
                    new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new MyHostnameVerifier());

            // 开始请求，使用 HttpURLConnection 也可以
            // HttpsURLConnection 是 HttpURLConnection 的一个扩展，继承自它
            URL url = new URL("https://bfchengnuo.com");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(2000);

            BufferedReader bufr = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = bufr.readLine()) != null){
                sb.append(str);
            }

            System.out.println(sb.toString());

            conn.disconnect();
            bufr.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void doGet(){
        try {
            String data = "萝莉";
            data = URLEncoder.encode(data,"utf-8");
            URL url = new URL("http://www.baidu.com/s?wd=" + data);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(1000);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            InputStream is = conn.getInputStream();
            byte[] bufr = new byte[2*1024];
            int len;
            while ((len = is.read(bufr)) != -1){
                os.write(bufr,0,len);
            }

            System.out.println(new String(os.toByteArray(),"utf-8"));

            conn.disconnect();
            os.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }
}