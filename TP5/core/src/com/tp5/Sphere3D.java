package com.tp5;

import com.badlogic.gdx.math.Vector3;

public class Sphere3D {

    private String name;
    private Vector3 position;
    private float raySphere;
    private Vector3 color;
    private String type;
    private float kPhong;
    private float kReflex;
    private float kRefrac;
    private float kText;

    Sphere3D(String nomGiven, float kPhongGiven, float kReflexGiven, float kRefracGiven, float kTextGiven,
            Vector3 posGiven, float rayon, Vector3 couleurGiven) {
        this.name = nomGiven;
        this.position = posGiven;
        this.raySphere = rayon;
        this.color = couleurGiven;
        this.type = "objet3D";
        this.kPhong = kPhongGiven;
        this.kReflex = kReflexGiven;
        this.kRefrac = kRefracGiven;
        this.kText = kTextGiven;
    }

}