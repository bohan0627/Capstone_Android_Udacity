package com.bohan.android.capstone.MVP.IssueDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicCharacterShort;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**s
 * Created by Bo Han.
 */
public class IssueCharacterAdapter extends BaseAdapter {

    private List<ComicCharacterShort> characterList;

    IssueCharacterAdapter(List<ComicCharacterShort> characterList) {
        this.characterList = characterList;
    }

    public void replaceCharacterListShort(List<ComicCharacterShort> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CharacterViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (CharacterViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_issue_details_character_item, parent, false);
            viewHolder = new CharacterViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.bindTo(characterList.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return characterList.size();
    }

    @Override
    public ComicCharacterShort getItem(int position) {
        return characterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return characterList.get(position).characterId();
    }


    class CharacterViewHolder {
        public long characterId;

        @BindView(R.id.issue_details_character_name)
        TextView characterName;

        CharacterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void bindTo(ComicCharacterShort character) {
            String newName = character.characterName();
            characterId = character.characterId();

            if (newName  != null)
                characterName.setText(newName );
        }
    }
}
