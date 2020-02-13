package com.gaurav.robotics_society.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gaurav.robotics_society.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

/**
 * Created by GAURAV on 06-02-2019.
 */

public class forget extends AppCompatActivity {

    ConstraintLayout root1;
    EditText email_text;
    Button rest_button;
    FirebaseAuth mAuth;
    String email;
    Snackbar snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass);

        email_text = findViewById(R.id.forget_email);
        root1 = findViewById(R.id.forget_xml);

        rest_button = findViewById(R.id.forget_button);
        mAuth = FirebaseAuth.getInstance();

        rest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = email_text.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    email_text.setError("This field is required");
                    View focusView = email_text;
                    focusView.requestFocus();
                } else {
                    check(email);
                }
            }
        });
    }

    private Boolean check(final String email) {
        final Boolean[] checke = new Boolean[1];
        mAuth.fetchProvidersForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(Task<ProviderQueryResult> task) {
                        checke[0] = !task.getResult().getProviders().isEmpty();
                        if (checke[0]) {
                            mAuth.sendPasswordResetEmail(email);
                            snack = Snackbar
                                    .make(root1, "Reset email sent!", Snackbar.LENGTH_LONG)
                                    .setAction("Retry login", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            snack.dismiss();
                                        }
                                    });
                            snack.setActionTextColor(Color.WHITE);
                            View sbView = snack.getView();
                            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snack.show();
                        } else {
                            snack = Snackbar
                                    .make(root1, "Email not found!", Snackbar.LENGTH_LONG)
                                    .setAction("Retry!", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            snack.dismiss();
                                        }
                                    });
                            snack.setActionTextColor(Color.WHITE);
                            View sbView = snack.getView();
                            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snack.show();
                        }
                    }
                });
        email_text.getText().clear();
        return checke[0];
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
