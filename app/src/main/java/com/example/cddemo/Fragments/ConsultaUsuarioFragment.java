package com.example.cddemo.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cddemo.Entidades.Usuario;
import com.example.cddemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultaUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultaUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText etDocumento;
    TextView tvNombre, tvProfesion;
    Button bConsultar;
    ProgressDialog progressDialog;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public ConsultaUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaUsuarioFragment newInstance(String param1, String param2) {
        ConsultaUsuarioFragment fragment = new ConsultaUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_consulta_usuario, container, false);

        etDocumento = vista.findViewById(R.id.etDocumento);
        tvNombre = vista.findViewById(R.id.tvNombre);
        tvProfesion = vista.findViewById(R.id.tvProfesion);
        bConsultar = vista.findViewById(R.id.bConsultar);

        requestQueue = Volley.newRequestQueue(getContext());

        bConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        return vista;
    }

    private void cargarWebService()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultado...");
        progressDialog.show();

        String url = "http://192.168.0.14/EjemploBDRemota/wsJSONConsultaUsuario.php?documento="
                + etDocumento.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this, this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progressDialog.hide();
        Toast.makeText(getContext(), "No se pudo consultar" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        //Toast.makeText(getContext(),"Mensaje: " + response, Toast.LENGTH_SHORT).show();

        Usuario usuario = new Usuario();

        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setProfesion(jsonObject.optString("profesion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvNombre.setText("Nombre: " + usuario.getNombre());
        tvProfesion.setText("Profesion: " + usuario.getProfesion());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
