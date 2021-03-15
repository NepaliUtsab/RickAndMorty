package com.example.ricknmorty.viewModels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.ricknmorty.helpers.CharacterClickNavigator;
import com.example.ricknmorty.helpers.Constants;
import com.example.ricknmorty.models.BaseResponseModel;
import com.example.ricknmorty.models.CharacterModel;
import com.example.ricknmorty.repositories.CharacterRepo;

import java.util.List;

public class CharacterVM extends ViewModel {

    private CharacterRepo characterRepo;

    private MutableLiveData<CharacterModel> mutableSelectedCharacter;

    private MutableLiveData<List<CharacterModel>> mutableCharacterList;

    private int currentPage = 1;

//    Initializing CharacterRepo
    public CharacterVM(){
        characterRepo = new CharacterRepo();
    }

//  Returns a live data object for the ui
    public LiveData<List<CharacterModel>> getCharacters(){
        if(mutableCharacterList == null) {
            mutableCharacterList = new MutableLiveData<>();
        }
        mutableCharacterList = characterRepo.getCharacterList();
        return mutableCharacterList;
    }

//  sets the currently selected character model for Character Detail UI
    public void setCharacterDetailModel(CharacterModel characterModel){
        mutableSelectedCharacter = new MutableLiveData<>();
        mutableSelectedCharacter.setValue(characterModel);
    }
}
