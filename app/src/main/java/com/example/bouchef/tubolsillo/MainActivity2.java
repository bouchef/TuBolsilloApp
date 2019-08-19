package com.example.bouchef.tubolsillo;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.model.MensajeViewModelResponse;
import com.example.bouchef.tubolsillo.api.model.UsuarioViewModelResponse;
import com.example.bouchef.tubolsillo.generics.ApplicationGlobal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;

public class MainActivity2 extends AppCompatActivity {
    private Context mContext= MainActivity2.this;

    private NavigationView navView;
    private DrawerLayout drawerLayout;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            //View credencial = findViewById(R.id.activity_credencial_pcd);
            //credencial.setVisibility(View.GONE);


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            /*boton flotante*/
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Ud esta realizando una llamada de Alerta!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            /* fin boton flotante*/


            drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

            navView = (NavigationView)findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);

            toggle.syncState();

            navView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            boolean fragmentTransaction = false;
                            Fragment fragment = null;

                            switch (menuItem.getItemId()) {
                                case R.id.nav_home:
                                    fragment = new FragmentBotoneraInicioPCD();
                                    fragmentTransaction = true;
                                    break;
                                case R.id.nav_inicio_ayudante:
                                    fragment = new FragmentBotoneraInicioAyudante();
                                    fragmentTransaction = true;
                                    break;
                                case R.id.nav_credencial:
                                    fragment = new FragmentCredencialPCD();
                                    fragmentTransaction = true;
                                    break;
                                case R.id.nav_comenzar_compra:
                                    fragment = new FragmentComenzarCompra();
                                    fragmentTransaction = true;
                                    break;
                                case R.id.nav_notificador:
                                    fragment = new FragmentNotificador();
                                    fragmentTransaction = true;
                                    break;
                                case R.id.nav_control_gasto:
                                    fragment = new FragmentControlGastos();
                                    fragmentTransaction = true;
                                    break;
                            }

                            if(fragmentTransaction) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .commit();

                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());
                            }

                            drawerLayout.closeDrawers();
                            return true;
                        }
                    });



            // ACCION DEL BOTON DE MENSAJE
            /*btn_accion =  findViewById(R.id.accion);
            String imageId = (String) btn_accion.getTag();

            btn_accion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(applicationGlobal.getUsuario().getIdTipoUsuario().equals(1)) {
                        if(imageId.equals("Autorizacion")) {
                            Intent intent = new Intent(v.getContext(), AutorizarTutor.class);
                            startActivityForResult(intent, 0);
                        }
                        if(imageId.equals("Informacion")) {
                            // Marcar mensaje como leido y actualizar
                            Alerts.newToastLarge(getApplicationContext(), "Marcar Mensaje como leido");
                        }
                    }

                }
            });*/
            // FIN ACCION DEL BOTON MENSAJE

            /* abro FragmentBotoneraInicioPCD */

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new FragmentBotoneraInicioPCD())
                    .commit();
            /* fin abro FragmentBotoneraInicioPCD*/
        }



        @Override
        public void onBackPressed() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        public FragmentManager getFM() {
            return getSupportFragmentManager();
        }

        public void goToFragment(Fragment fragment) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.content_frame, fragment).commit();
        }



    }
