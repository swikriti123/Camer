package com.swikriti.camer;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swikriti.camer.api.UserAPI;
import com.swikriti.camer.model.User;
import com.swikriti.camer.serverresponse.SignUpResponse;
import com.swikriti.camer.url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirst, etLast, etUsername, etPassword, etConfirmPassword;
    private Button btnSignUp;
    ImageView imgImage;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFirst = findViewById(R.id.etFirst);
        etLast = findViewById(R.id.etLast);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgImage = findViewById(R.id.imgImage);
        imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

                if (etPassword.getText().toString().equals((etPassword.getText().toString()))) {
//                    saveImageOnly();
                    signup();
                } else {
                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }


            }
        });

    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show();

            }
        }
        Uri uri = data.getData();
        imgImage.setImageURI(uri);


    }


    private void signup() {

        String firstname = etFirst.getText().toString();
        String lastname = etLast.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();





        User user = new User();
        UserAPI userapi = Url.getInstance().create(UserAPI.class);

        Call<SignUpResponse> signupResponseCall = userapi.registerUser(user);
        signupResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response< SignUpResponse > response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}

