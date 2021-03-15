package com.example.ricknmorty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ricknmorty.databinding.ActivityMainBinding;
import com.example.ricknmorty.adapters.CharacterListRvAdapter;
import com.example.ricknmorty.helpers.CharacterClickNavigator;
import com.example.ricknmorty.helpers.Constants;
import com.example.ricknmorty.helpers.MyApplication;
import com.example.ricknmorty.models.CharacterModel;
import com.example.ricknmorty.viewModels.CharacterVM;
import com.example.ricknmorty.views.CharacterDetailActivity;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterClickNavigator, SearchView.OnQueryTextListener {


    private ActivityMainBinding binding;

    private CharacterListRvAdapter adapter;
    private CharacterVM characterVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Binding the view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportActionBar().hide();
//        Initializing View Model
        characterVM = new CharacterVM();

        initUi();

        getCharacterLists();

    }

    /*
    * Method for getting list of characters
    * */
    private void getCharacterLists() {
//        Checks if Internet is available to either fetch data from network
//        else show error placeholder for User
        if(MyApplication.getAppInstance().isNetworkAvailable()){
            binding.rvCharacterList.setVisibility(View.VISIBLE);
            binding.tvErrorConnection.setVisibility(View.GONE);
//            observes the character model and updates UI when model is updated
            characterVM.getCharacters().observe(this,   new Observer<List<CharacterModel>>() {
                @Override
                public void onChanged(List<CharacterModel> characterModels) {
                    if(characterModels != null && !characterModels.isEmpty()){
                        adapter.addCharacters(characterModels);
                    }
                }
            });
        }else{

            binding.rvCharacterList.setVisibility(View.GONE);
            binding.tvErrorConnection.setVisibility(View.VISIBLE);
            binding.tvErrorConnection.setText(Constants.NO_INTERNET_CONNECTION);
        }
    }

    /*
    * Initializing the UI components
    * */
    private void initUi() {
        binding.rvCharacterList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CharacterListRvAdapter(this);
        final LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvCharacterList.getLayoutManager();
        binding.rvCharacterList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvCharacterList.getContext(),
                layoutManager.getOrientation());
        binding.rvCharacterList.addItemDecoration(dividerItemDecoration);

        binding.rvCharacterList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(layoutManager.getItemCount() <= layoutManager.findLastVisibleItemPosition() + 3){
                    getCharacterLists();
                }
            }
        });

        binding.svCharacterSearch.setOnQueryTextListener(this);
    }

    @Override
    public void onCharacterClicked(CharacterModel characterModel) {
        characterVM.setCharacterDetailModel(characterModel);
        Intent intent = new Intent(this, CharacterDetailActivity.class);
        intent.putExtra("CharacterModel", new Gson().toJson(characterModel));
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}