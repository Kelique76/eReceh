package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.databis.TransaksiPedagangActivity;
import com.kelique.chatlatian.uripuruppo.helper.SessionManager;
import com.kelique.chatlatian.uripuruppo.pesanpesan.PesanActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener {


    @BindView(R.id.btnTambahpsrt)
    Button mBtnTambahpsrt;
    @BindView(R.id.btnEcash)
    Button mBtnEcash;
    @BindView(R.id.btnLiatpserta)
    Button mBtnLiatpserta;
    @BindView(R.id.btnBukatbank)
    Button mBtnBukatbank;
    @BindView(R.id.btnObrol)
    Button mBtnObrol;
    @BindView(R.id.btnUpload)
    Button mBtnUpload;
    private FirebaseAuth mAuth;
    //private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tambah Data Pedagang", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, TambahAktifity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        final SessionManager sesi = new SessionManager(MainActivity.this);
        Log.d("ee ", sesi.getNama());
        View v = navigationView.getHeaderView(0);

        ImageView img =  v.findViewById(R.id.imageView);
        TextView txtvna = v.findViewById(R.id.textViewNamaAgen);

        txtvna.setText(sesi.getEmail());
        Picasso.with(this).load(sesi.getNama()).into(img);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this, DaftarLoginActivity.class));
           Toast.makeText(this, "Anda Sudah Keluar", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_foto_settings) {
            //startActivity(new Intent(MainActivity.this, UploadFotoTokoActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Tbhanggota) {
            startActivity(new Intent(MainActivity.this, TambahAktifity.class));
            // Handle the camera action
        } else if (id == R.id.nav_Lhtanggota) {
            startActivity(new Intent(MainActivity.this, TransaksiPedagangActivity.class));
        } else if (id == R.id.nav_eCash) {
            startActivity(new Intent(MainActivity.this, WebEcashActivity.class));
        } else if (id == R.id.nav_Tbank) {
            Intent linkWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://agen.bri.co.id/agent/index.php/auth"));
            startActivity(linkWeb);
        } else if (id == R.id.nav_telPust) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:087875297666"));
            startActivity(intent);
        } else if (id == R.id.nav_Pesan) {
            startActivity(new Intent(MainActivity.this, PesanActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @OnClick({R.id.btnTambahpsrt, R.id.btnEcash, R.id.btnLiatpserta, R.id.btnBukatbank, R.id.btnObrol, R.id.btnUpload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTambahpsrt:
                startActivity(new Intent(MainActivity.this, TambahAktifity.class));
                break;
            case R.id.btnEcash:
                startActivity(new Intent(MainActivity.this, WebEcashActivity.class));
                break;
            case R.id.btnLiatpserta:
                startActivity(new Intent(MainActivity.this, TransaksiPedagangActivity.class));
                break;
            case R.id.btnBukatbank:
                Intent linkWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://agen.bri.co.id/agent/index.php/auth"));
                startActivity(linkWeb);
                break;
            case R.id.btnObrol:
                startActivity(new Intent(MainActivity.this, PesanActivity.class));
                break;
            case R.id.btnUpload:
                startActivity(new Intent(MainActivity.this, KirimFotoActivity.class));
                break;
        }
    }

}
