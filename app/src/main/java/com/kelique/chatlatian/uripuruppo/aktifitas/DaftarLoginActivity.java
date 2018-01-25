package com.kelique.chatlatian.uripuruppo.aktifitas;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.helper.MyFunction;
import com.kelique.chatlatian.uripuruppo.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DaftarLoginActivity extends MyFunction implements OnClickListener, OnKeyListener,
        GoogleApiClient.OnConnectionFailedListener{
    //utk pilihan mau masuk atau mau daftar
    boolean sgnModeActive = true;

    TextView changeSignupModeTextView;

    @BindView(R.id.btnEnter)
    Button mBtnEnter;
    @BindView(R.id.logoImageView)
    ImageView mLogoImageView;
    @BindView(R.id.cngSignupModeTV)
    TextView mCngSignupModeTV;

    @BindView(R.id.backgrounRelativeLayout)
    RelativeLayout mBackgrounRelativeLayout;
    @BindView(R.id.btnGoog)
    Button mBtnGoog;
    @BindView(R.id.edtPass)
    EditText mEdtPass;
    @BindView(R.id.edtUser)
    EditText mEdtUser;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private GoogleApiClient mGoogleApiClient;

    private static final String TAG = "GoogleActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_login);
        ButterKnife.bind(this);

        setTitle("Masuk/Daftar");

        changeSignupModeTextView = (TextView) findViewById(R.id.cngSignupModeTV);
        changeSignupModeTextView.setOnClickListener(this);

        RelativeLayout backgrounRelativeLy = (RelativeLayout) findViewById(R.id.backgrounRelativeLayout);
        ImageView logoImgView = (ImageView) findViewById(R.id.logoImageView);


        backgrounRelativeLy.setOnClickListener(this);
        logoImgView.setOnClickListener(this);

        mEdtUser = (EditText) findViewById(R.id.edtUser);
        mEdtPass.setOnKeyListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //inisialisasi auth
        mAuth = FirebaseAuth.getInstance();
        //auth state listener
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(DaftarLoginActivity.this, "Anda Berhasil Masuk", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(DaftarLoginActivity.this, "Gagal Masuk!!!", Toast.LENGTH_SHORT).show();
                }

            }
        };


    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            untukDaftar(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cngSignupModeTV) {
            Button signupBtn = (Button) findViewById(R.id.btnEnter);
            if (sgnModeActive) {
                sgnModeActive = false;
                signupBtn.setText("MASUK");
                changeSignupModeTextView.setText("Mau: DAFTAR");
            } else {
                sgnModeActive = true;
                signupBtn.setText("DAFTAR");
                changeSignupModeTextView.setText("Mau: MASUK");
            }


        } else if (view.getId() == R.id.backgrounRelativeLayout || view.getId() == R.id.logoImageView) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        //
    }

    public void untukDaftar(View view) {

        //EditText usrname = (EditText) findViewById(R.id.edtPass);
        String email = mEdtUser.getText().toString();
        String passone = mEdtPass.getText().toString();

        if (mEdtUser.getText().toString().matches("") || mEdtPass.getText().toString().matches("")) {
            Toast.makeText(this, "Harap isi kolom user dan password", Toast.LENGTH_SHORT).show();
        } else {
            if (sgnModeActive) {
                mAuth.createUserWithEmailAndPassword(email, passone)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("tanggapan", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Pendaftaran Gagal",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Pendaftaran berhasil",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            } else {
                mAuth.signInWithEmailAndPassword(email, passone)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Tanggapan", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("tanggapan", "Aktivitas Masuk gagal", task.getException());
                                    Toast.makeText(getApplicationContext(), "Gagal Masuk",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Berhasil Masuk",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }

        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Koneksi gagal"+ connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }
    }

    @OnClick(R.id.btnGoog)
    public void onViewClicked() {
        //myIntent(LoginGoogleActivity.class);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        SessionManager sesi = new SessionManager(c);
        //sesi.cerateLoginSession("9");
        sesi.setNama(acct.getPhotoUrl().toString());
        sesi.setEmail(acct.getDisplayName().toString());
        sesi.setAlamat(acct.getEmail().toString());


        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog("Memuat informasi");
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(DaftarLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(new Intent(DaftarLoginActivity.this, MainActivity.class));
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

}
