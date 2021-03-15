package com.example.ricknmorty.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricknmorty.R;
import com.example.ricknmorty.databinding.ActivityCharacterDetailBinding;
import com.example.ricknmorty.models.CharacterModel;
import com.example.ricknmorty.viewModels.CharacterVM;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

public class CharacterDetailActivity extends AppCompatActivity {

    private CharacterModel characterModel;

    //This can be done in better way in Kotlin
    //I have chose not to use data binding in this activity
//    Since i am passing the character model from previous activity so no network call would be required
    private TextView tvTitle;
    private TextView tvStatus;
    private TextView tvSpecies;
    private TextView tvType;
    private TextView tvGender;
    private TextView tvOrigin;
    private TextView tvLocation;
    private ImageView ivProfile;
    private ImageView ivClose;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        getSupportActionBar().hide();
        this.characterModel = new Gson().fromJson(getIntent().getStringExtra("CharacterModel"), CharacterModel.class);

        initUi();
    }

    private void initUi() {
//        Initializing Ui Elements
        tvTitle = findViewById(R.id.tvCharacterName);
        tvStatus = findViewById(R.id.tvCharacterStatus);
        tvSpecies = findViewById(R.id.tvCharacterSpecies);
        tvType = findViewById(R.id.tvCharacterType);
        tvGender = findViewById(R.id.tvCharacterGender);
        tvOrigin = findViewById(R.id.tvCharacterOrigin);
        tvLocation = findViewById(R.id.tvCharacterLocation);
        ivProfile = findViewById(R.id.ivCharaterImage);
        ivClose = findViewById(R.id.ivClose);

//        Setting value for each field without databinding
        tvTitle.setText(characterModel.name);
        tvStatus.setText("Current Status: " + characterModel.status);
        tvSpecies.setText(characterModel.species != null ? "Species: " + characterModel.species : "Unknown");
        tvType.setText((characterModel.type != null && !characterModel.type.equals("")) ? "Type: " + characterModel.type : "Type: Unknown");
        tvGender.setText(characterModel.species != null ? "Gender: " + characterModel.species : "Unknown");
        tvOrigin.setText(characterModel.origin.name != null ? "Origin: " + characterModel.origin.name : "Unknown");
        tvLocation.setText(characterModel.location.name != null ? "Current Location: " + characterModel.location.name : "Unknown");

//        Using Picasso Library for loading images from nwtwork
        Picasso.get().load(characterModel.image).into(ivProfile);

//        Custom Close button to close the activity
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}