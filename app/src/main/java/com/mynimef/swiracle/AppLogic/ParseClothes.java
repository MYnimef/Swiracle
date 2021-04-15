package com.mynimef.swiracle.AppLogic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.Interfaces.ISetInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseClothes {
    private Handler handler;

    public ParseClothes(String url, Fragment fragment) {
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ISetInfo connector = (ISetInfo) fragment;
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
        private String url;

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
            Elements els = html.select(".item__specifications h1 a");
            Elements els1 = html.select(".item__specifications h1 span");
            Elements els2 = html.select("item-price");
            String name = els.text();
            String description = els1.text();
            String price = els2.text();
            publishProgress(name, description, price);
        }
    }
}
