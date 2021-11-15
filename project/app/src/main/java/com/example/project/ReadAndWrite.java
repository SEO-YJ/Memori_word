package com.example.project;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadAndWrite {

    private DatabaseReference rootDatabase;
    private DatabaseReference userDatabase;
    private DatabaseReference listDatabase;
    ArrayList<String> nameList;
    ArrayList<String> meanList;
    ArrayList<String> spellingList;


    // userID를 받아 그에 해당하는 위치에 userDatabase를 설정해둔다.
    // userDatabase를 만들며 그 하위 데이터를 포함한 리스트가 업데이트 될 때마다 호출되는 리스너를 달아준다.
    public ReadAndWrite(String userID, ArrayList<String> nameList, ArrayList<String> meanList, ArrayList<String> spellingList){
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase = rootDatabase.child(userID);
        this.nameList = nameList;
        this.meanList = meanList;
        this.spellingList = spellingList;

        addWordEventListener(userDatabase);
    }


    // 새롭게 단어를 받을 때 사용, 기본적으로 spelling을 리스트의 key로 삼아 저장
    public void writeNewWord(String listName, String mean, String spelling){
        userDatabase.child(listName).child(spelling).setValue(mean);
    }
    // 리스트를 선택해 해당 리스트를 가리키는 DB참조를 받는 listDatabase를 설정할 때 사용
    public void listSelcet(String listName){
        listDatabase = userDatabase.child(listName);
        addWordEventListener(listDatabase);
    }
    // 리스트를 생성하는 함수... 리스트만 생성한다.
    public void writeNewList(String listName){
        userDatabase.child(listName).setValue("");
    }

    // 기본적으로 spelling을 리스트의 key로 삼아 저장했기에, spelling을 받아 삭제
    public void deleteWord(String listName, String mean, String spelling){
        userDatabase.child(listName).child(spelling).removeValue();
    }

    public void updateWord(String listName, String mean, String spelling){
        if(userDatabase.child(listName).child(spelling).getKey().equals(spelling)){
            deleteWord(listName, mean, spelling);
        }
        writeNewWord(listName, mean, spelling);

    }

    // 전달받은 DB 참조를 통해 그 하위 데이터를 포함 한 리스트가 업데이트될 때마다 onDataChange메소드가 호출...
    // 전달받은 인자에 Listener를 달아주는 함수
    // userID 레벨의 참조가 들어오면 nameList의 수정을 목표로...
    // 리스트 이름 레벨의 참조가 들어오면 각 단어 데이터의 수정을 목표로...
    public void addWordEventListener(DatabaseReference baseRef){
        ValueEventListener baseListener = new ValueEventListener() {

            // 내부의 변화가 있으면 호출 되는 함수
            //
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(baseRef.equals(userDatabase)){
                    nameList.clear();
                    for(DataSnapshot userSnapshot : snapshot.getChildren())
                        nameList.add(userSnapshot.getKey());
                }
                else{
                    meanList.clear();
                    spellingList.clear();
                    for(DataSnapshot listSnapshot : snapshot.getChildren()){
                        spellingList.add(listSnapshot.getKey());
                        meanList.add(listSnapshot.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        baseRef.addValueEventListener(baseListener);
    }

    public void getFirstListListener(){
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameList.clear();
                for(DataSnapshot listSnapshot : snapshot.getChildren())
                    nameList.add(listSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
