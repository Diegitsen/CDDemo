package com.example.cddemo.Interfaces;

import com.example.cddemo.Fragments.BienvenidaFragment;
import com.example.cddemo.Fragments.ConsultaListaUsuariosFragment;
import com.example.cddemo.Fragments.ConsultaUsuarioFragment;
import com.example.cddemo.Fragments.DesarrolladoFragment;
import com.example.cddemo.Fragments.RegistrarUsuarioFragment;

public interface IFragments extends BienvenidaFragment.OnFragmentInteractionListener,
        ConsultaListaUsuariosFragment.OnFragmentInteractionListener,
        ConsultaUsuarioFragment.OnFragmentInteractionListener,
        DesarrolladoFragment.OnFragmentInteractionListener,
        RegistrarUsuarioFragment.OnFragmentInteractionListener
{
}
