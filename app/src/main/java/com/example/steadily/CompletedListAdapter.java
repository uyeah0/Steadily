package com.example.steadily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/*전체목록 루틴 리스트 연결*/
public class CompletedListAdapter extends BaseAdapter {
    private List<String> mRoutines;

    public CompletedListAdapter(List<String> routines) {
        this.mRoutines = routines;
    }

    @Override
    public int getCount() {
        return mRoutines.size();
    }

    @Override
    public Object getItem(int position) {
        return mRoutines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        /*만약 리스트가 하나도 없다면*/
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.completed_list_item, parent, false);
        }

        TextView routineTitle = convertView.findViewById(R.id.tv_routine_title);
        ImageButton modifyButton = convertView.findViewById(R.id.imgBtn_completedList_modify);

        /*루틴에 타이틀 넣기*/
        routineTitle.setText(mRoutines.get(position));

        return convertView;
    }
}
