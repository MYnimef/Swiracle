package com.mynimef.swiracle.AppLogic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.Interfaces.ISetInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseClothes {
    private final Handler handler;

    public ParseClothes(String url, Fragment fragment) {
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ISetClothesElements connector = (ISetClothesElements) fragment;
                connector.addClothes(msg.getData().getString("name"),
                        msg.getData().getString("description"),
                        msg.getData().getString("price"), url);
            }
        };

        Thread thread = new Thread(new AnotherRunnable(url));
        thread.start();
    }

    private void publishProgress(String name, String description, String price) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("description", description);
        bundle.putString("price", price);
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private class AnotherRunnable implements Runnable {
        private final String url;

        public AnotherRunnable(String url){
            this.url = url;
        }

        @Override
        public void run() {
            Document html = null;
            try {
                html = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                        " AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/89.0.4389.114 Safari/537.36").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements name = null, description = null, price = null;
            if (url.contains("tsum.ru")) {
                name = html.select(".item__specifications h1 a");
                description = html.select(".item__specifications h1 span");
                price = html.select("item-price");
            }
            else if (url.contains("lamoda.ru")) {
                name = html.select(".product-title-wrapper a");
                description = html.select(".product-title-wrapper span");
                price = html.select("vue-widget span span");
            }
            else if (url.contains("wildberries.ru")) {
                name = html.select(".brand-and-name j-product-title span brand");
                description = html.select(".brand-and-name j-product-title span name");
                price = html.select("final-price-block span");
            }
            else if (url.contains("gloria-jeans.ru")) {
                name = html.select(".basic-info js-block-for-shield h1");
                description = html.select(".basic-info js-block-for-shield h1");
                price = html.select("wrapper-price js-base-price");
            }
            else if (url.contains("dsquared2.com")) {
                name = html.select(".infoCta-wrapper h1");
                description = html.select(".product-title-wrapper h1 span");
                price = html.select("itemPrice span");
            }
            publishProgress(name.text(), description.text(), price.text());
        }
    }
}
