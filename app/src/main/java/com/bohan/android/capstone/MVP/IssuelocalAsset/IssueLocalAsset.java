package com.bohan.android.capstone.MVP.IssuelocalAsset;

import android.support.v7.widget.RecyclerView;

import com.bohan.android.capstone.model.ComicModel.ComicIssueList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bo Han.
 */

@SuppressWarnings("WeakerAccess")
class IssueLocalAsset extends RecyclerView.Adapter<IssueLocalViewHolder> {

    private List<ComicIssueList> issues;
    final OnIssueClickListener listener;

    IssueLocalAsset(OnIssueClickListener listener) {
        issues = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public OwnedIssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_owned_issues_list_item, parent, false);

        return new OwnedIssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnedIssueViewHolder holder, int position) {
        holder.bindTo(issues.get(position));
    }

    @Override
    public int getItemCount() {
        return issues == null ? 0 : issues.size();
    }

    @Override
    public long getItemId(int position) {
        return issues.get(position).id();
    }

    public List<ComicIssueList> getIssues() {
        return issues;
    }

    public void setIssues(List<ComicIssueList> issues) {
        this.issues = issues;
    }

    class IssueLocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentIssueId;

        @BindView(R.id.issue_cover)
        ImageView issueCover;
        @BindView(R.id.issue_name)
        TextView issueName;
        @BindView(R.id.issue_cover_progressbar)
        ProgressBar progressBar;

        OwnedIssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.issueClicked(currentIssueId);
        }

        void bindTo(ComicIssueInfoList issue) {

            currentIssueId = issue.id();

            String coverUrl = issue.image().small_url();
            String issueNameText = issue.name();
            String volumeNameText = issue.volume().name();
            int number = issue.issue_number();

            String name = IssueTextUtils.getFormattedIssueName(issueNameText, volumeNameText, number);
            issueName.setText(name);

            if (coverUrl != null) {
                ImageUtils.loadImageWithProgress(issueCover, coverUrl, progressBar);
            }
        }
    }

    interface OnIssueClickListener {

        void issueClicked(long issueId);
    }
