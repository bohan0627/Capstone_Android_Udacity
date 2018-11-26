package com.bohan.android.capstone.MVP.VolumeDetails;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicIssueShort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
class VolumeDetailsIssueAdapter extends
        RecyclerView.Adapter<VolumeDetailsIssueAdapter.IssueViewHolder> {

    private List<ComicIssueShort> issues;
    final IssuesAdapterCallbacks listener;

    VolumeDetailsIssueAdapter(IssuesAdapterCallbacks listener) {
        issues = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_volume_details_issue_item, parent, false);

        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
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

    public List<ComicIssueShort> getIssues() {
        return issues;
    }

    public void setIssues(List<ComicIssueShort> issues) {
        this.issues = issues;
    }

    class IssueViewHolder extends RecyclerView.ViewHolder {

        private long currentIssueId;

        @BindView(R.id.issue_number)
        TextView issueNumber;
        @BindString(R.string.volume_details_issue_number)
        String issueNumberFormat;
        @BindView(R.id.issue_name)
        TextView issueName;
        @BindView(R.id.issue_bookmarked_icon)
        ImageView bookmarkIcon;


        IssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> listener.issueClicked(currentIssueId));
        }

        void bindTo(ComicIssueShort issue) {

            currentIssueId = issue.issueId();
            int number = issue.issueNumber();
            issueNumber.setText(String.format(Locale.US, issueNumberFormat, number));

            String issueNameText = issue.issueName();

            if (issueNameText != null) {
                issueName.setText(issueNameText);
            } else {
                issueName.setVisibility(View.GONE);
            }

            if (listener.isIssueTracked(currentIssueId)) {
                bookmarkIcon.setImageResource(R.drawable.ic_bookmark_black_24dp);
            } else {
                bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            }
        }
    }

    interface IssuesAdapterCallbacks {

        void issueClicked(long issueId);

        boolean isIssueTracked(long issueId);
    }
}