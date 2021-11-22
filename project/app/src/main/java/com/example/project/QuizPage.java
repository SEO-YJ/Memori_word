package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

// arraylist 사용 위함
import java.util.ArrayList;


public class QuizPage extends AppCompatActivity implements View.OnClickListener{
    // AppCompatActivity 클래스 상속을 통해 Activity 클래스의 API level 하위 호환 가능

    // 모두 View 클래스의 자식 클래스
    Button button;
    ProgressBar progressBar;
    EditText editText;
    TextView textView;
    ImageView imageView1;
    ImageView imageView2;



    //arraylist 전역 변수 배열로 사용
    // 영어, 한글 저장 리스트
    // 이 리스트를 원본이라고 생각하고 유지
    ArrayList<String> english = new ArrayList<>();
    ArrayList<String> korean = new ArrayList<>();

    // 깊은 복사를 위한 리스트
    ArrayList<String> englishCopy = new ArrayList<>();
    ArrayList<String> koreanCopy = new ArrayList<>();

    // arraylist 인덱스 참조 변수
    int i = 0;


    // 퀴즈 정답, 오답 변수
    public static Context context_correct;
    public static Context context_wrong;
    public static Context context_allQuestion;
    public int correct = 0;
    public int wrong = 0;
    public int allQuestion = 0;
    int[] wrongCheck = {0, 0, 0, 0, 0};


    // onCreate: 현재 MainActivity 클래스가 화면에 출력되기 전에 객체를
//    생성하는 초기화 작업
//    현재 상태의 저장된 인자를 전달.
//    부모 클래스에서도 onCreate 메소드가 선언되어 있으므로,
//    동일한 인자를 전달하여 호출
//    override를 통해 현재 클래스의 객체 커스터마이즈 하여 초기화 작업
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        context_correct = this;
        context_wrong = this;
        context_allQuestion = this;

        // String 만 저장하는 arraylist
        // <string>은 제네릭이라는 뜻으로 들어가는 값의 타입을 하나로 일반화하는 기능
        // 우선, 두 리스트에 뜻과 데이터를 저장




        english.add("lion");
        english.add("tiger");
        english.add("bear");
        english.add("wolf");
        english.add("cow");


        korean.add("사자");
        korean.add("호랑이");
        korean.add("곰");
        korean.add("늑대");
        korean.add("소");

        // 깊은 복사 실행
        // 두 리스트에 영향을 미치지 않는다.
        // 이는 원본 데이터는 유지시킬 수 있다.
        // 이제 복사본 리스트를 이용해 구성해보자.
        englishCopy.addAll(english);
        koreanCopy.addAll(korean);


        // 원본 리스트의 사이즈 값 저장
        allQuestion = englishCopy.size();


        // Resource의 layout 폴더의 activity_main xml 파일 언급
        // .java 파일에서의 리소스 언급 방법
        // R: 클래스 이름, layout: R 클래스라는 이름의 static 클래스 요소
        // activity_main: 그 안에 있는 정수에 접근
        // activity_main 리소스는 직접적인 파일이름으로 접근하지 않고,
        // 결합도를 낮추기 위하여 변수로 접근
        // 자동으로 생성되는 상수의 이름으로 접근하여 결합도록 높인다.
        // layout은 파일 하나가 리소스 하나이다.
        // 리소스 이름을 파일 이름과 대응 시켜준다.
        // setContentView: 현재 클래스의 화면을 R.layout.activity_main 파일로
        // 구성하여 달라는 메소드
        // 인자가 정수, 객체 등 다양하다. (overload)
        setContentView(R.layout.activity_quiz_page);

        // findViewById: setContentView를 통해 객체를 생성했으면,
        // 그 객체를 코드에서 다루기 위하여 객체 획득을 해야한다.
        // findViewById는 객체 획득을 위한 메소드이다.
        //(Activity에서 제공하는 메소드)
        // 지금은 형 변환 할 필요는 없다.
        // View 객체 획득
        progressBar = findViewById(R.id.progressBar);

        editText = findViewById(R.id.editText);

        button = findViewById(R.id.button);

        textView = findViewById(R.id.textView);

        imageView1 = findViewById(R.id.imageView1);

        imageView2 = findViewById(R.id.imageView2);

        // 뜻 암기 실행시
        textView.setText(englishCopy.get(0));




        // editText 자동으로 focusing
        editText.requestFocus();

        // 키보드 보이게 하는 부분
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        // Button 이벤트 등록
        // button 클래스 - setOnClickListener 메소드
        // 이벤트가 반응했을 때, 그에 대한 반응에 대한 객체
        // button을 클릭(이벤트)이 반응했을 때에 대한 리스너
        // 각종 View의 하위 클래스마다 이벤트에 관한 메소드가 준비되어있다.
        // API 문서에 가서 View의 하위 클래스에 정의된 이벤트 관련 리스너들을 찾아
        // 리스너 메소드가 정의되어있으면, 이벤트 처리에 대한 대응 가능
        // 클릭 이벤트 발생시 onClick 메소드가 호출
        // 그러면 다른 하위 클래스에 동일한 setOnClickListener메소드
        // 정의되어 있으면, 다른 동작이 발생하게 어떻게 처리할까?
        // 1. onClick 메소드에서 조건문 달아서 view와의 true, false 비교
        // this type: interface type
        // implements View.onClickListener
        button.setOnClickListener(this);


    }



    // button 위젯 클릭 이벤트 발생 시 이벤트 콜백함수
    public void onClick(View view){


        // editText에 입력한 문자열 저장 변수
        String wordEng = editText.getText().toString();


        // 버튼 누를 때마다 진행바 증가
        // 최종적으로는 단어 입력하여 리스트의 값과 같으면 O
        // 아니면, X를 출력하고 진행바는 문제를 풀때마다 계속 증가


        if(wrongCheck[i] == 1) {
            i++;
        }

        // 클릭 시 다음 텍스트 설정
        // 뜻 암기 기준
        textView.setText(englishCopy.get(i));
        String wordAnswer = koreanCopy.get(i);


        // button이 눌리면 조건문을 통해 맞는 이미지 출력
        if (view == button && wrongCheck[i]==0) {
            if (wordEng.equals(wordAnswer)) {
                // 맞을 시
                // O 이미지 출력
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.GONE);


                correct++;
                wrongCheck[i] = 1;

                // 복사본 리스트 제거
                englishCopy.remove(i);
                koreanCopy.remove(i);


            } else {
                // 틀릴 시
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);

                wrong++;
                wrongCheck[i] = 0;
            }
        }


        // Button 클릭 시 키보드 내리기 위한 변수
        // EditText 포커싱 받을 경우는 키보드 자동으로 올라옴
        InputMethodManager imm2 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);


        i++;



        // 진행바 값 설정
        // progressBar.setProgress(value);
        // arraylist의 배열 크기 이용
        progressBar.setProgress((100/allQuestion)*i);


        // 1초후 실행 시키고 싶을 때
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                // 시간 지난 후 실행할 코딩
                if(i<english.size()) {

                    // 1초후 다시 키보드 보이게 하는 부분
                    InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm3.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    // O, X 이미지 제거
                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.GONE);

                    // 다음 리스트 요소 세팅
                    textView.setText(englishCopy.get(i));
                    editText.setText(""); // 버튼 클릭시 editText 빈칸 처리
                    progressBar.setProgress((100 / allQuestion) * i);
                }

                // 리스트 문제 다 해결시 채점 화면으로 전환
                if(i==english.size()){
                    // 처음 진행바 값 초기화
                    // editText 값 "" 로 초기화
                    progressBar.setProgress(0);
                    editText.setText("");
                    Intent intent = new Intent();
                    intent.putExtra("wrongs", wrongCheck);

                    startActivity(new Intent("com.example.wordrecord.ScoringPage"));
                }
            }
        }, 1000); // 1초후
    }
}