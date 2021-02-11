package com.example.coronanews;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;


public class WelcomeActivity extends AppCompatActivity {



    FirebaseAuth firebaseAuth;
    ConstraintLayout relativeLayout;
    ImageView bgapp, clover;
    LinearLayout textsplash;
    Animation frombottom;




    boolean connected;



    public  boolean checkConnectivity(){


        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

            return true;

        }
        else {


            connected = false;
            return false;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        relativeLayout = findViewById(R.id.relativeLayout);

        checkConnectivity();




        firebaseAuth = FirebaseAuth.getInstance();









        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = findViewById(R.id.bgapp);
        clover = findViewById(R.id.clover);
        textsplash =  findViewById(R.id.textsplash);


        bgapp.post(new Runnable() {
            @Override
            public void run() {


                if (!checkConnectivity()) {

                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_window_internet, null);

                    // create the popup window
                    int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = false; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    Button btnCancel = popupView.findViewById(R.id.btnOkPopup);

                    popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);


                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           if(checkConnectivity())
                           {
                               popupWindow.dismiss();


                               if(firebaseAuth.getCurrentUser()==null)
                               {

                                   Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                                   startActivity(intent);
                                   finish();


                               }

                               else
                               {

                                   Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                                   startActivity(intent);
                                   finish();



                               }
                           }





                        }
                    });


                }
                else
                {


                    new Handler().postDelayed(new Runnable() {


                        @Override
                        public void run() {



                            if(firebaseAuth.getCurrentUser()==null)
                            {

                                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();


                            }

                            else
                            {

                                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();



                            }




                            // This method will be executed once the timer is over

                        }
                    }, 2600);




                }



            }
        });




        bgapp.animate().translationY(-2900).setDuration(3200).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        //  textsplash.animate().translationY(140).alpha(0).setDuration(2800).setStartDelay(300);


        Window window = getWindow();




        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorSplash));






    }
}