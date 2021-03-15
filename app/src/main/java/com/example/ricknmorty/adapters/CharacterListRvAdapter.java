package com.example.ricknmorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmorty.R;
import com.example.ricknmorty.databinding.ItemCharacterBinding;
import com.example.ricknmorty.helpers.CharacterClickNavigator;
import com.example.ricknmorty.models.CharacterModel;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CharacterListRvAdapter extends RecyclerView.Adapter<CharacterListRvAdapter.CharacterListViewHolder> implements Filterable {

//    List for original data
    private List<CharacterModel> characterModelList;

//    List for search results
    private List<CharacterModel> filteredModelList;


    private CharacterClickNavigator navigator;

    public CharacterListRvAdapter(CharacterClickNavigator navigator) {
        this.characterModelList = new ArrayList<>();
        this.filteredModelList = new ArrayList<>();
        this.navigator = navigator;
    }

    public void addCharacters(List<CharacterModel> characterModels) {
        if(this.characterModelList == null){
            this.characterModelList = characterModels;
            this.filteredModelList = characterModels;
            notifyDataSetChanged();
        }else{
            int count = this.filteredModelList.size();
            characterModelList.addAll(characterModels);
            filteredModelList.addAll(characterModels);
            notifyItemRangeInserted(count, characterModels.size());
        }

    }

    @NonNull
    @Override
    public CharacterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_character, parent, false);

        return new CharacterListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CharacterListViewHolder holder, final int position) {
        holder.binding.setCharacterModel(filteredModelList.get(position));


//        Using Picasso to load the images from the network
        Picasso.get().load(filteredModelList.get(position).image).into(holder.binding.ivCharacterImage);

//        Setting the click event listener on the list items
        holder.binding.rlListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.onCharacterClicked(filteredModelList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredModelList != null ? filteredModelList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                String queryString = charSequence.toString();
//                Empty query returns the original character list
                if(queryString.isEmpty()){
                    results.values = characterModelList;
                    results.count = characterModelList.size();
                    return results;
                }
//              Else searching the query string against character name and return the matching results
                else {
                    List<CharacterModel> searchResults = new ArrayList<>();
                    for (CharacterModel character : characterModelList) {
                        if (character.name.toLowerCase().startsWith(queryString.toLowerCase())) {
                            searchResults.add(character);
                        }
                    }
                    results.values = searchResults;
                    results.count = searchResults.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//              Setting the result to the filteredModellist after the filter
                filteredModelList = (ArrayList<CharacterModel>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public class CharacterListViewHolder extends RecyclerView.ViewHolder {

        ItemCharacterBinding binding;
        public CharacterListViewHolder(ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
