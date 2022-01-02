package com.example.objetivo09;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.ViewHolder> {
    private final ArrayList<PostClass> arrayList;
    private static final String ip = "192.168.1.97";

    public RecyclerAdapterHome(ArrayList<PostClass> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterHome.ViewHolder holder, int position) {
        PostClass feed = arrayList.get(position);

        holder.user.setText(feed.userName);
        holder.message.setText(feed.getDescription());
        holder.profileImage.setImageResource(feed.userPhoto);
        holder.postImage.setImageResource(feed.getPostImage());
        holder.idUsuarioPost.setText(String.valueOf(feed.id_usuario));
        holder.idPost.setText(String.valueOf(feed.getId_publicacion()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage, postImage, like, dislike, finance;
        TextView user, message, idUsuarioPost, idPost, likeNumber, dislikeNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            String urlInsertReaccion = "http://" + ip + "/bdproyecto9/insert_reaccion.php";
            String urlDeleteReaccion = "http://" + ip + "/bdproyecto9/delete_reaccion.php";
            String urlInsertFinance = "http://" + ip + "/bdproyecto9/insert_finance.php";
            String urlCountReaccion = "http://" + ip + "/bdproyecto9/count_reaccion.php";
            String urlSetReaccion = "http://" + ip + "/bdproyecto9/select_reaccion.php";

            like = itemView.findViewById(R.id.ivLike);
            dislike = itemView.findViewById(R.id.ivDislike);
            finance = itemView.findViewById(R.id.ivFinance);
            profileImage = itemView.findViewById(R.id.ivProfile);
            postImage = itemView.findViewById(R.id.ivPost);
            user = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            idUsuarioPost = itemView.findViewById(R.id.idUsuario);
            idPost = itemView.findViewById(R.id.idPost);
            likeNumber = itemView.findViewById(R.id.likeCount);
            contarReaccion(urlCountReaccion, itemView, "0");
            dislikeNumber = itemView.findViewById(R.id.dislikeCount);
            contarReaccion(urlCountReaccion, itemView, "1");
            finance.setColorFilter(Color.parseColor("#7BB062"));
            setReaccion(urlSetReaccion, itemView);

            // LikeBtn
            itemView.findViewById(R.id.ivLike).setOnClickListener(view -> {
                int numeroLikes, numeroDislikes;
                Log.d("demo", "Like id de usuario: " + idUsuarioPost.getText());
                if (like.getColorFilter() != null) {
                    like.setColorFilter(null);
                    numeroLikes = Integer.parseInt(likeNumber.getText().toString()) - 1;
                    likeNumber.setText(String.valueOf(numeroLikes));
                    deleteReaccion(urlDeleteReaccion, itemView, "0");
                } else {
                    like.setColorFilter(Color.parseColor("#24a0ed"));
                    numeroLikes = Integer.parseInt(likeNumber.getText().toString()) + 1;
                    likeNumber.setText(String.valueOf(numeroLikes));
                    if (dislike.getColorFilter() != null) {
                        numeroDislikes = Integer.parseInt(dislikeNumber.getText().toString()) - 1;
                        dislikeNumber.setText(String.valueOf(numeroDislikes));
                    }
                    dislike.setColorFilter(null);
                    insertReaccion(urlInsertReaccion, itemView, "0");
                    deleteReaccion(urlDeleteReaccion, itemView, "1");
                }
            });

            // DislikeBtn
            itemView.findViewById(R.id.ivDislike).setOnClickListener(view -> {
                int numeroLikes, numeroDislikes;
                Log.d("demo", "Dislike id de usuario: " + idUsuarioPost.getText());
                if (dislike.getColorFilter() != null) {
                    dislike.setColorFilter(null);
                    numeroDislikes = Integer.parseInt(dislikeNumber.getText().toString()) - 1;
                    dislikeNumber.setText(String.valueOf(numeroDislikes));
                    deleteReaccion(urlDeleteReaccion, itemView, "1");
                } else {
                    dislike.setColorFilter(Color.parseColor("#DC143C"));
                    numeroDislikes = Integer.parseInt(dislikeNumber.getText().toString()) + 1;
                    dislikeNumber.setText(String.valueOf(numeroDislikes));

                    if (like.getColorFilter() != null) {
                        numeroLikes = Integer.parseInt(likeNumber.getText().toString()) - 1;
                        likeNumber.setText(String.valueOf(numeroLikes));
                    }
                    like.setColorFilter(null);
                    insertReaccion(urlInsertReaccion, itemView, "1");
                    deleteReaccion(urlDeleteReaccion, itemView, "0");
                }
            });


            // FinanceBtn
            itemView.findViewById(R.id.ivFinance).setOnClickListener(view -> {
                int margin = dpToPx(20);
                AlertDialog.Builder opcionesDialog = new AlertDialog.Builder(finance.getContext());
                opcionesDialog.setTitle("Finance");
                opcionesDialog.setMessage("Monto a donar en USD");
                final EditText input = new EditText(finance.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("USD");
                input.setPadding(margin, 0, margin, 0);
                opcionesDialog.setView(input);

                opcionesDialog.setPositiveButton("Donar", (dialog, which) -> {
                    if (input.getText().toString().isEmpty()) {
                        Toast.makeText(finance.getContext(), "Campo vacio, no se pudo donar", Toast.LENGTH_SHORT).show();
                    } else {
                        finance(urlInsertFinance, itemView, input.getText().toString());
                        Toast.makeText(finance.getContext(), "Donacion Realizada", Toast.LENGTH_SHORT).show();
                    }
                });
                opcionesDialog.setNegativeButton("Cancelar", null);
                AlertDialog dialogo = opcionesDialog.create();
                dialogo.show();

            });

        }

        public static int dpToPx(int dp) {
            return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
        }

        private void insertReaccion(String URL, View view, String tipo) {
            /*
            Mandar parametros de ID DE USUARIO y de TIPO que corresponde a 0 en el caso de like y 1 en caso de dislike
        */
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            }, error -> {
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    SharedPreferences preferences = view.getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                    parametros.put("tipo", tipo);
                    parametros.put("fk_id_usuario", preferences.getString("id_usuario", ""));
                    parametros.put("fk_id_publicacion", idPost.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);
        }


        private void deleteReaccion(String URL, View view, String tipo) {
            /*
            Mandar parametros de ID DE USUARIO y de TIPO que corresponde a 0 en el caso de like y 1 en caso de dislike
        */
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            }, error -> {
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    SharedPreferences preferences = view.getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                    parametros.put("tipo", tipo);
                    parametros.put("fk_id_usuario", preferences.getString("id_usuario", ""));
                    parametros.put("fk_id_publicacion", idPost.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);
        }

        private void finance(String URL, View view, String monto) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            }, error -> {
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    SharedPreferences preferences = view.getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                    parametros.put("monto", monto);
                    parametros.put("fk_id_usuario", preferences.getString("id_usuario", ""));
                    parametros.put("fk_id_publicacion", idPost.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);
        }

        private void contarReaccion(String URL, View view, String tipo) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                if (tipo.equals("0")) {
                    likeNumber.setText(response);
                } else if (tipo.equals("1")) {
                    dislikeNumber.setText(response);
                }
            }, error -> {

            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("tipo", tipo);
                    parametros.put("fk_id_publicacion", idPost.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);
        }

        private void setReaccion(String URL, View view) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                if (!response.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("reaccion");
                        String tipo = object.getString("TIPO");

                        SharedPreferences preferences = view.getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                        if (tipo.equals("0")) {
                            like.setColorFilter(Color.parseColor("#24a0ed"));

                        } else if (tipo.equals("1")) {
                            dislike.setColorFilter(Color.parseColor("#DC143C"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> {
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    SharedPreferences preferences = view.getContext().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                    parametros.put("fk_id_usuario", preferences.getString("id_usuario", ""));
                    parametros.put("fk_id_publicacion", idPost.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);
        }

    }
}