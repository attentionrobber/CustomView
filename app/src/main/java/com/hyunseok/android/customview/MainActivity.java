package com.hyunseok.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout ground;
    Button btn_up, btn_down, btn_left, btn_right;

    CustomView view;

    private static final int GROUND_SIZE = 10;


    int player_X = 0;
    int player_Y = 0;
    int unit = 0; // 이동 단위
    int player_radius = unit / 2;

    boolean isMovable = false;

    // 맵 정보
    Stage stage;
    int stageLevel = 1;
    int map[][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new CustomView(this));

        DisplayMetrics metrics = getResources().getDisplayMetrics(); // 현재 매트릭스를 가져온다.
        unit = metrics.widthPixels / GROUND_SIZE;
        player_radius = unit / 2;



        setWidget();
        setListener();
        init();

        // CustomView 생성 후 레이아웃에 add
        view = new CustomView(this);
        ground.addView(view);
    }

    private void init() {
        stage = new Stage();
        map = stage.getStage(stageLevel);
        player_X = 0;
        player_Y = 0;
    }

    private void setWidget() {
        ground = (FrameLayout) findViewById(R.id.ground);
        btn_up = (Button) findViewById(R.id.btn_up);
        btn_down = (Button) findViewById(R.id.btn_down);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
    }

    private void setListener() {
        btn_up.setOnClickListener(this);
        btn_down.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String direction = "";

        switch (v.getId()) {
            case R.id.btn_up:
//                if(player_Y > 0 && !collisionCheck("up"))
//                    player_Y = player_Y - 1;
                if(player_Y > 0)
                    player_Y = player_Y - 1;


                direction = checkBlock();
                Toast.makeText(this, ""+direction, Toast.LENGTH_SHORT).show();
//                if(checkBlock().equals("up")) {
//                    Toast.makeText(this, "if equals", Toast.LENGTH_SHORT).show();
//                    isMovable = true;
//                }
//                if(isMovable == true) {
//                    pushpush("up");
//                    isMovable = false;
//                }
                break;
            case R.id.btn_down:
//                if(player_Y < GROUND_SIZE-1 && !collisionCheck("down"))
//                    player_Y = player_Y + 1;
                if(player_Y < GROUND_SIZE-1)
                    player_Y = player_Y + 1;

                direction = checkBlock();
                Toast.makeText(this, ""+direction, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_left:
//                if(player_X > 0 && !collisionCheck("left"))
//                    player_X = player_X - 1;
                if(player_X > 0)
                    player_X = player_X - 1;

                direction = checkBlock();
                Toast.makeText(this, ""+direction, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_right:
//                if(player_X < GROUND_SIZE-1 && !collisionCheck("right"))
//                    player_X = player_X + 1;
                if(player_X < GROUND_SIZE-1)
                    player_X = player_X + 1;

                direction = checkBlock();
                Toast.makeText(this, ""+direction, Toast.LENGTH_SHORT).show();

                break;
        }
        view.invalidate(); // 화면을 다시 그려준다. 화면을 지운 후에 onDraw를 호출한다.
    }

    private boolean collisionCheck(String direction) {

        //if( (player_X > 0) && (player_Y > 0) && (player_X < GROUND_SIZE) && (player_Y < GROUND_SIZE) ) { // x,y좌표가 0보다 크고 SIZE보다 작을 때만 경우에만 검사
            if (direction.equals("up")) {
                if (map[player_Y - 1][player_X] == 1) {
                    Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
                    return true;
                }
            } else if (direction.equals("down")) {
                if (map[player_Y + 1][player_X] == 1) {
                    Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();
                    return true;
                }
            } else if (direction.equals("left")) {
                if (map[player_Y][player_X - 1] == 1) {
                    Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
                    return true;
                }
            } else if (direction.equals("right")) {
                if (map[player_Y][player_X + 1] == 1) {
                    Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        //}
        return false;
    }

    private String checkBlock() {

        boolean isX_zero = false;
        boolean isY_zero = false;
        boolean isX_gs = false;
        boolean isY_gs = false;

        // x,y좌표가 0보다 크고 SIZE보다 작을 때만 경우에만 검사
        if( player_X == 0 ) { // player가 왼쪽에 붙어있을 경우
            isX_zero = true;
        }
        if (player_Y == 0) { // player가 위쪽에 붙어있을 경우
            isY_zero = true;
        }
        if (player_X == GROUND_SIZE) { // player가 오른쪽에 붙어있을 경우
            isX_gs = true;
        }
        if (player_Y == GROUND_SIZE) { // player가 바닥에 붙어있을 경우
            isY_gs = true;
        }





        if (map[player_Y - 1][player_X] == 1) {
            Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
            return "up";
        }
        if (map[player_Y + 1][player_X] == 1) {
            Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();
            return "down";
        }
        if (map[player_Y][player_X - 1] == 1) {
            Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
            return "left";
        }
        if (map[player_Y][player_X + 1] == 1) {
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
            return "right";
        }

        return null;
    }

    private void pushpush(String direction) {
        //if( (player_X > 0) && (player_Y > 0) && (player_X < GROUND_SIZE) && (player_Y < GROUND_SIZE) ) { // x,y좌표가 0보다 크고 SIZE보다 작을 때만 경우에만 검사

        if (direction.equals("up")) {
            Toast.makeText(this, "pushup", Toast.LENGTH_SHORT).show();
            map[player_Y][player_X] = 0;
            map[player_Y - 1][player_X] = 1;

        }
        if (direction.equals("down")) {
            Toast.makeText(this, "pushdown", Toast.LENGTH_SHORT).show();
            map[player_Y][player_X] = 0;
            map[player_Y - 1][player_X] = 1;
        }
        if (direction.equals("left")) {
            Toast.makeText(this, "pushleft", Toast.LENGTH_SHORT).show();
            map[player_Y][player_X] = 0;
            map[player_Y][player_X - 1] = 1;
        }
        if (direction.equals("right")) {
            Toast.makeText(this, "pushright", Toast.LENGTH_SHORT).show();
            map[player_Y][player_X] = 0;
            map[player_Y][player_X + 1] = 1;
        }
        //}
    }


    class CustomView extends View {

        Paint yellow = new Paint();
        Paint black = new Paint();

        public CustomView(Context context) {
            super(context);
            yellow.setColor(Color.YELLOW);
            black.setColor(Color.BLACK);
        }

        // 처음 한번은 실행된다.
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // onDraw 함수에서 그림그리기
            // canvas에 그림그리기(pixel단위. dp단위가 아니다)

            // 플레이어를 화면에 그린다.
            canvas.drawCircle(player_X * unit + player_radius, player_Y * unit + player_radius, player_radius, yellow);

            // 맵을 화면에 그린다.
            for(int i = 0; i<map.length; i++) {
                for(int j = 0; j <map[0].length; j++) {
                    if (map[i][j] != 0) {
                        canvas.drawRect( unit*j, unit*i, unit*j + unit, unit*i + unit, black );
                    }
                }
            }
        }
    }
}