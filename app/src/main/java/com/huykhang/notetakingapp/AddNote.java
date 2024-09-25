package com.huykhang.notetakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {
    EditText noteContent,noteTitle;
    FloatingActionButton saveNoteButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressBar saveNoteProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBarTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        Toolbar toolbar=findViewById(R.id.createNote_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle=findViewById(R.id.createNote_Title);
        noteContent=findViewById(R.id.createNote_Content);
        saveNoteButton=findViewById(R.id.savenote);
        saveNoteProgressBar=findViewById(R.id.progressbarofcreatenote);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=noteTitle.getText().toString().trim();
                String content=noteContent.getText().toString().trim();
                saveNoteProgressBar.setVisibility(View.VISIBLE);

                DocumentReference documentReference=firebaseFirestore.
                        collection("user-notes").
                        document(firebaseUser.getUid()).
                        collection("notes").
                        document();

                Map<String ,Object> note= new HashMap<>();
                String titleToPut = title.isEmpty() ? "Untitled":title;
                String contentToPut = content.isEmpty() ?"...":content;
                note.put("title",titleToPut);
                note.put("content",contentToPut);
                note.put("timestamp", FormatTimeStamp.getCurrentTimestampAsString());

                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Created new note",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNote.this,NoteSpace.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed To Create Note", Toast.LENGTH_SHORT).show();
                        saveNoteProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}