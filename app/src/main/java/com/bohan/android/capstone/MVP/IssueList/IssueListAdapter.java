package com.bohan.android.capstone.MVP.IssueList;

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
import com.bohan.android.capstone.MVP.IssueList.IssueListAdapter.IssueListViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
class IssueListAdapter extends RecyclerView.Adapter<IssueListViewHolder> {

    private List<ComicIssueList> issues;
    final OnIssueClickListener listener;

    IssueListAdapter(OnIssueClickListener listener) {
        issues = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public IssueListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_issues_list_item, parent, false);

        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueListViewHolder holder, int position) {
        holder.bindTo(issues.get(position));
    }

    @Override
    public int getItemCount() {
        return issues == null ? 0 : issues.size();
    }

    @Override
    public long getItemId(int position) {
        return issues.get(position).issueId();
    }

    public List<ComicIssueList> getIssues() {
        return issues;
    }

    public void setIssues(List<ComicIssueList> issues) {
        this.issues = issues;
    }

    class IssueListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentIssueId;

        @BindView(R.id.issue_cover)
        ImageView issueCover;
        @BindView(R.id.issue_name)
        TextView issueName;
        /**
         *
         */
        @BindView(R.id.issue_cover_progressbar)
        ProgressBar progressBar;

        IssueListViewHolder(View itemView) {
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

            String coverUrl = issue.issueMainImage().imageSmallUrl();
            String issueNameText = issue.issueName();
            String volumeNameText = issue.volume().volumeName();
            int number = issue.issueNumber();

            String name = TextUtils.issueNameFromVolume(issueNameText, volumeNameText, number);
            issueName.setText(name);

            if (coverUrl != null) {
                ImageUtils.fetchingImageWithProgress(progressBar, issueCover, coverUrl);
            }
        }
    }

    interface OnIssueClickListener {

        void issueClicked(long issueId);
    }
}
