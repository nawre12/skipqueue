package com.example.tp3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

public class ProfilActivity extends AppCompatActivity {

    private boolean estInvite = false;

    private TextView tvNom, tvEmail;
    private Chip chipStatut;
    private MaterialCardView cardInvitation;
    private Button btnCreerCompte, btnCommandes, btnFavoris,
            btnParametres, btnDeconnexion, btnSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvNom = findViewById(R.id.tv_nom);
        tvEmail = findViewById(R.id.tv_email);
        chipStatut = findViewById(R.id.chip_statut);
        cardInvitation = findViewById(R.id.card_invitation);

        btnCreerCompte = findViewById(R.id.btn_creer_compte);
        btnCommandes = findViewById(R.id.btn_commandes);
        btnFavoris = findViewById(R.id.btn_favoris);
        btnParametres = findViewById(R.id.btn_parametres);
        btnDeconnexion = findViewById(R.id.btn_deconnexion);
        btnSupprimer = findViewById(R.id.btn_supprimer);

        btnCreerCompte.setOnClickListener(v -> afficherToast(R.string.toast_creer_compte));
        btnCommandes.setOnClickListener(v -> afficherToast(R.string.toast_commandes));
        btnFavoris.setOnClickListener(v -> afficherToast(R.string.toast_favoris));
        btnParametres.setOnClickListener(v -> afficherToast(R.string.toast_parametres));
        btnDeconnexion.setOnClickListener(v -> afficherToast(R.string.toast_deconnexion));
        btnSupprimer.setOnClickListener(v -> afficherToast(R.string.toast_supprimer));

        afficherMode();
    }

    private void afficherMode() {
        if (estInvite) {
            tvNom.setText(R.string.profil_nom_invite);
            chipStatut.setText(R.string.profil_statut_invite);

            tvEmail.setVisibility(View.GONE);
            cardInvitation.setVisibility(View.VISIBLE);

            btnCommandes.setVisibility(View.GONE);
            btnFavoris.setVisibility(View.GONE);
            btnDeconnexion.setVisibility(View.GONE);
            btnSupprimer.setVisibility(View.GONE);
        } else {
            tvNom.setText(R.string.profil_nom_placeholder);
            tvEmail.setText(R.string.profil_email_placeholder);
            chipStatut.setText(R.string.profil_statut_inscrit);

            tvEmail.setVisibility(View.VISIBLE);
            cardInvitation.setVisibility(View.GONE);

            btnCommandes.setVisibility(View.VISIBLE);
            btnFavoris.setVisibility(View.VISIBLE);
            btnDeconnexion.setVisibility(View.VISIBLE);
            btnSupprimer.setVisibility(View.VISIBLE);
        }

    }

    private void afficherToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}