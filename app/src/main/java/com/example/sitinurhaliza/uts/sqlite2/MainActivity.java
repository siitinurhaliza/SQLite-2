package com.example.sitinurhaliza.uts.sqlite2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvMahasiswa;
    FloatingActionButton fabTambah;
    DatabaseHandler databaseHandler;

    MahasiswaAdapter adapter;
    List<Mahasiswa> mahasiswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMahasiswa = findViewById(R.id.lv_favorit);
        fabTambah = findViewById(R.id.fab_Tambah);
        databaseHandler = new DatabaseHandler(this);

        fabTambah.setOnClickListener(v -> bukaDialogTambah());

        tampilkanSemuaData();
        lvMahasiswa.setOnItemClickListener((parent, view, position, id) -> {
            Mahasiswa mahasiswa = mahasiswaList.get(position);
            bukaDialogUpdateData(mahasiswa);
        });
    }

    private void bukaDialogUpdateData(Mahasiswa mahasiswa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update atau Hapus Data Mahasiswa");
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.from_update, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.et_nama);
        EditText etNim = dialogView.findViewById(R.id.et_nim);
        EditText etProdi = dialogView.findViewById(R.id.et_prodi);
        Button btnUpdate = dialogView.findViewById(R.id.button_update);
        Button btnHapus = dialogView.findViewById(R.id.button_delete);

        etNama.setText(mahasiswa.getNama());
        etNim.setText(mahasiswa.getNim());
        etProdi.setText(mahasiswa.getProdi());

        AlertDialog dialog = builder.create();
        btnUpdate.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().length() == 0 ||
                    etNim.getText().toString().trim().length() == 0 ||
                    etProdi.getText().toString().trim().length() == 0) {
                if (etNama.getText().toString().trim().length() == 0) {
                    etNama.setError("Nama Harus Diisi");
                }
                if (etNim.getText().toString().trim().length() == 0) {
                    etNim.setError("NIM Harus Diisi");
                }
                if (etProdi.getText().toString().trim().length() == 0) {
                    etProdi.setError("Prodi Harus Diisi");
                }
                return;
            }
            mahasiswa.setNama(etNama.getText().toString());
            mahasiswa.setNim(etNim.getText().toString());
            mahasiswa.setProdi(etProdi.getText().toString());
            databaseHandler.updateMahasiswa(mahasiswa);
            dialog.dismiss();
            tampilkanSemuaData();
        });

        btnHapus.setOnClickListener(v -> {
            databaseHandler.deleteMahasiswa(mahasiswa.getId());
            dialog.dismiss();
            tampilkanSemuaData();
        });

        dialog.show();
    }

    private void tampilkanSemuaData() {
        mahasiswaList = databaseHandler.getAllMahasiswa();
        Log.d("MainActivity", "mahasiswaList size: " + mahasiswaList.size());
        adapter = new MahasiswaAdapter(this, mahasiswaList);
        lvMahasiswa.setAdapter(adapter);
    }



    private void bukaDialogTambah() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Mahasiswa");
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.from_tambah, null);
        builder.setView(dialogView);

        EditText etNama = dialogView.findViewById(R.id.et_nama);
        EditText etNim = dialogView.findViewById(R.id.et_nim);
        EditText etProdi = dialogView.findViewById(R.id.et_prodi);
        Button btnSimpan = dialogView.findViewById(R.id.button_tambah);

        AlertDialog dialog = builder.create();
        btnSimpan.setOnClickListener(v -> {
            if (etNama.getText().toString().trim().length() == 0 ||
                    etNim.getText().toString().trim().length() == 0 ||
                    etProdi.getText().toString().trim().length() == 0) {
                if (etNama.getText().toString().trim().length() == 0) {
                    etNama.setError("Nama Harus Diisi");
                }
                if (etNim.getText().toString().trim().length() == 0) {
                    etNim.setError("NIM Harus Diisi");
                }
                if (etProdi.getText().toString().trim().length() == 0) {
                    etProdi.setError("Prodi Harus Diisi");
                }
                return;
            }
            Mahasiswa mahasiswa = new Mahasiswa(
                    etNama.getText().toString(),
                    etNim.getText().toString(),
                    etProdi.getText().toString()
            );
            databaseHandler.addMahasiswa(mahasiswa);
            dialog.dismiss();
            tampilkanSemuaData();
        });
        dialog.show();
    }
}
