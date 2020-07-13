package com.example.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class AccountFragment extends Fragment {
    Button logout;
    TextView email,name;
    FirebaseAuth fAuth;
    String data_name,data_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_account,null);
        email = v.findViewById(R.id.email);
        name = v.findViewById(R.id.name);
        logout = v.findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            data_name = getArguments().getString("name");
            data_email = getArguments().getString("email");

        }

        name.setText(data_name);
        email.setText(data_email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fAuth.signOut();
            GoogleSignIn.getClient(Objects.requireNonNull(getContext()),new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut();
            startActivity(new Intent(getContext(),Login.class));


                if(getActivity() != null) {
                        getActivity().finish();


                    }

            }
        });


        return v;
    }
}
