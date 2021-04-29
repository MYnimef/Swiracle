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
                Price price = (Price) bundle.getSerializable("price");
                if (!brand.equals("") && !description.equals("") && price!= null) {
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

        public ParsingRunnable(String url){ this.url = url; }

        @Override
        public void run() { publishProgress(getData()); }

        private void publishProgress(Bundle bundle) {
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }

        private Bundle getData() {
            Bundle bundle = new Bundle();
            Document html;
            String brand = "", description = "";
            Price price = null;
            try {
                html = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                        " AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/90.0.4430.85 Safari/537.36").get();
                if (url.contains("tsum.ru")) {
                    brand = html.select(".item__specifications h1 a").text();
                    description = html.select(".item__description").text();
                    String priceStr = html.select("item-price").text();
                    price = new Price(parseToInt(priceStr), "RUB");
                } else if (url.contains("lamoda.ru")) {
                    brand = html.select(".product-title__brand-name").text();
                    description = html.select(".product-title__model-name").text();
                    String priceStr = html.select(".product-prices-root").text();
                    price = new Price(Integer.parseInt(priceStr), "RUB");
                } else if (url.contains("wildberries.ru")) {
                    brand = html.select(".brand").text();
                    description = html.select(".name").text();
                    String priceStr = html.select(".final-cost").text();
                    price = new Price(parseToInt(priceStr), "RUB");
                } else if (url.contains("gloria-jeans.ru")) {
                    brand = "Gloria Jeans";
                    description = html.select(".js-name-product").text();
                    String priceStr = html.select(".js-price-info").text();
                    price = new Price(parseToInt(priceStr), "RUB");
                } else if (url.contains("dsquared2.com")) {
                    brand = "Dsquared2";
                    description = html.select(".itemAction-title").text();
                    String priceStr = html.select(".priceUpdater").text();
                    price = new Price(parseToInt(priceStr), "EUR");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bundle.putString("brand", brand);
            bundle.putString("description", description);
            bundle.putSerializable("price", price);
            return bundle;
        }

        private int parseToInt(String str) {
            StringBuilder result = new StringBuilder();
            for (char symb : str.toCharArray()) {
                if (symb >= '0' && symb <= '9') {
                    result.append(symb);
                }
            }
            return Integer.parseInt(result.toString());
        }
    }
}