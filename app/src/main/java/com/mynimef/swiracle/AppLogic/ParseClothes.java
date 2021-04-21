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
            String brand = "", description = "", price = "";
            if (html != null) {
                if (url.contains("tsum.ru")) {
                    brand = html.select(".item__specifications h1 a").text();
                    description = html.select(".item__description").text();
                    price = html.select("item-price").text();
                } else if (url.contains("lamoda.ru")) {
                    brand = html.select(".product-title__brand-name").text();
                    description = html.select(".product-title__model-name").text();
                    price = html.select(".product-prices-root").text();
                } else if (url.contains("wildberries.ru")) {
                    brand = html.select(".brand").text();
                    description = html.select(".name").text();
                    price = html.select(".final-cost").text();
                } else if (url.contains("gloria-jeans.ru")) {
                    brand = "Gloria Jeans";
                    description = html.select(".js-name-product").text();
                    price = html.select(".js-price-info").text();
                } else if (url.contains("dsquared2.com")) {
                    brand = "Dsquared2";
                    description = html.select(".itemAction-title").text();
                    price = html.select(".priceUpdater").text();
                }
            }
            if (!brand.equals("") && !description.equals("") && !price.equals("")) {
                publishProgress(brand, description, price);
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
