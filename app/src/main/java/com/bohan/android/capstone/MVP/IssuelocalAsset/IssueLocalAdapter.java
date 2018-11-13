package com.bohan.android.capstone.MVP.IssuelocalAsset;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.Helper.Utils.TextUtils;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.MVP.IssuelocalAsset.IssueLocalAdapter.IssueLocalViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
class IssueLocalAdapter extends RecyclerView.Adapter<IssueLocalViewHolder> {

    private List<ComicIssueList> issueList;
    final OnIssueClickListener listener;

    IssueLocalAdapter(OnIssueClickListener listener) {
        issueList = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public IssueLocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_owned_issueList_list_item, parent, false);

        return new IssueLocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueLocalViewHolder holder, int position) {
        holder.bindTo(issueList.get(position));
    }

    @Override
    public int getItemCount() {
        return issueList == null ? 0 : issueList.size();
    }

    @Override
    public long getItemId(int position) {
        return issueList.get(position).id();
    }

    public List<ComicIssueList> getIssueList() {
        return issueList;
    }

    public void setissueList(List<ComicIssueList> issueList) {
        this.issueList = issueList;
    }

    class IssueLocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentIssueId;

        @BindView(R.id.issue_cover)
        ImageView issueCover;
        @BindView(R.id.issue_name)
        TextView issueName;
        @BindView(R.id.issue_cover_progressbar)
        ProgressBar progressBar;

        IssueLocalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.issueClicked(currentIssueId);
        }

        void bindTo(ComicIssueList issue) {

            currentIssueId = issue.issueId();
            String imageSmallUrl = issue.issueMainImage().imageSmallUrl();
            String issueNameText = issue.issueName();
            String volumeNameText = issue.volume().volumeName();
            int number = issue.issueNumber();
            String newName = TextUtils.issueNameFromVolume(issueNameText, volumeNameText, number);
            issueName.setText(newName);

            if (imageSmallUrl != null)
                ImageUtils.fetchingImageWithProgress(progressBar,issueCover, imageSmallUrl);
        }
    }

    interface OnIssueClickListener {

        void issueClicked(long issueId);
    }
}