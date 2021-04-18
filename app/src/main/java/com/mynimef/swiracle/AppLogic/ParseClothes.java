package com.mynimef.swiracle.AppLogic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISetClothesElements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseClothes {
    private final Handler handler;
    private final Handler errorHandler;

    public ParseClothes(String url, Fragment fragment) {
        ISetClothesElements connector = (ISetClothesElements) fragment;
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                connector.addClothes(msg.getData().getString("name"),
                        msg.getData().getString("description"),
                        msg.getData().getString("price"), url);
            }
        };
        this.errorHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                connector.showError();
            }
        };

        Thread thread = new Thread(new AnotherRunnable(url));
        thread.start();
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
            if (html != null) {
                if (url.contains("tsum.ru")) {
                    name = html.select(".item__specifications h1 a");
                    description = html.select(".item__description");
                    price = html.select("item-price");
                } else if (url.contains("lamoda.ru")) {
                    name = html.select(".product-title__brand-name");
                    description = html.select(".product-title__model-name");
                    price = html.select(".product-prices-root");
                } else if (url.contains("wildberries.ru")) {
                    name = html.select(".brand");
                    description = html.select(".name");
                    price = html.select(".final-cost");
                } else if (url.contains("gloria-jeans.ru")) {
                    name = html.select(".js-name-product");
                    description = html.select(".js-name-product");
                    price = html.select(".js-price-info");
                } else if (url.contains("dsquared2.com")) {
                    name = html.select(".itemAction-title");
                    description = html.select(".itemAction-title");
                    price = html.select("priceUpdater");
                }
            }
            if (name != null && description != null && price != null) {
                publishProgress(name.text(), description.text(), price.text());
            }
            else {
                errorHandler.sendEmptyMessage(1);
            }
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
    }
}
