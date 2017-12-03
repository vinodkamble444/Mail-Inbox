package com.example.userslist.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.userslist.R;
import com.example.userslist.data.User;
import com.example.userslist.helper.FlipAnimator;
import com.example.userslist.sort.SortType;
import com.example.userslist.sort.UserLastActiveComparator;
import com.example.userslist.sort.UserNameComparator;
import com.example.userslist.sort.UserPointComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by vinod on 9/16/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private List<User> mUserList;
    private List<User> mFilteredList;
    private final UsersAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    private static final String TAG = "UserAdapter";

    public UsersAdapter(Context context, List<User> userList, UsersAdapterListener listener) {
        this.mContext = context;
        this.mUserList = userList;
        this.mFilteredList = userList;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        User user = mFilteredList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        if (!TextUtils.isEmpty(user.getPoint())) {
            holder.tvPoint.setVisibility(View.VISIBLE);
            holder.tvPoint.setText(user.getPoint());
        } else {
            holder.tvPoint.setVisibility(View.GONE);
        }
        holder.tvLastActive.setText(user.getLastActive());

        if (user.getUserGroup() != null && user.getUserGroup().size() > 0) {
            holder.tvGroupMore.setVisibility(View.GONE);
            holder.layoutGroup.setVisibility(View.VISIBLE);
            ArrayList<String> groupArrayList = user.getUserGroup();
            for (int i = 0; i < groupArrayList.size(); i++) {
                if (i == 3) {
                    holder.tvGroupMore.setVisibility(View.VISIBLE);
                    break;
                }
                holder.tvGroup[i].setVisibility(View.VISIBLE);
                holder.tvGroup[i].setText(groupArrayList.get(i));
            }
        } else {
            holder.layoutGroup.setVisibility(View.GONE);
        }
        if (user.getLastActive() != null) {
            CharSequence lastActive = convertTimeStampintoAgo(user.getLastActive());
            if (lastActive != null)
                holder.tvLastActive.setText(lastActive);
        }
        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, user);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.tvGroupMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMoreClicked(android.text.TextUtils.join(",", mFilteredList.get(position).getUserGroup()));
            }
        });
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyProfilePicture(MyViewHolder holder, User message) {
        if (!TextUtils.isEmpty(message.getAvatarUrl())) {
            holder.imgProfile.setImageResource(R.mipmap.profile);
        } else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mUserList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User message : mUserList) {
                        if (message.getEmail().toLowerCase().contains(charString)) {
                            filteredList.add(message);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setData(List<User> data) {
        if (mFilteredList != null)
            mFilteredList.clear();
        if (mUserList != null)
            mUserList.clear();
        mUserList = mFilteredList = data;
        notifyDataSetChanged();
    }

    public CharSequence convertTimeStampintoAgo(String serverTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time;
        try {
            time = sdf.parse(serverTime).getTime();
            long now = System.currentTimeMillis();
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void sort(int column, int sortType) {
        switch (column) {
            case SortType.INFO:
                Collections.sort(mUserList, new UserNameComparator(sortType));
                break;
            case SortType.POINT:
                Collections.sort(mUserList, new UserPointComparator(sortType));
                break;
            case SortType.ACTIVE:
                Collections.sort(mUserList, new UserLastActiveComparator(sortType));
                break;
        }
        notifyDataSetChanged();
    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }

    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public interface UsersAdapterListener {
        void onIconClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);

        void onMoreClicked(String groupLabel);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final TextView tvName;
        public final TextView tvEmail;
        public final TextView tvLastActive;
        public final TextView tvPoint;
        public final TextView tvGroupMore;
        public final TextView[] tvGroup = new TextView[3];
        public final ImageView imgProfile;
        public final LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;
        public final LinearLayout layoutGroup;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.txt_name);
            tvEmail = (TextView) view.findViewById(R.id.txt_email);
            tvLastActive = (TextView) view.findViewById(R.id.txt_lastActive);
            tvPoint = (TextView) view.findViewById(R.id.txt_points);
            tvGroup[0] = (TextView) view.findViewById(R.id.txt_group1);
            tvGroup[1] = (TextView) view.findViewById(R.id.txt_group2);
            tvGroup[2] = (TextView) view.findViewById(R.id.txt_group3);
            tvGroupMore = (TextView) view.findViewById(R.id.txt_group4);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            layoutGroup = (LinearLayout) view.findViewById(R.id.layout_group);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


}
