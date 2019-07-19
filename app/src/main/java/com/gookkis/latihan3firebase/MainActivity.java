package com.gookkis.latihan3firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spnProdi;
    EditText etNama, etURLGambar;
    Button btnSimpan;

    ListView listViewMahasiswa;
    List<MahasiswaModel> mahasiswaModelList = new ArrayList<>();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference databaseMahasiswa = database.getReference("mahasiswa");

    @Override
    protected void onStart() {
        super.onStart();
        databaseMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mahasiswaModelList.clear();

                for (DataSnapshot dataMahasiswa : dataSnapshot.getChildren()) {
                    mahasiswaModelList.add(dataMahasiswa.getValue(MahasiswaModel.class));
                }

                MahasiswaAdapter mahasiswaAdapter = new MahasiswaAdapter(mahasiswaModelList, MainActivity.this);
                listViewMahasiswa.setAdapter(mahasiswaAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnProdi = findViewById(R.id.spn_prodi);
        etNama = findViewById(R.id.et_nama);
        etURLGambar = findViewById(R.id.et_url_gambar);
        btnSimpan = findViewById(R.id.btn_simpan);
        listViewMahasiswa = findViewById(R.id.lv_mahasiswa);

        listViewMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MahasiswaModel mahasiswaModel = mahasiswaModelList.get(position);
                Bantuan.idMahasiswa = mahasiswaModel.getId();
                Bantuan.namaMahasiswa = mahasiswaModel.getNama();
                Intent intent = new Intent(MainActivity.this, NilaiMahasiswaActivity.class);
                //intent.putExtra("mahasiswa", mahasiswaModelList.get(position));
                startActivity(intent);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etNama.getText().toString().isEmpty() && !etURLGambar.getText().toString().isEmpty()) {
                    String id = databaseMahasiswa.push().getKey();

                    MahasiswaModel mahasiswaModel = new MahasiswaModel(id, etNama.getText().toString(), spnProdi.getSelectedItem().toString(),
                            etURLGambar.getText().toString());
                    databaseMahasiswa.child(id).setValue(mahasiswaModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Berhasil tersimpan!", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Gagal Tersimpan! " + e.getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Form tidak boleh kosong!", Toast.LENGTH_LONG).show();
                }


            }
        });

        /*databaseMahasiswa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataMahasiswa: dataSnapshot.getChildren()){
                    Log.d("Mahasiswa", dataMahasiswa.getValue(MahasiswaModel.class).getNama());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }
}
