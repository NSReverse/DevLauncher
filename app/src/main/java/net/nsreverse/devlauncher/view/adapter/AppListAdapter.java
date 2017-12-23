package net.nsreverse.devlauncher.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.nsreverse.devlauncher.R;
import net.nsreverse.devlauncher.model.ApplicationEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Robert on 12/22/2017.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {

    private Delegate delegate;
    private List<ApplicationEntry> dataSource;

    public AppListAdapter(Fragment fragment) {
        if (fragment instanceof Delegate) {
            delegate = (Delegate)fragment;
        }
    }

    // Used when an Activity handles the clicks
    @SuppressWarnings("unused") public AppListAdapter(Context context) {
        if (context instanceof Delegate) {
            delegate = (Delegate)context;
        }
    }

    public void setDataSource(List<ApplicationEntry> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public interface Delegate {
        void applicationSelected(int position, ApplicationEntry selectedEntry);
        void applicationLongSelected(int position, ApplicationEntry selectedEntry);
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_item_app_list_cell, parent,
                false);

        return new AppListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        holder.titleTextView.setText(dataSource.get(position).label);
        holder.iconImageView.setImageDrawable(dataSource.get(position).icon);
    }

    @Override
    public int getItemCount() {
        if (dataSource != null) {
            return dataSource.size();
        }

        return 0;
    }

    class AppListViewHolder extends RecyclerView.ViewHolder
                            implements View.OnClickListener,
                                       View.OnLongClickListener {

        @BindView(R.id.image_view_icon) ImageView iconImageView;
        @BindView(R.id.text_view_title) TextView titleTextView;

        AppListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (delegate != null) {
                delegate.applicationSelected(getAdapterPosition(),
                        dataSource.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (delegate != null) {
                delegate.applicationLongSelected(getAdapterPosition(),
                        dataSource.get(getAdapterPosition()));

                return true;
            }

            return false;
        }
    }
}
