package com.bohan.android.capstone.MVP.CharacterList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohan.android.capstone.R;
import com.bohan.android.capstone.Utils.ImageUtils;
import com.bohan.android.capstone.model.ComicCharacterList;
import com.bohan.android.capstone.model.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.model.ModelHelper.ComicPublisherHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bo Han.
 */
public class CharacterAdapter extends RecyclerView<CharacterViewHolder> {
    private List<ComicCharacterList> characterList;
    final OnVolumeClickListener listener;

    CharacterAdapter(OnVolumeClickListener listener) {
        characterList = new ArrayList<>(0);
        this.listener = listener;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_characters_list_item, parent, false);

        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.bindTo(characterList.get(position));
    }

    @Override
    public int getItemCount() {
        return characterList == null ? 0 : characterList.size();
    }

    List<ComicCharacterList> getCharacterList() {
        return characterList;
    }

    void setCharacters(List<ComicCharacterList> volumes) {
        this.characterList = volumes;
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentCharacterId;

        @BindView(R.id.character_image_layout)
        FrameLayout imageLayout;
        @BindView(R.id.character_poster)
        ImageView characterPoster;
        @BindView(R.id.character_poster_progressbar)
        ProgressBar progressBar;
        @BindView(R.id.character_name)
        TextView characterName;
        @BindView(R.id.character_publisher)
        TextView characterPublisher;
        @BindString(R.string.volumes_publisher_text)
        String publisherFormat;

        CharacterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindTo(ComicCharacterList character) {

            currentCharacterId = character.characterId();

            ComicImageHelper mainImage = character.characterMainImage();
            if (mainImage != null) {
                String smallImageUrl = mainImage.imageSmallUrl();
                ImageUtils.fetchingImageWithProgress(progressBar, characterPoster, smallImageUrl);
            } else
                imageLayout.setVisibility(View.GONE);

            characterName.setText(character.characterName());
            ComicPublisherHelper publisher = character.characterMainPublisher();

            if (publisher != null) {
                String publisherName = String.format(Locale.US, publisherFormat, publisher.publisherName());
                characterPublisher.setText(publisherName);
            } else
                characterPublisher.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            listener.volumeClicked(currentCharacterId);
        }
    }

    interface OnVolumeClickListener {
        void volumeClicked(long characterId);
    }

}

