package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilNom, tilEmail, tilMotDePasse, tilConfirmation;
    private TextInputEditText etNom, etEmail, etMotDePasse, etConfirmation;

    private Button btnInscrire, btnDejaCompte, btnInvite;
    private TextView tvTitle, tvSubtitle;

    private boolean modeConnexion = false;

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilNom = findViewById(R.id.til_nom);
        tilEmail = findViewById(R.id.til_email);
        tilMotDePasse = findViewById(R.id.til_password);
        tilConfirmation = findViewById(R.id.til_confirm_password);

        etNom = findViewById(R.id.et_nom);
        etEmail = findViewById(R.id.et_email);
        etMotDePasse = findViewById(R.id.et_password);
        etConfirmation = findViewById(R.id.et_confirm_password);

        btnInscrire = findViewById(R.id.btn_register);
        btnDejaCompte = findViewById(R.id.btn_have_account);
        btnInvite = findViewById(R.id.btn_guest);

        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);

        etNom.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus && !modeConnexion) {
                validerNom();
            }
        });

        etEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validerEmail();
            }
        });

        etMotDePasse.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                validerMotDePasse();

                if (!modeConnexion && etConfirmation.length() > 0) {
                    validerConfirmation();
                }
            }
        });

        etConfirmation.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus && !modeConnexion) {
                validerConfirmation();
            }
        });

        etMotDePasse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!modeConnexion && etConfirmation.length() > 0) {
                    validerConfirmation();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Rien à faire ici
            }
        });

        btnInscrire.setOnClickListener(view -> {
            boolean formulaireValide;

            if (modeConnexion) {
                boolean emailValide = validerEmail();
                boolean motDePasseValide = validerMotDePasse();

                formulaireValide = emailValide && motDePasseValide;
            } else {
                formulaireValide = validerTout();
            }

            if (formulaireValide) {
                Toast.makeText(
                        MainActivity.this,
                        getString(R.string.msg_inscription_ok),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Snackbar.make(
                        findViewById(R.id.main),
                        R.string.err_form_invalid,
                        Snackbar.LENGTH_LONG
                ).show();
            }
        });

        btnDejaCompte.setOnClickListener(view -> changerMode());

        // « Continuer en invité » ouvre l'écran Profil (2ème écran)
        btnInvite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(intent);
        });
    }

    private boolean validerTout() {
        boolean nomValide = validerNom();
        boolean emailValide = validerEmail();
        boolean motDePasseValide = validerMotDePasse();
        boolean confirmationValide = validerConfirmation();

        return nomValide
                && emailValide
                && motDePasseValide
                && confirmationValide;
    }

    private boolean validerNom() {
        String nom = etNom.getText() == null
                ? ""
                : etNom.getText().toString().trim();

        if (nom.isEmpty()) {
            tilNom.setError(getString(R.string.err_name_empty));
            return false;
        }

        if (nom.length() < MIN_NAME_LENGTH) {
            tilNom.setError(getString(R.string.err_name_short));
            return false;
        }

        if (nom.length() > MAX_NAME_LENGTH) {
            tilNom.setError(getString(R.string.err_name_long));
            return false;
        }

        tilNom.setError(null);
        return true;
    }

    private boolean validerEmail() {
        String email = etEmail.getText() == null
                ? ""
                : etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.err_email_empty));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.err_email_invalid));
            return false;
        }

        tilEmail.setError(null);
        return true;
    }

    private boolean validerMotDePasse() {
        String motDePasse = etMotDePasse.getText() == null
                ? ""
                : etMotDePasse.getText().toString();

        if (motDePasse.isEmpty()) {
            tilMotDePasse.setError(
                    getString(R.string.err_password_empty)
            );
            return false;
        }

        if (motDePasse.length() < MIN_PASSWORD_LENGTH) {
            tilMotDePasse.setError(
                    getString(R.string.err_password_short)
            );
            return false;
        }

        if (!motDePasse.matches(".*[A-Za-z].*")) {
            tilMotDePasse.setError(
                    getString(R.string.err_password_no_letter)
            );
            return false;
        }

        if (!motDePasse.matches(".*\\d.*")) {
            tilMotDePasse.setError(
                    getString(R.string.err_password_no_digit)
            );
            return false;
        }

        tilMotDePasse.setError(null);
        return true;
    }

    private boolean validerConfirmation() {
        String motDePasse = etMotDePasse.getText() == null
                ? ""
                : etMotDePasse.getText().toString();

        String confirmation = etConfirmation.getText() == null
                ? ""
                : etConfirmation.getText().toString();

        if (confirmation.isEmpty()) {
            tilConfirmation.setError(
                    getString(R.string.err_confirm_empty)
            );
            return false;
        }

        if (!motDePasse.equals(confirmation)) {
            tilConfirmation.setError(
                    getString(R.string.err_confirm_mismatch)
            );
            return false;
        }

        tilConfirmation.setError(null);
        return true;
    }

    private void changerMode() {
        modeConnexion = !modeConnexion;

        if (modeConnexion) {
            tilNom.setVisibility(View.GONE);
            tilConfirmation.setVisibility(View.GONE);

            btnInscrire.setText(R.string.btn_login);
            btnDejaCompte.setText(R.string.btn_register);

            tvTitle.setText(R.string.title_login);
            tvSubtitle.setText(R.string.subtitle_login);

            tilNom.setError(null);
            tilConfirmation.setError(null);

        } else {
            tilNom.setVisibility(View.VISIBLE);
            tilConfirmation.setVisibility(View.VISIBLE);

            btnInscrire.setText(R.string.btn_register);
            btnDejaCompte.setText(R.string.btn_have_account);

            tvTitle.setText(R.string.title_skipqueue);
            tvSubtitle.setText(R.string.subtitle_skipqueue);
        }

        tilEmail.setError(null);
        tilMotDePasse.setError(null);
    }
}