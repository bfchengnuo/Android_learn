package com.bfchengnuo.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathFragment extends Fragment implements View.OnClickListener {
    private TableLayout mTableLayout;
    private List<Stu> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_math, container, false);

        mTableLayout = (TableLayout) view.findViewById(R.id.tl_data);

        view.findViewById(R.id.btn_math).setOnClickListener(this);
        view.findViewById(R.id.btn_addTB).setOnClickListener(this);
        view.findViewById(R.id.btn_tbStor).setOnClickListener(this);

        initData();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_math:
                showMath();
                break;
            case R.id.btn_addTB:
                addTB();
                break;
            case R.id.btn_tbStor:
                ListSort();
                break;
        }
    }

    private void ListSort() {
        if (mList != null && mList.size() != 0) {
            Collections.sort(mList, new Comparator<Stu>() {
                @Override
                public int compare(Stu o1, Stu o2) {
                    return o1.getAge() - o2.getAge();
                }
            });

            addTB();
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new Stu("凝萱", "女", 10));
        mList.add(new Stu("欣妍", "女", 5));
        mList.add(new Stu("诗茵", "女", 11));
        mList.add(new Stu("茹雪", "女", 6));
        mList.add(new Stu("娅楠", "女", 16));
        mList.add(new Stu("沛凝", "女", 8));
        mList.add(new Stu("佳芷", "女", 10));
    }

    private void addTB() {
        if (mTableLayout.getChildCount() != 1) {
            TableRow tr = (TableRow) mTableLayout.getChildAt(0);
            mTableLayout.removeAllViews();
            mTableLayout.addView(tr);
//            mTableLayout.removeViews(1, mTableLayout.getChildCount() - 1);
        }

        mTableLayout.setStretchAllColumns(true);
        for (Stu stu :
                mList) {
            TableRow tr = new TableRow(getContext());
            tr.setBackgroundColor(Color.rgb(0,0,0));

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
            lp.setMargins(2,2,2,2);

            for (int i = 0; i < stu.toStrArray().length; i++) {
                TextView tv = new TextView(getContext());
                tv.setText(stu.toStrArray()[i]);
                tv.setBackgroundColor(Color.rgb(255, 255, 255));
                tv.setLayoutParams(lp);
                tr.addView(tv);
            }

            // View view = new View(getContext());
            mTableLayout.addView(tr, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void showMath() {
        String str = "abc3712jgwo234dn34";

//        boolean is = Pattern.matches("\\d{4}",str);

        Matcher math = Pattern.compile("\\d{4}").matcher(str);
        if (math.find())
            Toast.makeText(getActivity(), math.group(), Toast.LENGTH_SHORT).show();
    }
}
