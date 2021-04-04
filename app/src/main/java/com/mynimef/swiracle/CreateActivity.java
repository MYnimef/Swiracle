package com.mynimef.swiracle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.AppLogic.Singleton;
import com.mynimef.swiracle.CreateFragments.PickImageFragment;
import com.mynimef.swiracle.CreateFragments.SetInfoFragment;
import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.Interfaces.IPickImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity implements IPickImage, IFragmentConnector {
    FragmentManager fm;
    private ArrayList<Bitmap> images;
    Button backToMain, back, next, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        fm = getSupportFragmentManager();

        backToMain = findViewById(R.id.backToMainButton);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateActivity.this, MainActivity.class));
            }
        });

        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                share.setVisibility(View.GONE);
                backToMain.setVisibility(View.VISIBLE);
                replaceFragment(new PickImageFragment());
            }
        });

        next = findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.GONE);
                backToMain.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                replaceFragment(new SetInfoFragment());
            }
        });

        share = findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetInfoFragment fragment = (SetInfoFragment) getSupportFragmentManager().findFragmentById(R.id.create_nav_host_fragment);
                Singleton list = Singleton.getInstance();
                list.addToList(new Post(fragment.getTitle(), fragment.getDescription(), images));
                startActivity(new Intent(CreateActivity.this, MainActivity.class));
            }
        });

        images = new ArrayList<>();
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                images.add(selectedImage);
                next.setVisibility(View.VISIBLE);

                PickImageFragment fragment = (PickImageFragment) getSupportFragmentManager().findFragmentById(R.id.create_nav_host_fragment);
                fragment.setImageView(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(CreateActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(CreateActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteImage(int index) {
        images.remove(index);
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.create_nav_host_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}