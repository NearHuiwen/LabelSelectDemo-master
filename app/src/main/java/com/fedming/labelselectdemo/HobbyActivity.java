package com.fedming.labelselectdemo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class HobbyActivity extends AppCompatActivity implements View.OnClickListener{

    private TagFlowLayout mFlowLayout;
    private TagFlowLayout addedLabelFlowlayout;
    private EditText labelEditText;
    private Button labelAddButton;
    private ArrayList<String> labelArrayList;
    private ArrayList<String> addedLabelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        addLabels();
        initView();
    }

    private void addLabels() {
        labelArrayList = new ArrayList<>();
        addedLabelArrayList = new ArrayList<>();
        labelArrayList.add("美食");
        labelArrayList.add("轻运动");
        labelArrayList.add("旅行");
        labelArrayList.add("金属音乐");
        labelArrayList.add("二次元");
        labelArrayList.add("轻运动");
        labelArrayList.add("金属音乐");
        labelArrayList.add("R&B");
        labelArrayList.add("篮球");
        labelArrayList.add("电影");
        labelArrayList.add("吃吃吃");
    }

    private void initView() {

        labelEditText = (EditText) findViewById(R.id.add_label_editText);
        labelAddButton = (Button) findViewById(R.id.add_label_button);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        addedLabelFlowlayout = (TagFlowLayout) findViewById(R.id.added_label_flowlayout);
        setAdapter();

        labelAddButton.setOnClickListener(this);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (addedLabelArrayList.size() < 5) {
                    addedLabelArrayList.add(labelArrayList.get(position));
                    labelArrayList.remove(position);
                    setAdapter();
                } else {
                    Toast.makeText(getApplicationContext(), "最多只能添加5个标签~", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        addedLabelFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (!addedLabelArrayList.isEmpty()) {
                    labelArrayList.add(addedLabelArrayList.get(position));
                    addedLabelArrayList.remove(position);
                    setAdapter();
                }

                return true;
            }
        });
    }

    private void setAdapter() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout.setAdapter(new TagAdapter<String>(labelArrayList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.interest_label_textview,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        addedLabelFlowlayout.setAdapter(new TagAdapter<String>(addedLabelArrayList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.interest_label_added_textview,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_label_button:
                if (TextUtils.isEmpty(labelEditText.getText())){
                    Toast.makeText(getApplication(), "请先输入标签~", Toast.LENGTH_SHORT).show();
                }else if (addedLabelArrayList.size() < 5) {
                    addedLabelArrayList.add(labelEditText.getText().toString());
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                    labelEditText.setText(null);
                    setAdapter();
                }else {
                    Toast.makeText(this, "最多只能添加5个标签~", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard(getApplication());
                }
                break;
        }

    }
    private  void hideSoftKeyboard(Context context) {

        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context)
                    .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception e) {
            Log.e("InputMethodManager", "hideSoftKeyboard Catch error, skip it!", e);
        }
    }
}
