package com.mynimef.swiracle.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISetClothesElements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseClothes {
    private final Handler handler;

    public ParseClothes(String url, Fragment fragment) {
        ISetClothesElements connector = (ISetClothesElements) fragment;
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String brand = bundle.getString("brand");
                String description = bundle.getString("description");
                String price = bundle.getString("price");
                if (!brand.equals("") && !description.equals("") && !price.equals("")) {
                    connector.addClothes(brand, description, price, url);
                }
                else {
                    connector.showError();
                }
            }
        };

        Thread thread = new Thread(new ParsingRunnable(url));
        thread.start();
    }

    private class ParsingRunnable implements Runnable {
        private final String url;

        public ParsingRunnable(String url){
            this.url = url;
        }

        @Override
        public void run() {
            publishProgress(getData());
        }

        private void publishProgress(String[] info) {
            Bundle bundle = new Bundle();
            bundle.putString("brand", info[0]);
            bundle.putString("description", info[1]);
            bundle.putString("price", info[2]);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }

        private String[] getData() {
            Document html;
            String brand = "", description = "", price = "";
            try {
                html = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                        " AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/89.0.4389.114 Safari/537.36").get();
            } catch (Exception e) {
                return new String[]{brand, description, price};
            }

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
            return new String[]{brand, description, price};
        }
    }
}