package com.example.project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListitemAdapter extends ArrayAdapter<Listitem> {
    String userID;
    Context context;
    int resID;
    ArrayList<Listitem> items;

    public ListitemAdapter(Context context, int resID, ArrayList<Listitem> items, String userID){
        super(context, resID);
        this.context = context;
        this.resID = resID;
        this.items = items;
        this.userID = userID;
    }

//    //ArrayList크기 리턴, 리스트뷰에 생성될 아이템 수
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    //해당 position의 아이템을 리턴, 아이템 클릭시 아이템이 몇번째인지
////    @Override
////    public Object getItem(int position) {
////        return items.get(position);
////    }
//
//    //position리턴
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    //아이템 추가
//    public void addItem(Listitem item){
//        items.add(item);
//    }

    //이 메서드가 리스트를 어떻게 보여줄 것인가..
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resID, null);
            ListHolder holder = new ListHolder(convertView);
            convertView.setTag(holder);
        }

        ListHolder holder = (ListHolder) convertView.getTag();

        TextView listNameText = holder.listNameText;
        TextView listSizeText = holder.listSizeText;
        Button showListBtn = holder.showListBtn;
        Button quizPageBtn = holder.quizPageBtn;

        Listitem item = items.get(position);

        listNameText.setText(item.getListname());
        listSizeText.setText(item.getListsize());

       //quizPageBtn.setOnClickListener();

        showListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListHolder holder = (ListHolder) view.getTag();
                String listName = holder.listNameText.getText().toString();
                ReadAndWrite DBHelper = new ReadAndWrite(userID, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());



            }
        });

        return convertView;
    }

}
