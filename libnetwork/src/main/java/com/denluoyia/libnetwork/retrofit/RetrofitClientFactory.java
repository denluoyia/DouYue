package com.denluoyia.libnetwork.retrofit;

import android.annotation.SuppressLint;

import com.denluoyia.libnetwork.model.BookItemBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denluoyia on 2019/3/18
 * Description:
 */
public class RetrofitClientFactory implements ServerApi {

    private final String baseUrl = "https://api.douban.com/v2/book/";
    private X509TrustManager trustManager;
    private SSLSocketFactory sslSocketFactory;

    private static volatile RetrofitClientFactory sInstance;

    private final ServerApi getServerApi(){
        return getInstance().getRetrofit().create(ServerApi.class);
    }
    @Override
    public Observable<BookItemBean> getBookDetail(String id) {
        return getServerApi().getBookDetail(id);
    }

    public static RetrofitClientFactory getInstance(){
        if (sInstance == null){
            synchronized (RetrofitClientFactory.class){
                if (sInstance == null){
                    sInstance = new RetrofitClientFactory(null);
                }
            }
        }

        return sInstance;
    }
    public Retrofit getRetrofit(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.sslSocketFactory(sslSocketFactory, trustManager);

        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(new RequestInterceptor(baseUrl));
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build();
    }


    public RetrofitClientFactory(InputStream certFileStream) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");

            if (certFileStream != null) {
                // Trust https certificate
                trustManager = trustManagerForCertificates(certFileStream);
            } else {
                // Trust all certificate
                trustManager = new TrustAllManager();
            }

            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     *
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        // Any password will work.
        char[] password = "password".toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static class TrustAllManager implements X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }
}
