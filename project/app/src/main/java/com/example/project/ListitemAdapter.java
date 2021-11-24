package com.example.project;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListitemAdapter extends ArrayAdapter<Listitem>{
    String userID;
    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;
    ReadAndWrite DBHelper;

    Button showListBtn;
    Button quizPageBtn;

    Context context;
    int resID;
    ArrayList<Listitem> items;

    public ListitemAdapter(Context context, int resID, ArrayList<Listitem> items, String userID,
    ArrayList<String> nameList, ArrayList<String> meanList, ArrayList<String> spellingList){
        super(context, resID);
        this.context = context;
        this.resID = resID;
        this.items = items;
        this.userID = userID;

        this.nameList = nameList;
        this.meanList = meanList;
        this.spellingList = spellingList;
        this.userID = userID;

        DBHelper = new ReadAndWrite(userID, nameList, meanList, spellingList);
    }

    //ArrayList크기 리턴, 리스트뷰에 생성될 아이템 수
    @Override
    public int getCount() {
        return items.size();
    }

//    //해당 position의 아이템을 리턴, 아이템 클릭시 아이템이 몇번째인지
////    @Override
////    public Object getItem(int position) {
////        return items.get(position);
////    }
//
    //position리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
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
        showListBtn = holder.showListBtn;
        quizPageBtn = holder.quizPageBtn;

        Listitem item = items.get(position);

        listNameText.setText(item.getListname());
        listSizeText.setText(item.getListsize());

        quizPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("showListBtn", "클릭은 됨?");
                Thread holderThread = new Thread("holderThread"){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            // 이 부분 좀 이상함... listNameText가 내가 클릭 한 쪽의 것을 알아서 가져오긴 해서 쓰긴 했음..
                            Log.d(listNameText.getText().toString(), "호출..");
                            String listName = listNameText.getText().toString();
                            DBHelper.addWordEventListener(DBHelper.userDatabase.child(listName));
                            DBHelper.getFirstListListener(listName);

                            Thread.sleep(100);

                            Intent intent = new Intent(context, QuizPage.class);
                            intent.putExtra("list name", listName);
                            intent.putExtra("meanList", meanList);
                            intent.putExtra("spelling", spellingList);
                            intent.putExtra("UID", userID);

                            context.startActivity(intent);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                holderThread.start();
            }
        });


        showListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("showListBtn", "클릭은 됨?");
                Thread holderThread = new Thread("holderThread"){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            // 이 부분 좀 이상함... listNameText가 내가 클릭 한 쪽의 것을 알아서 가져오긴 해서 쓰긴 했음..
                            Log.d(listNameText.getText().toString(), "호출..");
                            String listName = listNameText.getText().toString();
                            DBHelper.addWordEventListener(DBHelper.userDatabase.child(listName));
                            DBHelper.getFirstListListener(listName);

                            Thread.sleep(100);

                            Intent intent = new Intent(context, MeanAndSpellingPage.class);
                            intent.putExtra("list name", listName);
                            intent.putExtra("meanList", meanList);
                            intent.putExtra("spelling", spellingList);
                            intent.putExtra("UID", userID);

                            context.startActivity(intent);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                holderThread.start();
            }
        });

        return convertView;
    }

}
