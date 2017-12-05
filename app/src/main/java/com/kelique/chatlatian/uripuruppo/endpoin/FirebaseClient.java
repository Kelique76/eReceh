package com.kelique.chatlatian.uripuruppo.endpoin;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kelique.chatlatian.uripuruppo.adapter.CustomAdapter;
import com.kelique.chatlatian.uripuruppo.modeling.Pedagang;

import java.util.ArrayList;

/**
 * Created by kelique on 10/26/2017.
 */

public class FirebaseClient {
    Firebase firebase;
    ArrayList<Pedagang> bakulArrayList = new ArrayList<>();
    CustomAdapter customAdapter;
    Context c;
    String DB_URL;
    ListView listView;
    String userid;

    public FirebaseClient(Context c, String DB_URL, ListView listView) {
        this.c = c;
        this.DB_URL = DB_URL;
        this.listView = listView;
        Firebase.setAndroidContext(c);
        firebase = new Firebase(DB_URL);
    }

//    public void savedata(String photoUrl, String name, String alamat, String modal, String bghasil, String periode) {
//        Pedagang pdg = new Pedagang();
//        pdg.setName(name);
//        pdg.setAlamat(alamat);
//        pdg.setModal(modal);
//        pdg.setBghasil(bghasil);
//        pdg.setPeriode(periode);
//        pdg.setPhotoUrl(photoUrl);
//        firebase.child("daftar_pedagang").push().setValue(pdg);
//    }


    public void refreshdata() {
        firebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUpdates(final com.firebase.client.DataSnapshot dataSnapshot) {
        bakulArrayList.clear();
        for (com.firebase.client.DataSnapshot ds : dataSnapshot.getChildren()) {
            Pedagang pedagang = new Pedagang();

            pedagang.setName(ds.getValue(Pedagang.class).getName());
            pedagang.setAlamat(ds.getValue(Pedagang.class).getAlamat());
            pedagang.setPeriode(ds.getValue(Pedagang.class).getPeriode());
            pedagang.setModal(ds.getValue(Pedagang.class).getModal());

            bakulArrayList.add(pedagang);

        }
        if (bakulArrayList.size() > 0) {

            //Log.d("arraynando", bakulArrayList.get(0).getKey());
            customAdapter = new CustomAdapter(c, bakulArrayList);

            listView.setAdapter((ListAdapter) customAdapter);


//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    final Dialog dialog = new Dialog(c);
//                    dialog.setContentView(R.layout.update_bakul);
//                    dialog.setTitle("Data Pedagang");
//                    dialog.setCancelable(true);
//                    dialog.setCanceledOnTouchOutside(false);
//                    //inisialisasi
//                    TextView txtNama = (TextView) dialog.findViewById(R.id.txtNamaPdg);
//                    TextView txtModal = (TextView) dialog.findViewById(R.id.txtPermodalan);
//                    TextView txtBagiHsl = (TextView) dialog.findViewById(R.id.txtBagiHsl);
//                    TextView txtPeriode = (TextView) dialog.findViewById(R.id.txtBagiHsl);
//                    ImageView img = (ImageView) dialog.findViewById(R.id.imgDiri);
//
//                    Button btnupdate = (Button) dialog.findViewById(R.id.btnupdate);
//                    //Button btndelete = (Button) dialog.findViewById(R.id.btndelete);
//
//                    final String nama = bakulArrayList.get(position).getName();
//                    final String modal = bakulArrayList.get(position).getModal();
//                    final String bagihsl = bakulArrayList.get(position).getBghasil();
//                    final String prd = bakulArrayList.get(position).getPeriode();
//                    //final String url = bakulArrayList.get(position).getPhotoUrl();
//
//                    txtNama.setText(nama);
//                    txtModal.setText(modal);
//                    txtBagiHsl.setText(bagihsl);
//                    txtPeriode.setText(prd);
//                    //img.setImageBitmap(url);
//
//                    txtNama.setEnabled(true);
//                    txtModal.setEnabled(true);
//                    txtBagiHsl.setEnabled(true);
//                    txtPeriode.setEnabled(false);
//
//
//                    btnupdate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            updateBakul(nama, modal, bagihsl, prd);
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                }
//            });
//        } else {
//            Toast.makeText(c, "no data", Toast.LENGTH_SHORT).show();
//        }
        }

    }

//    public void updateBakul( String name, String modal, String bghasil, String periode) {
//        Pedagang pedagang = new Pedagang();
//        pedagang.setName(name);
//        pedagang.setModal(modal);
//        pedagang.setBghasil(bghasil);
//        pedagang.setPeriode(periode);
//        //pedagang.setPhotoUrl(photoUrl);
//        //firebase.child("daftar_pedagang").child(key).setValue(pedagang);
//    }

//    private void deletedata(String userid) {
//        firebase.child("daftar_pedagang").child(userid).removeValue();
//    }

    public void update(String photoUrl, String name, String alamat, String modal, String bghasil, String periode) {
        Pedagang bakul = new Pedagang(alamat, modal, bghasil, periode, name, photoUrl);
        firebase.child("daftar_pedagang").child(periode).setValue(bakul);


    }

    public ArrayList<Pedagang> dataArrayList(){
            return bakulArrayList;
    }

}
